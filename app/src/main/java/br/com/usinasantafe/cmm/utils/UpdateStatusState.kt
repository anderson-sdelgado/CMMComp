package br.com.usinasantafe.cmm.utils

import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

data class UpdateStatusState(
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = true,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
    val flagStopFlow: Boolean = false
)

suspend fun Flow<UpdateStatusState>.collectUpdateStep(
    classAndMethod: String,
    currentStatus: UpdateStatusState,
    emitState: suspend (UpdateStatusState) -> Unit
): Boolean {

    var ok = true

    collect { result ->
        val newStatus = result.toUiStatus(classAndMethod, currentStatus)
        emitState(newStatus)

        if (newStatus.flagFailure) {
            ok = false
            return@collect
        }
    }

    return ok
}

fun UpdateStatusState.toUiStatus(
    classAndMethod: String,
    current: UpdateStatusState
): UpdateStatusState {

    val failMsg = failure.takeIf { it.isNotEmpty() }
        ?.let { "$classAndMethod -> $it" }
        ?: ""

    if (failMsg.isNotEmpty()) Timber.e(failMsg)

    return current.copy(
        flagDialog = flagDialog,
        flagFailure = flagFailure,
        errors = errors,
        failure = failMsg,
        flagProgress = flagProgress,
        currentProgress = currentProgress,
        levelUpdate = levelUpdate,
        tableUpdate = tableUpdate
    )
}

fun UpdateStatusState.withFailure(
    classAndMethod: String,
    message: String,
    errors: Errors = Errors.EXCEPTION,
    flagProgress: Boolean = false
): UpdateStatusState {

    val failMsg = "$classAndMethod -> $message"
    Timber.e(failMsg)

    return copy(
        flagDialog = true,
        flagFailure = true,
        failure = failMsg,
        errors = errors,
        flagProgress = flagProgress,
        currentProgress = 1f
    )
}

fun UpdateStatusState.withFailure(
    classAndMethod: String,
    throwable: Throwable,
    errors: Errors = Errors.EXCEPTION,
    flagProgress: Boolean = false
): UpdateStatusState {
    val msg = "${throwable.message} -> ${throwable.cause}"
    return withFailure(classAndMethod, msg, errors, flagProgress)
}

suspend fun FlowCollector<UpdateStatusState>.emitProgress(
    count: Float,
    sizeAll: Float,
    level: LevelUpdate,
    table: String,
    flagProgress: Boolean = true
) {
    val step = when(level){
        LevelUpdate.RECOVERY -> 1f
        LevelUpdate.CLEAN -> 2f
        LevelUpdate.SAVE -> 3f
        else -> 0f
    }
    emit(
        UpdateStatusState(
            flagProgress = flagProgress,
            currentProgress = updatePercentage(step, count, sizeAll),
            tableUpdate = table,
            levelUpdate = level
        )
    )
}

suspend fun FlowCollector<UpdateStatusState>.emitProgressOS(
    count: Float = 1f,
    sizeAll: Float = 1f,
    level: LevelUpdate,
    table: String,
    flagProgress: Boolean = true,
    flagStopFlow: Boolean = false
) {
    val step = when(level){
        LevelUpdate.CHECK_CONNECTION -> 1f
        LevelUpdate.RECOVERY -> 2f
        LevelUpdate.CLEAN -> 3f
        LevelUpdate.SAVE -> 4f
        else -> 0f
    }
    emit(
        UpdateStatusState(
            flagProgress = flagProgress,
            currentProgress = updatePercentage(step, count, sizeAll),
            tableUpdate = table,
            levelUpdate = level,
            flagStopFlow = flagStopFlow
        )
    )
}

suspend fun FlowCollector<UpdateStatusState>.emitProgressOSError(
    errors: Errors,
) {
    emit(
        UpdateStatusState(
            flagProgress = true,
            errors = errors,
            flagDialog = true,
            flagFailure = true,
            failure = "",
            currentProgress = 1f,
            levelUpdate = null
        )
    )
}


suspend fun FlowCollector<UpdateStatusState>.emitFailure(
    failure: String,
) {
    emit(
        UpdateStatusState(
            flagProgress = true,
            errors = Errors.UPDATE,
            flagDialog = true,
            flagFailure = true,
            failure = failure,
            currentProgress = 1f,
            levelUpdate = null
        )
    )
}

fun <STATE> executeUpdateSteps(
    steps: List<Flow<UpdateStatusState>>,
    getState: () -> STATE,
    getStatus: (STATE) -> UpdateStatusState,
    copyStateWithStatus: (STATE, UpdateStatusState) -> STATE,
    classAndMethod: String,
    flagUpdateConfig: Boolean = false,
    flagCheckOS: Boolean = false
): Flow<STATE> = flow {

    var stopFlow = false
    var levelUpdate: LevelUpdate? = null

    for (step in steps) {
        val ok = step.collectUpdateStep(
            classAndMethod = classAndMethod,
            currentStatus = getStatus(getState())
        ) { status ->
            if (status.flagStopFlow) {
                stopFlow = true
                levelUpdate = status.levelUpdate
            }
            val newState = copyStateWithStatus(getState(), status)
            emit(newState)
        }
        if (!ok) return@flow

        if (stopFlow) break

    }

    if (!flagUpdateConfig) {
        val finalStatus = getStatus(getState()).copy(
            flagDialog = true,
            flagProgress = false,
            flagFailure = false,
            levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
            currentProgress = 1f,
        )
        val finalState = copyStateWithStatus(getState(), finalStatus)
        emit(finalState)
    }

    if(flagCheckOS){
        val finalStatus = getStatus(getState()).copy(
            flagDialog = false,
            flagProgress = false,
            flagFailure = false,
            levelUpdate = levelUpdate,
            currentProgress = 1f,
        )
        val finalState = copyStateWithStatus(getState(), finalStatus)
        emit(finalState)
    }

}

interface UiStateWithStatus<T> {
    val status: UpdateStatusState

    fun copyWithStatus(status: UpdateStatusState): T

    fun withFailure(
        classAndMethod: String,
        throwable: Throwable,
        errors: Errors = Errors.EXCEPTION,
        flagProgress: Boolean = false
    ): T =
        copyWithStatus(
            status.withFailure(
                classAndMethod,
                throwable,
                errors,
                flagProgress
            )
        )

    fun withFailure(
        classAndMethod: String,
        message: String,
        errors: Errors = Errors.EXCEPTION,
        flagProgress: Boolean = false
    ): T =
        copyWithStatus(
            status.withFailure(
                classAndMethod,
                message,
                errors,
                flagProgress
            )
        )

}

fun <T> UiStateWithStatus<T>.withFailure(
    classAndMethod: String,
    error: Errors = Errors.INVALID,
    flagProgress: Boolean = false,
    failure: String = ""
): T =
    copyWithStatus(
        status.withFailure(
            classAndMethod = classAndMethod,
            message = failure.ifEmpty { failure(error) },
            errors = error,
            flagProgress = flagProgress
        )
    )


fun <T : UiStateWithStatus<T>> Result<*>.onFailureUpdate(
    classAndMethod: String,
    updateState: ((T.() -> T)) -> Unit
) {
    onFailure { failure ->
        updateState {
            withFailure(classAndMethod, failure)
        }
    }
}
