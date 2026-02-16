package br.com.usinasantafe.cmm.presenter.view.fertigation.speedlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SpeedListState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SpeedListViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(SpeedListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: SpeedListState.() -> SpeedListState) {
        _uiState.update(block)
    }
    
    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun list() = viewModelScope.launch {

    }

    fun setId(id: Int) = viewModelScope.launch {

    }

    fun updateDatabase() = viewModelScope.launch {

    }

}