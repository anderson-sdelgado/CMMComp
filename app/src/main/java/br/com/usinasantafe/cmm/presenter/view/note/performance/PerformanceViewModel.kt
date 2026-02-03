package br.com.usinasantafe.cmm.presenter.view.note.performance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.Args.ID_ARG
import br.com.usinasantafe.cmm.presenter.view.header.hourMeter.HourMeterHeaderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PerformanceState(
    val id: Int = 0,
    val nroOS: String = "",
    val performance: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class PerformanceViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARG]!!

    private val _uiState = MutableStateFlow(PerformanceState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: PerformanceState.() -> PerformanceState) {
        _uiState.update(block)
    }

    init { updateState { copy(id = this@PerformanceViewModel.id) }}

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun setTextField(text: String, typeButton: TypeButton) {

    }

}