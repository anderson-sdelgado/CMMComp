package br.com.usinasantafe.cmm.presenter.view.fertigation.speedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.presenter.view.fertigation.pressurelist.PressureListState
import br.com.usinasantafe.cmm.utils.UiStateWithStatus
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeedListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: SpeedListState.() -> SpeedListState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(status = status.copy(flagDialog = false)) }

    fun list() = viewModelScope.launch {

    }

    fun set(id: Int) = viewModelScope.launch {

    }

    fun updateDatabase() = viewModelScope.launch {

    }

}