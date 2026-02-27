package br.com.usinasantafe.cmm.presenter.view.mechanic.os

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetNroOS
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OSMechanicState(
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<OSMechanicState> {

    override fun copyWithStatus(status: UpdateStatusState): OSMechanicState =
        copy(status = status)
}

@HiltViewModel
class OSMechanicViewModel @Inject constructor(
    private val checkNroOS: CheckNroOS,
    private val setNroOS: SetNroOS
) : ViewModel() {

    private val _uiState = MutableStateFlow(OSMechanicState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: OSMechanicState.() -> OSMechanicState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false, flagFailure = false)) }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when(typeButton) {
            TypeButton.NUMERIC -> updateState { copy(nroOS = addTextField(nroOS, text)) }
            TypeButton.CLEAN -> updateState { copy(nroOS = clearTextField(nroOS)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {}
        }
    }


    private fun set() = viewModelScope.launch {
        checkAndUpdate()
            .onCompletion {
                if(!state.status.flagFailure || !state.status.flagProgress) {
                    setNroOS(state.nroOS).fold(
                        onSuccess = {
                            emit(
                                state.copy(
                                    flagAccess = true,
                                    status = state.status.copy(
                                        flagProgress = false
                                    )
                                )
                            )
                        },
                        onFailure = {
                            val newState = state.withFailure(getClassAndMethod(), it)
                            emit(newState)
                            _uiState.value = newState
                        }
                    )
                }
            }
            .collect {
                _uiState.value = it
            }
    }

    suspend fun checkAndUpdate(): Flow<OSMechanicState> =
        executeUpdateSteps(
            steps = listOf(checkNroOS(_uiState.value.nroOS)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
            flagUpdateFinish = false
        )

}