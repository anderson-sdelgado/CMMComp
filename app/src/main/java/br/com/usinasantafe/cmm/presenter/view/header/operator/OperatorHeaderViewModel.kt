package br.com.usinasantafe.cmm.presenter.view.header.operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckRegOperator
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetRegOperator
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OperatorHeaderState(
    val regColab: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.toOperator(
    classAndMethod: String,
    current: OperatorHeaderState
): OperatorHeaderState {

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

@HiltViewModel
class OperatorHeaderViewModel @Inject constructor(
    private val updateTableColab: UpdateTableColab,
    private val checkRegOperator: CheckRegOperator,
    private val setRegOperator: SetRegOperator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OperatorHeaderState())
    val uiState = _uiState.asStateFlow()

    private fun updateUi(block: OperatorHeaderState.() -> OperatorHeaderState) {
        _uiState.update { it.block() }
    }

    fun setCloseDialog() = updateUi {
        copy(flagDialog = false, flagFailure = false)
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val matricColab = addTextField(uiState.value.regColab, text)
                _uiState.update {
                    it.copy(regColab = matricColab)
                }
            }

            TypeButton.CLEAN -> {
                val matricColab = clearTextField(uiState.value.regColab)
                _uiState.update {
                    it.copy(regColab = matricColab)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.regColab.isEmpty()) {
                    handleFailure("Field Empty!", Errors.FIELD_EMPTY)
                    return
                }
                setRegOperatorHeader()
            }

            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }

    private fun setRegOperatorHeader() = viewModelScope.launch {
        val resultCheck = checkRegOperator(uiState.value.regColab)
        resultCheck.onFailure {
            handleFailure(it)
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        if (check) {
            val resultSet = setRegOperator(uiState.value.regColab)
            resultSet.onFailure {
                handleFailure(it)
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = check,
                flagDialog = !check,
                flagFailure = !check,
                errors = Errors.INVALID
            )
        }
    }

    private suspend fun Flow<ResultUpdateModel>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (OperatorHeaderState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toOperator(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<OperatorHeaderState> = flow {
        val classAndMethod = getClassAndMethod()
        val size = 4f

        val steps = listOf(
            updateTableColab(size, 1f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(classAndMethod) { emit(it) }
            if (!ok) return@flow
        }

        emit(
            _uiState.value.copy(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

    private fun handleFailure(
        message: String,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): OperatorHeaderState {
        val failMsg = "${getClassAndMethod()} -> $message"
        Timber.e(failMsg)

        val newState = _uiState.value.copy(
            flagDialog = true,
            flagFailure = true,
            failure = failMsg,
            errors = errors,
            flagProgress = false,
            currentProgress = 1f
        )

        if (!emit) {
            _uiState.value = newState
        }

        return newState
    }

    private fun handleFailure(
        throwable: Throwable,
        errors: Errors = Errors.EXCEPTION,
        emit: Boolean = false
    ): OperatorHeaderState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}