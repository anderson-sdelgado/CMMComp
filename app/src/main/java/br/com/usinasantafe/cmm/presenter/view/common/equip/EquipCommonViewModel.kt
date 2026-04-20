package br.com.usinasantafe.cmm.presenter.view.common.equip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.cec.SetNroEquipPreCEC
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdEquipMotoMec
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EquipCommonState(
    val flowApp: FlowApp = FlowApp.HEADER_INITIAL,
    val description: String = "",
    val checkClass: Boolean? = null,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class EquipCommonViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val getDescrEquip: GetDescrEquip,
    private val setIdEquipMotoMec: SetIdEquipMotoMec,
    private val setNroEquipPreCEC: SetNroEquipPreCEC
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARG]!!

    private val _uiState = MutableStateFlow(EquipCommonState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: EquipCommonState.() -> EquipCommonState) {
        _uiState.update(block)
    }

    init { updateState { copy(flowApp = FlowApp.entries[this@EquipCommonViewModel.flowApp]) } }

    fun setCloseDialog()  = updateState { copy(flagDialog = false) }

    fun get() = viewModelScope.launch {
        runCatching {
            getDescrEquip().getOrThrow()
        }
            .onSuccess { updateState { copy(description = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun set() = viewModelScope.launch {
        runCatching {
            var check: Boolean? = null
            if(uiState.value.flowApp == FlowApp.HEADER_INITIAL) {
                setIdEquipMotoMec().getOrThrow()
            } else {
                check = setNroEquipPreCEC().getOrThrow()
            }
            check
        }
            .onSuccess { updateState { copy(flagAccess = true, checkClass = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }

}