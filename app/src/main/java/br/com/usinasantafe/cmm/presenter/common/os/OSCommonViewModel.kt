package br.com.usinasantafe.cmm.presenter.common.os

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.header.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.header.GetNroOSHeader
import br.com.usinasantafe.cmm.domain.usecases.common.SetNroOSCommon
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.cmm.ui.theme.addTextField
import br.com.usinasantafe.cmm.ui.theme.clearTextField
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OSCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_DEFAULT,
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
)

@HiltViewModel
class OSCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkNroOS: CheckNroOS,
    private val setNroOSCommon: SetNroOSCommon,
    private val getNroOSHeader: GetNroOSHeader
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!

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
                    val failure = "OSCommonViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.FIELDEMPTY,
                            flagAccess = false
                        )
                    }
                    return
                }
                setNroOS()
            }
            TypeButton.UPDATE -> {}
        }
    }

    fun getNroOS() = viewModelScope.launch {
        if(uiState.value.flowApp != FlowApp.HEADER_DEFAULT) {
            val result = getNroOSHeader()
            if (result.isFailure) {
                val error = result.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                        flagProgress = false,
                    )
                }
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
        val resultCheck = checkNroOS(uiState.value.nroOS)
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagProgress = false,
                )
            }
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
        val resultSet = setNroOSCommon(
            nroOS = uiState.value.nroOS,
            flowApp = uiState.value.flowApp
        )
        if(resultSet.isFailure){
            val error = resultSet.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagProgress = false,
                    flagAccess = false
                )
            }
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

}