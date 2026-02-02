package br.com.usinasantafe.cmm.presenter.view.common.activityList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListActivity
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.executeUpdateSteps
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ActivityListCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val activityList: List<Activity> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<ActivityListCommonState> {

    override fun copyWithStatus(status: UpdateStatusState): ActivityListCommonState =
        copy(status = status)
}

@HiltViewModel
class ActivityListCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val listActivity: ListActivity,
    private val updateTableActivity: UpdateTableActivity,
    private val setIdActivityCommon: SetIdActivityCommon
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(ActivityListCommonState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ActivityListCommonState.() -> ActivityListCommonState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    init { updateState { copy(flowApp = FlowApp.entries[this@ActivityListCommonViewModel.flowApp]) } }

    fun activityList() = viewModelScope.launch {
        runCatching {
            listActivity().getOrThrow()
        }
            .onSuccess { updateState { copy(activityList = it) } }
            .onFailure {  throwable ->
                updateState { withFailure(getClassAndMethod(), throwable) }
            }
    }

    fun setIdActivity(id: Int) = viewModelScope.launch {
        runCatching {
            setIdActivityCommon(
                id = id,
                flowApp = uiState.value.flowApp
            ).getOrThrow()
        }
            .onSuccess { updateState { copy(flowApp = it, flagAccess = true) } }
            .onFailure {  throwable ->
                updateState { withFailure(getClassAndMethod(), throwable) }
            }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<ActivityListCommonState> =
        executeUpdateSteps(
            steps = listOf(updateTableActivity(4f, 1f)),
            getState = { _uiState.value },
            getStatus = { it.status },
            copyStateWithStatus = { state, status -> state.copy(status = status) },
            classAndMethod = getClassAndMethod(),
        )

}