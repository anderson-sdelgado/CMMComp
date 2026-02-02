package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHourMeter
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetHourMeter
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.presenter.theme.addTextFieldComma
import br.com.usinasantafe.cmm.presenter.theme.clearTextFieldComma
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
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
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(HourMeterHeaderState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: HourMeterHeaderState.() -> HourMeterHeaderState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    init { updateState { copy(flowApp = FlowApp.entries[this@HourMeterHeaderViewModel.flowApp]) }}

    fun setTextField(text: String, typeButton: TypeButton) {
        when (typeButton) {
            TypeButton.NUMERIC -> updateState { copy(hourMeter = addTextFieldComma(hourMeter, text)) }
            TypeButton.CLEAN -> updateState { copy(hourMeter = clearTextFieldComma(hourMeter)) }
            TypeButton.OK -> validateAndSet()
            TypeButton.UPDATE -> {}
        }
    }


    private fun validateAndSet() {
        if (uiState.value.hourMeter == "0,0") {
            handleFailure("Field Empty!", getClassAndMethod()) { onError(it, Errors.FIELD_EMPTY) }
            return
        }
        setHourMeterHeader()
    }
    fun setHourMeterHeader() =
        viewModelScope.launch {
            runCatching {
                val model = checkHourMeter(state.hourMeter).getOrThrow()
                if(!model.check){
                    updateState {
                        copy(flagDialog = true, flagAccess = false, errors = Errors.INVALID, hourMeterOld = model.measureBD)
                    }
                    return@launch
                }
                setHourMeter(
                    hourMeter = uiState.value.hourMeter,
                    flowApp = uiState.value.flowApp
                ).getOrThrow()
            }.onSuccess {
                updateState { copy(flagAccess = true, flagDialog = false, flowApp = it) }
            }.onFailureHandled(getClassAndMethod(), ::onError)
        }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState { copy(flagDialog = true, failure = failure, errors = errors) }

}