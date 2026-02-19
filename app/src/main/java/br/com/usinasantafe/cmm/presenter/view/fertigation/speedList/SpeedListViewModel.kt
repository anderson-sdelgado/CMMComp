package br.com.usinasantafe.cmm.presenter.view.fertigation.speedList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListSpeed
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetSpeedPressure
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

data class SpeedListState(
    val list: List<Pressure> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<SpeedListState> {

    override fun copyWithStatus(status: UpdateStatusState): SpeedListState =
        copy(status = status)
}

@HiltViewModel
class SpeedListViewModel @Inject constructor(
    private val listSpeed: ListSpeed,
    private val updateTablePressure: UpdateTablePressure,
    private val setSpeedPressure: SetSpeedPressure
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeedListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: SpeedListState.() -> SpeedListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {
        runCatching {
            listSpeed().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun set(speed: Int) = viewModelScope.launch {
        runCatching {
            setSpeedPressure(speed).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureUpdate(getClassAndMethod(), ::updateState)
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch { updateAllDatabase().collect { _uiState.value = it } }
    }

    suspend fun updateAllDatabase(): Flow<SpeedListState> =
        executeUpdateSteps(
            steps = listOf(updateTablePressure(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )
}