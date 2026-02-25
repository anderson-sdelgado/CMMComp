package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.checkList.DelLastRespItem
import br.com.usinasantafe.cmm.domain.usecases.checkList.GetItem
import br.com.usinasantafe.cmm.domain.usecases.checkList.SetRespItem
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val getItem: GetItem,
    private val setRespItem: SetRespItem,
    private val delLastRespItem: DelLastRespItem
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemCheckListState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: ItemCheckListState.() -> ItemCheckListState) {
        _uiState.update(block)
    }


    fun setCloseDialog()  = updateState { copy(flagDialog = false) }

    fun get(pos: Int = 1) =
        viewModelScope.launch {
            runCatching {
                getItem(pos).getOrThrow()
            }
                .onSuccess { model -> updateState { copy(pos = pos, id = model.id, descr = model.descr) } }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    fun ret() = viewModelScope.launch {
        runCatching {
            if(state.pos == 1) return@launch
            delLastRespItem().getOrThrow()
        }
            .onSuccess { get(pos = state.pos - 1) }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun set(option: OptionRespCheckList) =
        viewModelScope.launch {
            runCatching {
                val check = setRespItem(
                    pos = state.pos,
                    id = state.id,
                    option = option
                ).getOrThrow()
                if (check) {
                    get(pos = state.pos + 1)
                    return@launch
                }
            }
                .onSuccess { updateState { copy(flagAccess = true) } }
                .onFailureHandled(getClassAndMethod(), ::onError)
        }

    private fun onError(failure: String) = updateState { copy(flagDialog = true, failure = failure) }

}