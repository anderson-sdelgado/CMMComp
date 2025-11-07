package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckHasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckTypeHeaderMotoMec
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.presenter.model.functionListPMM
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FINISH_MECHANICAL
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
    val checkButton: Boolean = true,
    val function: Pair<Int, String>? = null,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.EXCEPTION,
    val flavorApp: String = "",
)

@HiltViewModel
class MenuNoteViewModel @Inject constructor(
    private val listItemMenu: ListItemMenu,
    private val getDescrEquip: GetDescrEquip,
    private val checkHasNoteMotoMec: CheckHasNoteMotoMec,
    private val checkHasNoteOpenMechanic: CheckHasNoteOpenMechanic,
    private val checkTypeHeaderMotoMec: CheckTypeHeaderMotoMec
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuNoteState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun menuList(flavor: String) = viewModelScope.launch {
        _uiState.update {
            it.copy(flavorApp = flavor)
        }
        val result = listItemMenu(flavor)
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
        val menuList = result.getOrNull()!!
        if(menuList.isEmpty()){
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
        _uiState.update {
            it.copy(
                menuList = menuList,
            )
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

    fun checkButton() = viewModelScope.launch {
        val result = checkTypeHeaderMotoMec()
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
        val check = result.getOrNull()!!
        _uiState.update {
            it.copy(
                checkButton = check,
            )
        }
    }

    fun setSelection(id: Int)  = viewModelScope.launch {
        val item = _uiState.value.menuList.find { it.id == id }
        if (item == null) {
            val failure = "${getClassAndMethod()} -> Item with id = $id not found in menuList"
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
        val function = functionListPMM.find { it.first == item.function.first }
        if (function == null) {
            val failure = "${getClassAndMethod()} -> idFunction = ${item.function.first} not found in functionListPMM"
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
        if (function.second != FINISH_MECHANICAL){
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
                function = function
            )
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
            )
        }
    }

}