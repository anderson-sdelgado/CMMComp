package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckHasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.mechanic.FinishNoteMechanical
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckTypeLastNote
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetFlowEquipNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNoteMotoMec
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.ECM
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.utils.FlowEquipNote
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.utils.PCOMP
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.StatusTranshipment
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeMsg
import br.com.usinasantafe.cmm.utils.TypeNote
import br.com.usinasantafe.cmm.utils.functionListPMM
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.typeListECM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class MenuNoteState(
    val descrEquip: String = "",
    val menuList: List<ItemMenuModel> = listOf(),
    val flowEquipNote: FlowEquipNote = FlowEquipNote.MAIN,
    val idSelection: Int? = null,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.EXCEPTION,
    val flavorApp: String = "",
    val flagDialogCheck: Boolean = false,
    val flagFailure: Boolean = false,
    val typeMsg: TypeMsg = TypeMsg.NOTE_FINISH_MECHANICAL
)

@HiltViewModel
class MenuNoteViewModel @Inject constructor(
    private val listItemMenu: ListItemMenu,
    private val getDescrEquip: GetDescrEquip,
    private val checkHasNoteMotoMec: CheckHasNoteMotoMec,
    private val checkHasNoteOpenMechanic: CheckHasNoteOpenMechanic,
    private val getFlowEquipNoteMotoMec: GetFlowEquipNoteMotoMec,
    private val getStatusTranshipment: GetStatusTranshipment,
    private val checkTypeLastNote: CheckTypeLastNote,
    private val finishNoteMechanical: FinishNoteMechanical,
    private val setNoteMotoMec: SetNoteMotoMec
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
                    flagAccess = false,
                    flagFailure = true
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
                    flagAccess = false,
                    flagFailure = true
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
                    flagAccess = false,
                    flagFailure = true
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

    fun flowEquipNote() = viewModelScope.launch {
        val result = getFlowEquipNoteMotoMec()
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        val flowEquipNote = result.getOrNull()!!
        _uiState.update {
            it.copy(
                flowEquipNote = flowEquipNote,
            )
        }
    }

    fun onButtonReturn() = viewModelScope.launch {
        val result = checkHasNote().await()
        if(result.isFailure) return@launch
        val check = result.getOrNull()!!
        if(!check) {
            val failure = "MenuNoteViewModel.onButtonReturn -> CheckNoteHeaderOpen -> Header without note!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.HEADER_EMPTY,
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
                idSelection = null
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        when(_uiState.value.flavorApp.uppercase()) {
            PMM -> {
                setSelectionPMM(
                    item = item
                )
            }
            ECM -> setSelectionECM(item)
            PCOMP -> {}
        }
    }

    fun setSelectionPMM(item: ItemMenuModel) = viewModelScope.launch {
        val function = functionListPMM.find { it.first == item.function.first }
        if (function == null) {
            val failure = "${getClassAndMethod()} -> idFunction = ${item.function.first} not found in functionListPMM"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.INVALID,
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        if(function.second != FINISH_MECHANICAL){
            val result = checkHasOpenMechanic().await()
            if(result.isFailure) return@launch
            val check = result.getOrNull()!!
            if(check) {
                val failure = "MenuNoteViewModel.setSelection -> checkHasNoteOpenMechanic -> Note mechanic open!"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        errors = Errors.NOTE_MECHANICAL_OPEN,
                        flagAccess = false,
                        flagFailure = true
                    )
                }
                return@launch
            }
        }
        when(function.second){
            FINISH_MECHANICAL -> {
                val result = checkHasOpenMechanic().await()
                if(result.isFailure) return@launch
                val check = result.getOrNull()!!
                if(!check) {
                    val failure = "MenuNoteViewModel.setSelection -> checkHasNoteOpenMechanic -> Note mechanic NOT open!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.NOTE_MECHANICAL_OPEN,
                            flagAccess = false,
                            flagFailure = true
                        )
                    }
                    return@launch
                }
            }
            TRANSHIPMENT -> checkStatusTranshipment()
            IMPLEMENT -> checkHasNoteImplement()
            NOTE_MECHANICAL -> checkLastNote()
        }
        if(!_uiState.value.flagAccess) return@launch
        _uiState.update {
            it.copy(
                flagAccess = true,
                idSelection = item.id
            )
        }
    }

    fun setSelectionECM(item: ItemMenuModel) = viewModelScope.launch {
        val type = typeListECM.find { it.first == item.type.first }
        if (type == null) {
            val failure = "${getClassAndMethod()} -> idType = ${item.type.first} not found in typeListECM"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.INVALID,
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        when(type.second){
            ITEM_NORMAL -> {
                val result = setNoteMotoMec(item)
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
                            flagAccess = false,
                            flagFailure = true
                        )
                    }
                    return@launch
                }
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = false,
                        typeMsg = TypeMsg.ITEM_NORMAL
                    )
                }
            }
        }
    }


    fun checkLastNote() = viewModelScope.launch {
        val result = checkTypeLastNote()
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        val typeNote = result.getOrNull()!!
        if(typeNote == TypeNote.WORK) {
            val failure = "MenuNoteViewModel.setSelection -> CheckLastNote -> Type Last Note is WORK!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.LAST_NOTE_WORK,
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
    }

    fun checkHasOpenMechanic(): Deferred<Result<Boolean>> = viewModelScope.async {
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@async Result.failure(error)
        }
        return@async result
    }

    fun checkStatusTranshipment() = viewModelScope.launch {
        val result = getStatusTranshipment()
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
        val status = result.getOrNull()!!
        if(status == StatusTranshipment.OK) return@launch
        when(status){
            StatusTranshipment.WITHOUT_NOTE -> {
                val failure = "MenuNoteViewModel.setSelection -> CheckStatusTranshipment -> Without note!"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        errors = Errors.WITHOUT_NOTE_TRANSHIPMENT,
                        flagAccess = false,
                        flagFailure = true
                    )
                }
                return@launch
            }
            StatusTranshipment.TIME_INVALID -> {
                val failure = "MenuNoteViewModel.setSelection -> CheckStatusTranshipment -> Time invalid!"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                        errors = Errors.TIME_INVALID_TRANSHIPMENT,
                        flagAccess = false,
                        flagFailure = true
                    )
                }
                return@launch
            }

            else -> {}
        }
    }

    fun checkHasNoteImplement() = viewModelScope.launch {
        val result = checkHasNote().await()
        if(result.isFailure) return@launch
        val flag = result.getOrNull()!!
        if(!flag) {
            val failure = "MenuNoteViewModel.setSelection -> CheckHasNoteImplement -> Header without note!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.HEADER_EMPTY,
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@launch
        }
    }

    fun checkHasNote(): Deferred<Result<Boolean>> = viewModelScope.async {
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
                    flagAccess = false,
                    flagFailure = true
                )
            }
            return@async Result.failure(error)
        }
        return@async result
    }

    fun onDialogCheck(function: Pair<Int, String>) = viewModelScope.launch {
        when(function.second){
            FINISH_MECHANICAL -> {
                val result = finishNoteMechanical()
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
                            flagAccess = false,
                            flagFailure = true
                        )
                    }
                    return@launch
                }
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = false
                    )
                }
            }
        }
    }

}