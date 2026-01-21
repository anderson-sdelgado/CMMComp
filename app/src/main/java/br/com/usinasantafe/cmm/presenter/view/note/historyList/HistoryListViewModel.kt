package br.com.usinasantafe.cmm.presenter.view.note.historyList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListHistory
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverList() = viewModelScope.launch {
        val result = listHistory()
        if(result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val historyList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                historyList = historyList
            )
        }
    }


}