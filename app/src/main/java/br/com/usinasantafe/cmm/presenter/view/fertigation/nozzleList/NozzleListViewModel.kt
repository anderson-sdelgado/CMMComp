package br.com.usinasantafe.cmm.presenter.view.fertigation.nozzleList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListNozzle
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetIdNozzle
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableNozzle
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
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

data class NozzleListState(
    val list: List<Nozzle> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<NozzleListState> {

    override fun copyWithStatus(status: UpdateStatusState): NozzleListState =
        copy(status = status)
}

@HiltViewModel
class NozzleListViewModel @Inject constructor(
    private val listNozzle: ListNozzle,
    private val updateTableNozzle: UpdateTableNozzle,
    private val setIdNozzle: SetIdNozzle
) : ViewModel() {

    private val _uiState = MutableStateFlow(NozzleListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: NozzleListState.() -> NozzleListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            listNozzle().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun set(id: Int) = viewModelScope.launch {
        runCatching {
            setIdNozzle(id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
    }

    suspend fun updateAllDatabase(): Flow<NozzleListState> =
        executeUpdateSteps(
            steps = listOf(updateTableNozzle(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )
}