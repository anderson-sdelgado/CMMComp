package br.com.usinasantafe.cmm.presenter.view.configuration.initial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.CheckAccessInitial
import br.com.usinasantafe.cmm.domain.usecases.config.GetStatusSend
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
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

    private inline fun updateState(block: InitialMenuState.() -> InitialMenuState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun recoverStatusSend() = viewModelScope.launch {
            runCatching {
                getStatusSend().getOrThrow()
            }.onSuccess { updateState { copy(statusSend = it) }
            }.onFailureHandled(getClassAndMethod(), ::onError)
        }


    fun checkAccess() = viewModelScope.launch {
        runCatching {
            checkAccessInitial().getOrThrow()
        }.onSuccess { updateState { copy(flagDialog = !it, flagAccess = it) }
        }.onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure, flagFailure = true) }


}