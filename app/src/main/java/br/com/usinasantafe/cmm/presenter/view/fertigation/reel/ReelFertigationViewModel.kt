package br.com.usinasantafe.cmm.presenter.view.fertigation.reel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetNroEquipReel
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.presenter.view.motomec.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import br.com.usinasantafe.cmm.utils.sizeUpdate
import br.com.usinasantafe.cmm.utils.withFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ReelFertigationState(
    val nroEquip: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<ReelFertigationState> {

    override fun copyWithStatus(status: UpdateStatusState): ReelFertigationState =
        copy(status = status)

}

@HiltViewModel
class ReelFertigationViewModel @Inject constructor(
    private val updateTableEquip: UpdateTableEquip,
    private val hasEquipSecondary: HasEquipSecondary,
    private val setNroEquipReel: SetNroEquipReel,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReelFertigationState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ReelFertigationState.() -> ReelFertigationState) {
        _uiState.update(block)
    }

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

    private fun set() = viewModelScope.launch {
        runCatching {
            if (state.nroEquip.isBlank()) {
                updateState { withFailure(getClassAndMethod(), Errors.FIELD_EMPTY) }
                return@launch
            }
            val check = hasEquipSecondary(uiState.value.nroEquip, TypeEquip.REEL_FERT).getOrThrow()
            if (check) setNroEquipReel(uiState.value.nroEquip).getOrThrow()
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

    suspend fun updateAllDatabase(): Flow<ReelFertigationState> =
        executeUpdateSteps(
            steps = listOf(updateTableEquip(sizeUpdate())),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}