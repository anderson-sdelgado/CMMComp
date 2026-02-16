package br.com.usinasantafe.cmm.presenter.view.fertigation.motorPump

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetIdEquipMotorPump
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MotorPumpState(
    val nroEquip: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<MotorPumpState> {

    override fun copyWithStatus(status: UpdateStatusState): MotorPumpState =
        copy(status = status)
}

@HiltViewModel
class MotorPumpViewModel @Inject constructor(
    private val updateTableEquip: UpdateTableEquip,
    private val hasEquipSecondary: HasEquipSecondary,
    private val setIdEquipMotorPump: SetIdEquipMotorPump
) : ViewModel() {

    private val _uiState = MutableStateFlow(MotorPumpState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MotorPumpState.() -> MotorPumpState) {
        _uiState.update { it.block() }
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(nroEquip = addTextField(nroEquip, text)) }
            TypeButton.CLEAN -> updateState { copy(nroEquip = clearTextField(nroEquip)) }
            TypeButton.OK -> validateAndSet()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    private fun validateAndSet() {
        if (state.nroEquip.isBlank()) {
            updateState { withFailure(getClassAndMethod(), "Field Empty!", Errors.FIELD_EMPTY) }
            return
        }
        set()
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            val check = hasEquipSecondary(uiState.value.nroEquip, TypeEquip.MOTOR_PUMP).getOrThrow()
            if (check) {
                setIdEquipMotorPump(uiState.value.nroEquip).getOrThrow()
            }
            check
        }
            .onSuccess { check ->
                updateState {
                    copy(
                        flagAccess = check,
                        status = status.copy(
                            flagDialog = !check,
                            flagFailure = !check,
                            errors = Errors.INVALID
                        )
                    )
                }
            }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    suspend fun updateAllDatabase(): Flow<MotorPumpState> =
        executeUpdateSteps(
            steps = listOf(updateTableEquip(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}