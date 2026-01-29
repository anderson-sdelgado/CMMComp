package br.com.usinasantafe.cmm.presenter.view.header.motorPump

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdEquipMotorPump
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
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

data class MotorPumpState(
    val nroEquip: String = "",
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

fun UpdateStatusState.toMotorPump(
    classAndMethod: String,
    current: MotorPumpState
): MotorPumpState {

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
class MotorPumpViewModel @Inject constructor(
    private val updateTableEquip: UpdateTableEquip,
    private val hasEquipSecondary: HasEquipSecondary,
    private val setIdEquipMotorPump: SetIdEquipMotorPump
) : ViewModel() {

    private val _uiState = MutableStateFlow(MotorPumpState())
    val uiState = _uiState.asStateFlow()

    private fun updateUi(block: MotorPumpState.() -> MotorPumpState) {
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
                val value = addTextField(uiState.value.nroEquip, text)
                _uiState.update {
                    it.copy(nroEquip = value)
                }
            }

            TypeButton.CLEAN -> {
                val value = clearTextField(uiState.value.nroEquip)
                _uiState.update {
                    it.copy(nroEquip = value)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.nroEquip.isEmpty()) {
                    handleFailure("Field Empty!", Errors.FIELD_EMPTY)
                    return
                }
                setNroEquip()
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

    private fun setNroEquip() = viewModelScope.launch {
        val resultHas = hasEquipSecondary(
            nroEquip =  uiState.value.nroEquip,
            typeEquip = TypeEquip.MOTOR_PUMP
        )
        resultHas.onFailure {
            handleFailure(it)
            return@launch
        }
        val check = resultHas.getOrNull()!!
        if (check) {
            val resultSet = setIdEquipMotorPump(
                nroEquip = uiState.value.nroEquip,
            )
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

    private suspend fun Flow<UpdateStatusState>.collectUpdateStep(
        classAndMethod: String,
        emitState: suspend (MotorPumpState) -> Unit
    ): Boolean {
        var ok = true
        collect { result ->
            val newState = result.toMotorPump(classAndMethod, _uiState.value)
            emitState(newState)
            if (newState.flagFailure) {
                ok = false
                return@collect
            }
        }
        return ok
    }

    fun updateAllDatabase(): Flow<MotorPumpState> = flow {

        val size = 4f

        val steps = listOf(
            updateTableEquip(size, 1f),
        )

        for (step in steps) {
            val ok = step.collectUpdateStep(getClassAndMethod()) { emit(it) }
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
    ): MotorPumpState {
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
    ): MotorPumpState {
        val msg = "${throwable.message} -> ${throwable.cause}"
        return handleFailure(msg, errors, emit)
    }

}