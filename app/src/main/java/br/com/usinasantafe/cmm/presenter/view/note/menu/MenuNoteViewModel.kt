package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.cec.GetStatusPreCEC
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.composting.CheckWill
import br.com.usinasantafe.cmm.domain.usecases.composting.CheckInitialLoading
import br.com.usinasantafe.cmm.domain.usecases.composting.GetFlowComposting
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckHasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.mechanic.FinishNoteMechanical
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckCouplingTrailer
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckTypeLastNote
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetFlowEquipNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNoteMotoMec
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.ARRIVAL_FIELD
import br.com.usinasantafe.cmm.utils.CHECK_WILL
import br.com.usinasantafe.cmm.utils.COUPLING_TRAILER
import br.com.usinasantafe.cmm.utils.ECM
import br.com.usinasantafe.cmm.utils.EXIT_MILL
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.utils.FlowComposting
import br.com.usinasantafe.cmm.utils.FlowEquipNote
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.utils.PCOMP
import br.com.usinasantafe.cmm.utils.PMM
import br.com.usinasantafe.cmm.utils.RETURN_MILL
import br.com.usinasantafe.cmm.utils.StatusPreCEC
import br.com.usinasantafe.cmm.utils.StatusTranshipment
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeMsg
import br.com.usinasantafe.cmm.utils.TypeNote
import br.com.usinasantafe.cmm.utils.UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.utils.UNLOADING_INPUT
import br.com.usinasantafe.cmm.utils.WEIGHING
import br.com.usinasantafe.cmm.utils.functionListPMM
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.typeListECM
import br.com.usinasantafe.cmm.utils.typeListPCOMPCompound
import br.com.usinasantafe.cmm.utils.typeListPCOMPInput
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
    private val setNoteMotoMec: SetNoteMotoMec,
    private val getStatusPreCEC: GetStatusPreCEC,
    private val checkCouplingTrailer: CheckCouplingTrailer,
    private val getFlowComposting: GetFlowComposting,
    private val checkInitialLoading: CheckInitialLoading,
    private val checkWill: CheckWill
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuNoteState())
    val uiState = _uiState.asStateFlow()

    fun onCloseDialog() = _uiState.update { it.copy(flagDialog = false) }

    fun onCloseDialogCheck() = _uiState.update { it.copy(flagDialogCheck = false) }

    fun menuList(flavor: String) = viewModelScope.launch {
        _uiState.update { it.copy(flavorApp = flavor.uppercase()) }
        val result = listItemMenu(flavor.uppercase())
        result.onSuccess { menuList ->
            if (menuList.isEmpty()) {
                handleFailure("MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")
            } else {
                _uiState.update { it.copy(menuList = menuList) }
            }
        }.onFailure(::handleFailure)
    }

    fun descrEquip() = viewModelScope.launch {
        getDescrEquip().onSuccess { descr ->
            _uiState.update { it.copy(descrEquip = descr) }
        }.onFailure(::handleFailure)
    }

    fun flowEquipNote() = viewModelScope.launch {
        getFlowEquipNoteMotoMec().onSuccess { flow ->
            _uiState.update { it.copy(flowEquipNote = flow) }
        }.onFailure(::handleFailure)
    }

    fun onButtonReturn() = viewModelScope.launch {
        if (checkHasNote() == false) {
            handleFailure(
                failure = "MenuNoteViewModel.onButtonReturn -> checkHasNote -> Header without note!",
                errors = Errors.HEADER_EMPTY
            )
            return@launch
        }
        _uiState.update { it.copy(flagAccess = true, idSelection = null) }
    }

    fun onDialogCheck(function: Pair<Int, String>) = viewModelScope.launch {
        onCloseDialogCheck()
        when (function.second) {
            FINISH_MECHANICAL -> handleFinishMechanicalDialog()
            RETURN_MILL -> {
                _uiState.update { it.copy(flagAccess = true) }
            }
        }
    }

    fun setSelection(id: Int)  = viewModelScope.launch {
        val item = _uiState.value.menuList.find { it.id == id }
        if (item == null) {
            handleFailure("Item with id = $id not found in menuList", Errors.INVALID)
            return@launch
        }
        _uiState.update { it.copy(idSelection = item.id) }
        when (_uiState.value.flavorApp.uppercase()) {
            PMM -> handleSelectionPMM(item)
            ECM -> handleSelectionECM(item)
            PCOMP -> handleSelectionPCOM(item)
        }
    }

    private suspend fun handleSelectionPMM(item: ItemMenuModel) {
        val function = functionListPMM.find { it.first == item.function.first }
        if (function == null) {
            handleFailure("idFunction = ${item.function.first} not found in functionListPMM", Errors.INVALID)
            return
        }

        if (function.second != FINISH_MECHANICAL && checkHasOpenMechanic() == true) {
            handleFailure("Note mechanic open!", Errors.NOTE_MECHANICAL_OPEN)
            return
        }

        val allChecksPassed = when (function.second) {
            FINISH_MECHANICAL -> {
                handleFinishMechanical()
                false
            }
            TRANSHIPMENT -> handleTranshipment()
            IMPLEMENT -> handleImplement()
            NOTE_MECHANICAL -> handleNoteMechanical()
            UNCOUPLING_TRAILER -> checkTrailer()
            COUPLING_TRAILER -> checkTrailer() != true
            else -> true
        }

        if (allChecksPassed != null && allChecksPassed) {
            _uiState.update { it.copy(flagAccess = true) }
        }
    }

    private suspend fun handleSelectionECM(item: ItemMenuModel) {
        val type = typeListECM.find { it.first == item.type.first }
        if (type == null) {
            handleFailure("idType = ${item.type.first} not found in typeListECM", Errors.INVALID)
            return
        }
        val allChecksPassed = when (type.second) {
            ITEM_NORMAL,
            WEIGHING -> {
                handleSetNote(item)
                false
            }
            EXIT_MILL -> {
                handleExitWill()
                false
            }
            ARRIVAL_FIELD -> {
                handleArrivalField(item)
                false
            }
            RETURN_MILL -> {
                handleReturnMill()
                false
            }
            else -> true
        }

        if (allChecksPassed) {
            _uiState.update { it.copy(flagAccess = true) }
        }
    }

    private suspend fun handleSelectionPCOM(item: ItemMenuModel) {
        val flow = getFlowComposting().getOrElse {
            handleFailure(it)
            return
        }
        val type = when(flow){
            FlowComposting.INPUT -> typeListPCOMPInput.find { it.first == item.type.first }
            FlowComposting.COMPOUND -> typeListPCOMPCompound.find { it.first == item.type.first }
        }
        if (type == null) {
            handleFailure("idType = ${item.type.first} not found in typeListECM", Errors.INVALID)
            return
        }
        val allChecksPassed = when (type.second) {
            ITEM_NORMAL,
            WEIGHING -> {
                handleSetNote(item)
                false
            }

            UNLOADING_INPUT -> handleLoadingInput()
            CHECK_WILL -> handleShowInfoWill()
            else -> true
        }

        if (allChecksPassed) {
            _uiState.update { it.copy(flagAccess = true) }
        }
    }

    private suspend fun handleShowInfoWill(): Boolean {
        val check = checkWill().getOrElse {
            handleFailure(it)
            return false
        }
        if (!check) {
            handleFailure("Without will!", Errors.WITHOUT_LOADING_COMPOSTING)
            return false
        }
        return true
    }

    private suspend fun handleLoadingInput(): Boolean {
        val check = checkInitialLoading().getOrElse {
            handleFailure(it)
            return false
        }
        if (!check) {
            handleFailure("Without loading input!", Errors.WITHOUT_LOADING_INPUT)
            return false
        }
        return true
    }

    private suspend fun checkTrailer(): Boolean? {
        val result = checkCouplingTrailer()
        result.onFailure {
            handleFailure(it)
            return null
        }
        return result.getOrNull()!!
    }

    private fun handleReturnMill(){
        _uiState.update {
            it.copy(flagDialogCheck = true, typeMsg = TypeMsg.RETURN_MILL)
        }
    }

    private suspend fun handleSetNote(item: ItemMenuModel) {
        setNoteMotoMec(item).onFailure {
            handleFailure(it)
            return
        }
        _uiState.update {
            it.copy(
                flagDialog = true,
                flagFailure = false,
                typeMsg = TypeMsg.ITEM_NORMAL,
                idSelection = item.id
            )
        }
    }

    private suspend fun handleArrivalField(item: ItemMenuModel) {
        val status = getStatusPreCEC().getOrElse {
            handleFailure(it)
            return
        }
        when (status) {
            StatusPreCEC.EMPTY -> {
                handleFailure("PRE CEC without exit mill", Errors.WITHOUT_EXIT_MILL_PRE_CEC)
            }
            StatusPreCEC.ARRIVAL_FIELD -> {
                handleFailure("PRE CEC with arrival field", Errors.WITH_ARRIVAL_FIELD_PRE_CEC)
            }
            StatusPreCEC.EXIT_MILL -> handleSetNote(item)
        }
    }

    private suspend fun handleExitWill(): Boolean {
        val status = getStatusPreCEC().getOrElse {
            handleFailure(it)
            return false
        }
        if (status != StatusPreCEC.EMPTY) {
            handleFailure("PRE CEC already started!", Errors.PRE_CEC_STARTED)
            return false
        }
        return true
    }

    private suspend fun handleNoteMechanical(): Boolean {
        val typeNote = checkTypeLastNote().getOrElse {
            handleFailure(it)
            return false
        }
        if (typeNote == TypeNote.WORK) {
            handleFailure("Type Last Note is WORK!", Errors.LAST_NOTE_WORK)
            return false
        }
        return true
    }

    private suspend fun handleFinishMechanical() {
        if (checkHasOpenMechanic() == false) {
            handleFailure("Note mechanic NOT open!", Errors.NOTE_MECHANICAL_OPEN)
            return
        }
        _uiState.update {
            it.copy(flagDialogCheck = true, typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL)
        }
    }

    private suspend fun handleTranshipment(): Boolean {
        val status = getStatusTranshipment().getOrElse {
            handleFailure(it)
            return false
        }
        return when (status) {
            StatusTranshipment.OK -> true
            StatusTranshipment.WITHOUT_NOTE -> {
                handleFailure("Without note!", Errors.WITHOUT_NOTE_TRANSHIPMENT)
                false
            }
            StatusTranshipment.TIME_INVALID -> {
                handleFailure("Time invalid!", Errors.TIME_INVALID_TRANSHIPMENT)
                false
            }
        }
    }

    private suspend fun handleImplement(): Boolean {
        if (checkHasNote() == false) {
            handleFailure("Header without note!", Errors.HEADER_EMPTY)
            return false
        }
        return true
    }

    private suspend fun handleFinishMechanicalDialog() {
        finishNoteMechanical()
            .onSuccess {
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = false,
                        typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                        flagAccess = false
                    )
                }
            }
            .onFailure(::handleFailure)
    }

    private suspend fun checkHasNote(): Boolean? {
        val result = checkHasNoteMotoMec()
        result.onFailure {
            handleFailure(it)
            return null
        }
        return result.getOrNull()!!
    }

    private suspend fun checkHasOpenMechanic(): Boolean? {
        val result = checkHasNoteOpenMechanic()
        result.onFailure {
            handleFailure(it)
            return null
        }
        return result.getOrNull()!!
    }

    private fun handleFailure(failure: String, errors: Errors = Errors.EXCEPTION) {
        Timber.e("${getClassAndMethod()} -> $failure")
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = failure,
                errors = errors,
                flagAccess = false,
                flagFailure = true
            )
        }
    }

    private fun handleFailure(error: Throwable) {
        val failure = "${error.message} -> ${error.cause.toString()}"
        handleFailure(failure)
    }
}