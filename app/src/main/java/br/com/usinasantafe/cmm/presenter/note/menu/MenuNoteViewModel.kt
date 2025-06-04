package br.com.usinasantafe.cmm.presenter.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.note.GetItemMenuList
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowMenu
import br.com.usinasantafe.cmm.utils.TypeView
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MenuNoteState(
    val descrEquip: String = "",
    val menuList: List<ItemMenuModel> = listOf(),
    val textButtonReturn: String = "",
    val flowMenu: FlowMenu = FlowMenu.INVALID,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
)

@HiltViewModel
class MenuNoteViewModel @Inject constructor(
    private val getItemMenuList: GetItemMenuList,
    private val getDescrEquip: GetDescrEquip,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun descrEquip() = viewModelScope.launch {
        val result = getDescrEquip()
        if(result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val descrEquip = result.getOrNull()!!
        _uiState.update {
            it.copy(
                descrEquip = descrEquip
            )
        }
    }

    fun menuList() = viewModelScope.launch {
        val result = getItemMenuList()
        if(result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val list = result.getOrNull()!!
        val itemList = list.filter { it.type == TypeView.ITEM }
        val textButtonReturn = list.filter { it.type == TypeView.BUTTON }[0].title
        val itemMenuModel = itemList.map { entity ->
            ItemMenuModel(
                id = entity.id,
                title = entity.title
            )
        }
        _uiState.update {
            it.copy(
                menuList = itemMenuModel,
                textButtonReturn = textButtonReturn
            )
        }
    }

    fun setSelection(id: Int)  = viewModelScope.launch {
        val flowMenu = when(id) {
            1 -> FlowMenu.WORK
            2 -> FlowMenu.STOP
            else -> FlowMenu.INVALID
        }
        if(flowMenu == FlowMenu.INVALID){
            val failure = "MenuNoteViewModel.setSelection -> Option Invalid!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.INVALID,
                    flagAccess = false
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
                flowMenu = flowMenu
            )
        }
    }

}