package br.com.usinasantafe.cmm.presenter.header.measure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.header.CheckMeasure
import br.com.usinasantafe.cmm.domain.usecases.header.SetMeasureInitial
import br.com.usinasantafe.cmm.ui.theme.addTextFieldComma
import br.com.usinasantafe.cmm.ui.theme.clearTextFieldComma
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

data class MeasureHeaderState(
    val measure: String = "0,0",
    val measureOld: String = "0,0",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
)

@HiltViewModel
class MeasureHeaderViewModel @Inject constructor(
    private val checkMeasure: CheckMeasure,
    private val setMeasureInitial: SetMeasureInitial
) : ViewModel() {

    private val _uiState = MutableStateFlow(MeasureHeaderState())
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
                val measure = addTextFieldComma(uiState.value.measure, text)
                _uiState.update {
                    it.copy(measure = measure)
                }
            }

            TypeButton.CLEAN -> {
                val measure = clearTextFieldComma(uiState.value.measure)
                _uiState.update {
                    it.copy(measure = measure)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.measure == "0,0") {
                    val failure = "MeasureHeaderViewModel.setTextField.OK -> Field Empty!"
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
                checkMeasureHeader()
            }

            TypeButton.UPDATE -> {}
        }
    }

    private fun checkMeasureHeader() =
        viewModelScope.launch {
            val result = checkMeasure(uiState.value.measure)
            if (result.isFailure) {
                val error = result.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        errors = Errors.EXCEPTION,
                        flagAccess = false
                    )
                }
                return@launch
            }
            val model = result.getOrNull()!!
            if(!model.check){
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagAccess = false,
                        errors = Errors.INVALID,
                        measureOld = model.measureBD
                    )
                }
                return@launch
            }
            setMeasureInitialHeader()
        }

    fun setMeasureInitialHeader() =
        viewModelScope.launch {
            val result = setMeasureInitial(uiState.value.measure)
            if (result.isFailure) {
                val error = result.exceptionOrNull()!!
                val failure =
                    "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        errors = Errors.EXCEPTION,
                        flagAccess = false
                    )
                }
                return@launch
            }
            _uiState.update {
                it.copy(
                    flagDialog = false,
                    flagAccess = true,
                )
            }
        }

}