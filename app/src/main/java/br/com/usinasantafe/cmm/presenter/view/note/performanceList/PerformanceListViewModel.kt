package br.com.usinasantafe.cmm.presenter.view.note.performanceList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.presenter.model.ItemPerformanceScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PerformanceListState(
    val performanceList: List<ItemPerformanceScreenModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class PerformanceListViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerformanceListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }


    fun performanceList() = viewModelScope.launch {

    }

    private fun handleFailure(failure: String, errors: Errors = Errors.EXCEPTION) {
        val fail = "${getClassAndMethod()} -> $failure"
        Timber.e(fail)
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = fail,
                flagAccess = false,
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        val failure = "${error.message} -> ${error.cause.toString()}"
        handleFailure(failure)
    }
}