package br.com.usinasantafe.cmm.presenter.view.fertigation.reelList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListHeaderSec
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetHeaderEquipMain
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetHeaderPointing
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.presenter.model.ItemDefaultScreenModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReelListFertigationState(
    val list: List<ItemDefaultScreenModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class ReelListFertigationViewModel @Inject constructor(
    private val listHeaderSec: ListHeaderSec,
    private val setHeaderPointing: SetHeaderPointing,
    private val setHeaderEquipMain: SetHeaderEquipMain
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReelListFertigationState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ReelListFertigationState.() -> ReelListFertigationState) {
        _uiState.update(block)
    }

    fun setCloseDialog() = updateState { copy(flagDialog = false) }

    fun list() = viewModelScope.launch {
        runCatching {
            listHeaderSec().getOrThrow()
        }
            .onSuccess { updateState { copy(list = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun set(id: Int) = viewModelScope.launch {
        runCatching {
            setHeaderPointing(id).getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun ret()  = viewModelScope.launch {
        runCatching {
            setHeaderEquipMain().getOrThrow()
        }
            .onSuccess { updateState { copy(flagAccess = true) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState { copy(flagDialog = true, failure = failure, errors = errors) }

}