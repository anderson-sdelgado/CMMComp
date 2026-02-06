package br.com.usinasantafe.cmm.presenter.view.motomec.header.equip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdEquip
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EquipHeaderState(
    val description: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class EquipHeaderViewModel @Inject constructor(
    private val getDescrEquip: GetDescrEquip,
    private val setIdEquip: SetIdEquip
) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipHeaderState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: EquipHeaderState.() -> EquipHeaderState) {
        _uiState.update(block)
    }

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
            setIdEquip().getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }

}