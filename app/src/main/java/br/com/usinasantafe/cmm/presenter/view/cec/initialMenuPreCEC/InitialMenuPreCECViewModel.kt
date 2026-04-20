package br.com.usinasantafe.cmm.presenter.view.cec.initialMenuPreCEC

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.cec.CheckAccessCertificate
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InitialMenuPreCECState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class InitialMenuPreCECViewModel @Inject constructor(
    private val checkAccessCertificate: CheckAccessCertificate
) : ViewModel() {

    private val _uiState = MutableStateFlow(InitialMenuPreCECState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: InitialMenuPreCECState.() -> InitialMenuPreCECState) {
        _uiState.update(block)
    }
    
    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun checkAccess() = viewModelScope.launch {
        runCatching {
            checkAccessCertificate().getOrThrow()
        }
            .onSuccess { updateState { copy(flagDialog = !it, flagAccess = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }

}