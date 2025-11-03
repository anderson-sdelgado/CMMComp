package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.common.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckHasNoteOpenMechanic
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowNote
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
    val flowNote: FlowNote = FlowNote.WORK,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.EXCEPTION,
)

@HiltViewModel
class MenuNoteViewModel @Inject constructor(
    private val listItemMenu: ListItemMenu,
    private val getDescrEquip: GetDescrEquip,
    private val checkHasNoteMotoMec: CheckHasNoteMotoMec,
    private val checkHasNoteOpenMechanic: CheckHasNoteOpenMechanic
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
                    failure = failure,
                    errors = Errors.EXCEPTION,
                    flagAccess = false
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
        val result = listItemMenu()
        if(result.isFailure) {
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
        val list = result.getOrNull()!!
        if(list.isEmpty()){
            val failure = "MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!"
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
        try {
            val flowNote = FlowNote.entries[id - 1]
            if(flowNote != FlowNote.FINISH_MECHANICAL) {
                val result = checkHasNoteOpenMechanic()
                if(result.isFailure) {
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
                if(result.getOrNull() == true) {
                    val failure = "MenuNoteViewModel.setSelection -> checkHasNoteOpenMechanic -> Note mechanic open!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.NOTE_MECHANICAL_OPEN,
                            flagAccess = false
                        )
                    }
                    return@launch
                }
            }
            _uiState.update {
                it.copy(
                    flagAccess = true,
                    flowNote = flowNote
                )
            }
        } catch (e: Exception) {
            val failure =
                "${getClassAndMethod()} -> Option Invalid! -> ${e.message}"
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
    }

    fun onButtonReturn() = viewModelScope.launch {
        val result = checkHasNoteMotoMec()
        if(result.isFailure) {
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
        val flag = result.getOrNull()!!
        if(!flag) {
            val failure = "MenuNoteViewModel.onButtonReturn -> CheckNoteHeaderOpen -> Header empty!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.HEADER_EMPTY,
                    flagAccess = false
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
                flowNote = FlowNote.FINISH_HEADER
            )
        }
    }

}