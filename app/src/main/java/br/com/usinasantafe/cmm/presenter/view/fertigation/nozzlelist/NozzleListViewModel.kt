package br.com.usinasantafe.cmm.presenter.view.fertigation.nozzlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.presenter.view.motomec.common.activityList.ActivityListCommonState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NozzleListState(
    val listNozzle: List<Nozzle> = emptyList(),
    val flagAccess: Boolean = false,
    override val status: UpdateStatusState = UpdateStatusState()
) : UiStateWithStatus<NozzleListState> {

    override fun copyWithStatus(status: UpdateStatusState): NozzleListState =
        copy(status = status)
}

@HiltViewModel
class NozzleListViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(NozzleListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: NozzleListState.() -> NozzleListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {

    }

    fun setId(id: Int) = viewModelScope.launch {

    }

    fun updateDatabase() = viewModelScope.launch {

    }

}