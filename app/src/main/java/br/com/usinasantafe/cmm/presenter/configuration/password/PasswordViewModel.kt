package br.com.usinasantafe.cmm.presenter.configuration.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.config.CheckPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val tag = javaClass.simpleName

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
            val resultCheck = checkPassword(
                password = _uiState.value.password
            )
            if(resultCheck.isFailure) {
                val error = resultCheck.exceptionOrNull()!!
                val failure =
                    "${tag}.checkAccess -> CheckPassword -> ${error.message} -> ${error.cause.toString()}"
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