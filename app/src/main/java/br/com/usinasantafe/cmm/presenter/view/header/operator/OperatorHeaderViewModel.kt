package br.com.usinasantafe.cmm.presenter.view.header.operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasRegColab
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetRegOperator
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
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

data class OperatorHeaderState(
    val regColab: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<OperatorHeaderState> {

    override fun copyWithStatus(status: UpdateStatusState): OperatorHeaderState =
        copy(status = status)
    }

@HiltViewModel
class OperatorHeaderViewModel @Inject constructor(
    private val updateTableColab: UpdateTableColab,
    private val hasRegColab: HasRegColab,
    private val setRegOperator: SetRegOperator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OperatorHeaderState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: OperatorHeaderState.() -> OperatorHeaderState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(regColab = addTextField(regColab, text)) }
            TypeButton.CLEAN -> updateState { copy(regColab = clearTextField(regColab)) }
            TypeButton.OK -> validateAndSet()
            TypeButton.UPDATE -> {
                viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
            }
        }
    }

    private fun validateAndSet() {
        if (state.regColab.isBlank()) {
            updateState { withFailure(getClassAndMethod(), "Field Empty!", Errors.FIELD_EMPTY) }
            return
        }
        set()
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            val check = hasRegColab(uiState.value.regColab).getOrThrow()
            if (check) setRegOperator(uiState.value.regColab).getOrThrow()
            check
        }.onSuccess { check ->
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
        }.onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    suspend fun updateAllDatabase(): Flow<OperatorHeaderState> =
        executeUpdateSteps(
            steps = listOf(updateTableColab(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}