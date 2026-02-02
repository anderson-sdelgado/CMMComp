package br.com.usinasantafe.cmm.presenter.view.configuration.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.config.CheckPassword
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
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

    private inline fun updateState(block: PasswordState.() -> PasswordState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun updatePassword(password: String) = updateState { copy(password = password) }

    fun checkAccess() =
        viewModelScope.launch {
            runCatching {
                checkPassword(_uiState.value.password).getOrThrow()
            }
                .onSuccess (::onSuccess)
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    private fun onSuccess(check: Boolean) = updateState { copy(flagDialog = !check, flagAccess = check) }
    private fun onError(error: String) = updateState { copy(flagDialog = true, failure = error, flagFailure = true ) }

}