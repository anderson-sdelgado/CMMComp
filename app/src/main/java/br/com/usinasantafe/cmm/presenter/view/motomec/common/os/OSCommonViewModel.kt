package br.com.usinasantafe.cmm.presenter.view.motomec.common.os

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetNroOSHeader
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroOS
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextField
import br.com.usinasantafe.cmm.presenter.theme.clearTextField
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.MSG_CHECK_OS
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OSCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
)

@HiltViewModel
class OSCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val hasNroOS: HasNroOS,
    private val setNroOS: SetNroOS,
    private val getNroOSHeader: GetNroOSHeader
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(OSCommonState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: OSCommonState.() -> OSCommonState) {
        _uiState.update(block)
    }

    init { updateState { copy(flowApp = FlowApp.entries[this@OSCommonViewModel.flowApp]) } }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(nroOS = addTextField(nroOS, text)) }
            TypeButton.CLEAN -> updateState { copy(nroOS = clearTextField(nroOS)) }
            TypeButton.OK -> set()
            TypeButton.UPDATE -> {}
        }
    }

    fun get() = viewModelScope.launch {
        if(uiState.value.flowApp != FlowApp.HEADER_INITIAL) {
            runCatching {
                getNroOSHeader().getOrThrow()
            }
                .onSuccess{ updateState { copy(nroOS = it) } }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }
    }

    private fun set() = viewModelScope.launch {
        runCatching {
            if (state.nroOS.isBlank()) {
                handleFailure(getClassAndMethod(), Errors.FIELD_EMPTY, ::onError)
                return@launch
            }
            updateState { copy(flagProgress = true, msgProgress = MSG_CHECK_OS) }
            val check = hasNroOS(nroOS = uiState.value.nroOS, flowApp = uiState.value.flowApp).getOrThrow()
            if(!check){
                handleFailure(getClassAndMethod(), Errors.INVALID, ::onError)
                return@launch
            }
            setNroOS(nroOS = uiState.value.nroOS, flowApp = uiState.value.flowApp).getOrThrow()
        }
            .onSuccess {
                updateState { copy(flagAccess = true, flagDialog = false, flagProgress = false) }
            }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String, error: Errors = Errors.EXCEPTION) = updateState {
        copy(flagDialog = true, failure = failure, errors = error, flagAccess = false, flagProgress = false)
    }

}