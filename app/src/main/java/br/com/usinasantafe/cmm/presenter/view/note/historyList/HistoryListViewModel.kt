package br.com.usinasantafe.cmm.presenter.view.note.historyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class HistoryListState(
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class HistoryListViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun historyList() = viewModelScope.launch {

    }


}