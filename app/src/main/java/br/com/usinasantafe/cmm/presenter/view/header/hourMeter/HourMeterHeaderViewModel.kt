package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.CheckOpenCheckList
import br.com.usinasantafe.cmm.domain.usecases.header.CheckHourMeter
import br.com.usinasantafe.cmm.domain.usecases.header.SetHourMeter
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextFieldComma
import br.com.usinasantafe.cmm.presenter.theme.clearTextFieldComma
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

data class HourMeterHeaderState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val hourMeter: String = "0,0",
    val hourMeterOld: String = "0,0",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class HourMeterHeaderViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkHourMeter: CheckHourMeter,
    private val setHourMeter: SetHourMeter,
    private val checkOpenCheckList: CheckOpenCheckList
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(HourMeterHeaderState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp]
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val measure = addTextFieldComma(uiState.value.hourMeter, text)
                _uiState.update {
                    it.copy(hourMeter = measure)
                }
            }

            TypeButton.CLEAN -> {
                val measure = clearTextFieldComma(uiState.value.hourMeter)
                _uiState.update {
                    it.copy(hourMeter = measure)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.hourMeter == "0,0") {
                    val failure = "HourMeterHeaderViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.FIELD_EMPTY,
                            flagAccess = false
                        )
                    }
                    return
                }
                checkHourMeterHeader()
            }

            TypeButton.UPDATE -> {}
        }
    }

    private fun checkHourMeterHeader() =
        viewModelScope.launch {
            val result = checkHourMeter(uiState.value.hourMeter)
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
                        hourMeterOld = model.measureBD
                    )
                }
                return@launch
            }
            setHourMeterHeader()
        }

    fun setHourMeterHeader() =
        viewModelScope.launch {
            val resultSet = setHourMeter(
                hourMeter = uiState.value.hourMeter,
                flowApp = uiState.value.flowApp
            )
            if (resultSet.isFailure) {
                val error = resultSet.exceptionOrNull()!!
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
            if(uiState.value.flowApp == FlowApp.HEADER_FINISH){
                _uiState.update {
                    it.copy(
                        flagDialog = false,
                        flagAccess = true
                    )
                }
                return@launch
            }
            val resultCheckOpenCheckList = checkOpenCheckList()
            if (resultCheckOpenCheckList.isFailure) {
                val error = resultCheckOpenCheckList.exceptionOrNull()!!
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
            val check = resultCheckOpenCheckList.getOrNull()!!
            val flowApp = if(check) FlowApp.CHECK_LIST else uiState.value.flowApp
            _uiState.update {
                it.copy(
                    flagDialog = false,
                    flagAccess = true,
                    flowApp = flowApp
                )
            }
        }

}