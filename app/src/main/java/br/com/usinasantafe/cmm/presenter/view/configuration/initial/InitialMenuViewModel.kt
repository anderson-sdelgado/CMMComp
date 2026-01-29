package br.com.usinasantafe.cmm.presenter.view.configuration.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.CheckAccessInitial
import br.com.usinasantafe.cmm.domain.usecases.config.GetStatusSend
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class InitialMenuState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
    val statusSend: StatusSend = StatusSend.STARTED
)

@HiltViewModel
class InitialMenuViewModel @Inject constructor(
    private val getStatusSend: GetStatusSend,
    private val checkAccessInitial: CheckAccessInitial,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InitialMenuState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverStatusSend() = viewModelScope.launch {
            runCatching {
                getStatusSend().getOrThrow()
            }.onSuccess { statusSend ->
                _uiState.update {
                    it.copy(
                        statusSend = statusSend
                    )
                }
            }.onFailure {
                handleFailure(it)
            }
        }


    fun checkAccess() = viewModelScope.launch {
        runCatching {
            checkAccessInitial().getOrThrow()
        }.onSuccess { check ->
            _uiState.update {
                it.copy(
                    flagDialog = !check,
                    flagAccess = check,
                    flagFailure = false,
                )
            }
        }.onFailure {
            handleFailure(it)
        }
    }

    private fun handleFailure(failure: String) {
        val fail = "${getClassAndMethod()} -> $failure"
        Timber.e(fail)
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = fail,
                flagAccess = false,
                flagFailure = true
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        val failure = "${error.message} -> ${error.cause.toString()}"
        handleFailure(failure)
    }

}