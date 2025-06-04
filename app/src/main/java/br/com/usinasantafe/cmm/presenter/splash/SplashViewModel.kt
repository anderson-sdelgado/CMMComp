package br.com.usinasantafe.cmm.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.header.CheckHeaderOpen
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SplashState(
    val flagHeaderOpen: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkHeaderOpen: CheckHeaderOpen,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun checkOpen() = viewModelScope.launch {
        val result = checkHeaderOpen()
        if(result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagAccess = false,
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val check = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = true,
                flagHeaderOpen = check
            )
        }
    }

}