package br.com.usinasantafe.cmm.presenter.view.motomec.header.turnList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListTurn
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdTurn
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableTurn
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

data class TurnListHeaderState(
    val turnList: List<Turn> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<TurnListHeaderState> {

    override fun copyWithStatus(status: UpdateStatusState): TurnListHeaderState =
        copy(status = status)
}

@HiltViewModel
class TurnListHeaderViewModel @Inject constructor(
    private val listTurn: ListTurn,
    private val setIdTurn: SetIdTurn,
    private val updateTableTurn: UpdateTableTurn
) : ViewModel() {

    private val _uiState = MutableStateFlow(TurnListHeaderState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: TurnListHeaderState.() -> TurnListHeaderState) {
        _uiState.update(block)
    }

    fun setCloseDialog()  = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            listTurn().getOrThrow()
        }
            .onSuccess { updateState { copy(turnList = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun set(id: Int) = viewModelScope.launch {
        runCatching {
            setIdTurn(id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
    }

    suspend fun updateAllDatabase(): Flow<TurnListHeaderState> =
        executeUpdateSteps(
            steps = listOf(updateTableTurn(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}