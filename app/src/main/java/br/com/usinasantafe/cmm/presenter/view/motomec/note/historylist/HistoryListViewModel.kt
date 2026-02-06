package br.com.usinasantafe.cmm.presenter.view.motomec.note.historylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListHistory
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryListState(
    val historyList: List<ItemHistoryScreenModel> = emptyList(),
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class HistoryListViewModel @Inject constructor(
    private val listHistory: ListHistory
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: HistoryListState.() -> HistoryListState) {
        _uiState.update(block)
    }

    fun setCloseDialog()  = updateState { copy(flagDialog = false) }

    fun recoverList() = viewModelScope.launch {
        runCatching {
            listHistory().getOrThrow()
        }
            .onSuccess { updateState { copy(historyList = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }


}