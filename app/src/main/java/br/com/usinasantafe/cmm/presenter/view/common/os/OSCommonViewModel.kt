package br.com.usinasantafe.cmm.presenter.view.common.os

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
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
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

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp]
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val nroOS = addTextField(uiState.value.nroOS, text)
                _uiState.update {
                    it.copy(nroOS = nroOS)
                }
            }
            TypeButton.CLEAN -> {
                val nroOS = clearTextField(uiState.value.nroOS)
                _uiState.update {
                    it.copy(nroOS = nroOS)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.nroOS.isEmpty()) {
                    handleFailure("Field Empty!", Errors.FIELD_EMPTY)
                    return
                }
                setNroOS()
            }
            TypeButton.UPDATE -> {}
        }
    }

    fun getNroOS() = viewModelScope.launch {
        if(uiState.value.flowApp != FlowApp.HEADER_INITIAL) {
            val result = getNroOSHeader()
            result.onFailure {
                handleFailure(it)
                return@launch
            }
            val nroOS = result.getOrNull()!!
            _uiState.update {
                it.copy(
                    nroOS = nroOS
                )
            }
        }
    }

    private fun setNroOS() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
                msgProgress = "Verificando OS no Web Service",
            )
        }
        val resultCheck = hasNroOS(
            nroOS = uiState.value.nroOS,
            flowApp = uiState.value.flowApp
        )
        resultCheck.onFailure {
            handleFailure(it)
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        if(!check){
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.INVALID,
                    flagProgress = false,
                    flagAccess = false
                )
            }
            return@launch
        }
        val resultSet = setNroOS(
            nroOS = uiState.value.nroOS,
            flowApp = uiState.value.flowApp
        )
        resultSet.onFailure {
            handleFailure(it)
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
                flagDialog = false,
                flagProgress = false,
            )
        }
    }

    private fun handleFailure(failure: String, errors: Errors = Errors.EXCEPTION) {
        val fail = "${getClassAndMethod()} -> $failure"
        Timber.e(fail)
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = fail,
                errors = errors,
                flagAccess = false
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        val failure = "${error.message} -> ${error.cause.toString()}"
        handleFailure(failure)
    }

}