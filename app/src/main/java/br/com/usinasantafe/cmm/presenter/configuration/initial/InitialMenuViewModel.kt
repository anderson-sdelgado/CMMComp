package br.com.usinasantafe.cmm.presenter.configuration.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.CheckAccessInitial
import br.com.usinasantafe.cmm.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.cmm.utils.StatusSend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InitialMenuState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
    val failureStatus: String = "",
    val statusSend: StatusSend = StatusSend.STARTED
)

@HiltViewModel
class InitialMenuViewModel @Inject constructor(
    private val getStatusSend: GetStatusSend,
    private val checkAccessInitial: CheckAccessInitial,
) : ViewModel() {

    private val tag = javaClass.simpleName

    private val _uiState = MutableStateFlow(InitialMenuState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverStatusSend() {
        viewModelScope.launch {
            val resultGetStatus = getStatusSend()
            if(resultGetStatus.isFailure){
                val error = resultGetStatus.exceptionOrNull()!!
                val failure =
                    "${tag}.recoverStatusSend -> GetStatusSend -> ${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        failureStatus = failure
                    )
                }
                return@launch
            }
            val result = resultGetStatus.getOrNull()!!
            _uiState.update {
                it.copy(
                    statusSend = result
                )
            }
        }
    }

    fun checkAccess() {
        viewModelScope.launch {
            val resultCheck = checkAccessInitial()
            if(resultCheck.isFailure){
                val error = resultCheck.exceptionOrNull()!!
                val failure =
                    "${tag}.checkAccess -> CheckAccessInitial -> ${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagAccess = false,
                        flagFailure = true,
                        failure = failure
                    )
                }
                return@launch
            }
            val statusAccess = resultCheck.getOrNull()!!
            val statusDialog = !statusAccess
            _uiState.update {
                it.copy(
                    flagDialog = statusDialog,
                    flagAccess = statusAccess,
                    flagFailure = false,
                )
            }
        }
    }
}