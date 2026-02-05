package br.com.usinasantafe.cmm.presenter.view.note.performanceList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListPerformance
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.model.ItemPerformanceScreenModel
import br.com.usinasantafe.cmm.presenter.view.common.activityList.ActivityListCommonState
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import br.com.usinasantafe.cmm.utils.onFailureUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PerformanceListState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val performanceList: List<ItemPerformanceScreenModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class PerformanceListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listPerformance: ListPerformance,

) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(PerformanceListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: PerformanceListState.() -> PerformanceListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    init { updateState { copy(flowApp = FlowApp.entries[this@PerformanceListViewModel.flowApp]) }}

    fun performanceList() = viewModelScope.launch {
        runCatching {
            listPerformance().getOrThrow()
        }
            .onSuccess { updateState { copy(performanceList = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = { updateState { copy(flagDialog = true, failure = failure)  } }

}