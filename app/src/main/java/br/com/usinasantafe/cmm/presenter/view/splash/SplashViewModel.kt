package br.com.usinasantafe.cmm.presenter.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.domain.usecases.common.FlowAppOpen
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun startApp() = viewModelScope.launch {
        startWorkManager()
        val result = flowAppOpen()
        result.onFailure {
            handleFailure(it)
            return@launch
        }
        val flowApp = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = true,
                flowApp = flowApp
            )
        }
    }

    private fun handleFailure(failure: String) {
        val fail = "${getClassAndMethod()} -> $failure"
        Timber.e(fail)
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = fail,
                flagAccess = false
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        val failure = "${error.message} -> ${error.cause.toString()}"
        handleFailure(failure)
    }

}