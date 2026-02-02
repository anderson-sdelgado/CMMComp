package br.com.usinasantafe.cmm.presenter.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.domain.usecases.common.FlowAppOpen
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val startWorkManager: StartWorkManager,
    private val flowAppOpen: FlowAppOpen,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    private inline fun updateState(block: SplashState.() -> SplashState) {
        _uiState.update { it.block() }
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun startApp() = viewModelScope.launch {
        runCatching {
            startWorkManager()
            flowAppOpen().getOrThrow()
        }
            .onSuccess(::onSuccess)
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onSuccess(flowApp: FlowApp) = updateState { copy(flagAccess = true, flowApp = flowApp) }
    private fun onError(error: String) = updateState { copy(flagDialog = true, failure = error) }

}