package br.com.usinasantafe.cmm.presenter.view.fertigation.pressureList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListPressure
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetValuePressure
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTablePressure
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

data class PressureListState(
    val list: List<Double> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<PressureListState> {

    override fun copyWithStatus(status: UpdateStatusState): PressureListState =
        copy(status = status)
}

@HiltViewModel
class PressureListViewModel @Inject constructor(
    private val listPressure: ListPressure,
    private val setValuePressure: SetValuePressure,
    private val updateTablePressure: UpdateTablePressure
) : ViewModel() {

    private val _uiState = MutableStateFlow(PressureListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: PressureListState.() -> PressureListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            listPressure().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun set(value: Double) = viewModelScope.launch {
        runCatching {
            setValuePressure(value).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
    }

    suspend fun updateAllDatabase(): Flow<PressureListState> =
        executeUpdateSteps(
            steps = listOf(updateTablePressure(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )
}