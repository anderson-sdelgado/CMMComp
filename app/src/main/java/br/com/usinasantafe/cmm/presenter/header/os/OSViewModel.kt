package br.com.usinasantafe.cmm.presenter.header.os

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.header.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.header.SetNroOS
import br.com.usinasantafe.cmm.ui.theme.addTextField
import br.com.usinasantafe.cmm.ui.theme.clearTextField
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TypeButton
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OSState(
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
)

@HiltViewModel
class OSViewModel @Inject constructor(
    private val checkNroOS: CheckNroOS,
    private val setNroOS: SetNroOS
) : ViewModel() {

    private val _uiState = MutableStateFlow(OSState())
    val uiState = _uiState.asStateFlow()

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
                    val failure = "OSViewModel.setTextField.OK -> Field Empty!"
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
                setNroOSHeader()
            }
            TypeButton.UPDATE -> {}
        }
    }

    private fun setNroOSHeader() = viewModelScope.launch {
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
        val resultSet = setNroOS(uiState.value.nroOS)
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