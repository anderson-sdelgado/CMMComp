package br.com.usinasantafe.cmm.presenter.view.implement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.implement.SetNroEquip
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import br.com.usinasantafe.cmm.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

data class ImplementState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val pos: Int = 1,
    val nroEquip: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<ImplementState> {

    override fun copyWithStatus(status: UpdateStatusState): ImplementState =
        copy(status = status)
}

@HiltViewModel
class ImplementViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateTableEquip: UpdateTableEquip,
    private val hasEquipSecondary: HasEquipSecondary,
    private val setNroEquip: SetNroEquip
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(ImplementState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ImplementState.() -> ImplementState) {
        _uiState.update(block)
    }

    init { updateState { copy(flowApp = FlowApp.entries[this@ImplementViewModel.flowApp]) } }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(nroEquip = addTextField(nroEquip, text)) }
            TypeButton.CLEAN -> updateState { copy(nroEquip = clearTextField(nroEquip)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    fun ret() = viewModelScope.launch {
        updateState { copy(pos = 1, nroEquip = "") }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            val checkEmpty = state.nroEquip.isBlank()
            if(checkEmpty) {
                if(state.pos == 1) {
                    updateState { withFailure(getClassAndMethod(), Errors.FIELD_EMPTY) }
                    return@launch
                } else {
                    return@runCatching true
                }
            }
            val check = hasEquipSecondary(state.nroEquip, TypeEquip.IMPLEMENT).getOrThrow()
            if(!check) return@runCatching false
            setNroEquip(state.nroEquip, state.pos).getOrThrow()
            if(state.pos == 1) {
                updateState { copy(pos = 2, nroEquip = "") }
                return@launch
            }
            true
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

    suspend fun updateAllDatabase(): Flow<ImplementState> =
        executeUpdateSteps(
            steps = listOf(updateTableEquip(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}