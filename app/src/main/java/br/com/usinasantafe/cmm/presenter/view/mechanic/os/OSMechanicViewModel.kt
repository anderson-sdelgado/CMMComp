package br.com.usinasantafe.cmm.presenter.view.mechanic.os

import androidx.lifecycle.ViewModel
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckNroOS
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.view.motomec.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val checkNroOS: CheckNroOS
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

    }

    suspend fun updateAllDatabase(): Flow<OSMechanicState> =
        executeUpdateSteps(
            steps = listOf(checkNroOS(_uiState.value.nroOS, 4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}