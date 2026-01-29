package br.com.usinasantafe.cmm.presenter.view.configuration.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.config.CheckPassword
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class PasswordState(
    val password: String = "",
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val checkPassword: CheckPassword
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun checkAccess() =
        viewModelScope.launch {
            runCatching {
                checkPassword(_uiState.value.password).getOrThrow()
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