package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.DelLastRespItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.GetItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.SetRespItemCheckList
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.presenter.view.header.equip.EquipHeaderState
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ItemCheckListState(
    val pos: Int = 0,
    val id: Int = 0,
    val descr: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class ItemCheckListViewModel @Inject constructor(
    private val getItemCheckList: GetItemCheckList,
    private val setRespItemCheckList: SetRespItemCheckList,
    private val delLastRespItemCheckList: DelLastRespItemCheckList
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemCheckListState())
    val uiState = _uiState.asStateFlow()

    private fun updateState(block: ItemCheckListState.() -> ItemCheckListState) {
        _uiState.update(block)
    }

    fun setCloseDialog()  = updateState { copy(flagDialog = false) }

    fun get(pos: Int = 1) =
        viewModelScope.launch {
            runCatching {
                getItemCheckList(pos).getOrThrow()
            }
                .onSuccess { model -> updateState { copy(pos = pos, id = model.id, descr = model.descr) } }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    fun ret() = viewModelScope.launch {
        runCatching {
            if(uiState.value.pos == 1) return@launch
            delLastRespItemCheckList().getOrThrow()
        }
            .onSuccess { get(pos = uiState.value.pos - 1) }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun set(option: OptionRespCheckList) =
        viewModelScope.launch {
            runCatching {
                val check = setRespItemCheckList(
                    pos = uiState.value.pos,
                    id = uiState.value.id,
                    option = option
                ).getOrThrow()
                if (check) {
                    get(pos = uiState.value.pos + 1)
                    return@launch
                }
            }
                .onSuccess { updateState { copy(flagAccess = true) } }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }

}