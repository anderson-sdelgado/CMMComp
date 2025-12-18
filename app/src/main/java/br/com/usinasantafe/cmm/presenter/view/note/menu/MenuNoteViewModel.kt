package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.cmm.domain.usecases.cec.SetDatePreCEC
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.composting.HasWill
import br.com.usinasantafe.cmm.domain.usecases.composting.HasCompostingInputLoadSentOpen
import br.com.usinasantafe.cmm.domain.usecases.composting.GetFlowComposting
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.HasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.mechanic.FinishNoteMechanic
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasCouplingTrailer
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetTypeLastNote
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetFlowEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.UncouplingTrailer
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.FIELD_ARRIVAL
import br.com.usinasantafe.cmm.lib.CHECK_WILL
import br.com.usinasantafe.cmm.lib.COUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.EXIT_MILL
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.FlowEquipNote
import br.com.usinasantafe.cmm.lib.FlowTrailer
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.lib.PCOMP
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.RETURN_MILL
import br.com.usinasantafe.cmm.lib.StatusPreCEC
import br.com.usinasantafe.cmm.lib.StatusTranshipment
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.lib.TypeMsg
import br.com.usinasantafe.cmm.lib.TypeNote
import br.com.usinasantafe.cmm.lib.UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.UNLOADING_INPUT
import br.com.usinasantafe.cmm.lib.WEIGHING
import br.com.usinasantafe.cmm.lib.WEIGHING_TARE
import br.com.usinasantafe.cmm.lib.functionListPMM
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.lib.typeListECM
import br.com.usinasantafe.cmm.lib.typeListPCOMPCompound
import br.com.usinasantafe.cmm.lib.typeListPCOMPInput
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
    val typeMsg: TypeMsg = TypeMsg.ITEM_NORMAL
)

@HiltViewModel
class MenuNoteViewModel @Inject constructor(
    private val listItemMenu: ListItemMenu,
    private val getDescrEquip: GetDescrEquip,
    private val hasNoteMotoMec: HasNoteMotoMec,
    private val hasNoteOpenMechanic: HasNoteOpenMechanic,
    private val getFlowEquip: GetFlowEquip,
    private val getStatusTranshipment: GetStatusTranshipment,
    private val getTypeLastNote: GetTypeLastNote,
    private val finishNoteMechanic: FinishNoteMechanic,
    private val setNoteMotoMec: SetNoteMotoMec,
    private val setDatePreCEC: SetDatePreCEC,
    private val hasCouplingTrailer: HasCouplingTrailer,
    private val getFlowComposting: GetFlowComposting,
    private val hasCompostingInputLoadSentOpen: HasCompostingInputLoadSentOpen,
    private val hasWill: HasWill,
    private val uncouplingTrailer: UncouplingTrailer
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
                handleFailure("listItemMenu -> EmptyList!")
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
        getFlowEquip().onSuccess { flow ->
            _uiState.update { it.copy(flowEquipNote = flow) }
        }.onFailure(::handleFailure)
    }

    fun onButtonReturn() = viewModelScope.launch {
        if (checkHasNote() == false) {
            handleFailure(
                failure = "checkHasNote -> Header without note!",
                errors = Errors.HEADER_EMPTY
            )
            return@launch
        }
        _uiState.update { it.copy(flagAccess = true, idSelection = null) }
    }

    fun onDialogCheck(pair: Pair<Int, String>) = viewModelScope.launch {
        onCloseDialogCheck()
        when (pair.second) {
            UNCOUPLING_TRAILER -> handleUncouplingTrailer()
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
            PCOMP -> handleSelectionPCOMP(item)
        }
    }

    private suspend fun handleSelectionPMM(item: ItemMenuModel) {
        val function = functionListPMM.find { it.first == item.function.first }
        if (function == null) {
            handleFailure("idFunction = ${item.function.first} not found in functionListPMM", Errors.INVALID)
            return
        }

        if (function.second != FINISH_MECHANICAL) {
            val check = checkHasOpenMechanic() ?: return
            if (check) {
                handleFailure("Note mechanic open!", Errors.NOTE_MECHANICAL_OPEN)
                return
            }
        }

        val allChecksPassed = when (function.second) {
            FINISH_MECHANICAL -> {
                handleFinishMechanical()
                false
            }
            TRANSHIPMENT -> handleTranshipment()
            IMPLEMENT -> handleImplement()
            NOTE_MECHANICAL -> handleNoteMechanical()
            else -> true
        }

        if (allChecksPassed) {
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
                handleExitWill(item)
            }
            FIELD_ARRIVAL -> {
                handleFieldArrival(item)
                false
            }
            RETURN_MILL -> {
                handleReturnMill()
                false
            }
            UNCOUPLING_TRAILER -> checkTrailer(FlowTrailer.UNCOUPLING)
            COUPLING_TRAILER -> checkTrailer(FlowTrailer.COUPLING)
            else -> true
        }

        if (allChecksPassed) {
            _uiState.update { it.copy(flagAccess = true) }
        }
    }

    private suspend fun handleSelectionPCOMP(item: ItemMenuModel) {
        val flow = getFlowComposting().getOrElse {
            handleFailure(it)
            return
        }
        val type = when(flow){
            FlowComposting.INPUT -> typeListPCOMPInput.find { it.first == item.type.first }
            FlowComposting.COMPOUND -> typeListPCOMPCompound.find { it.first == item.type.first }
        }
        if (type == null) {
            when(flow){
                FlowComposting.INPUT -> handleFailure("idType = ${item.type.first} not found in typeListPCOMPInput", Errors.INVALID)
                FlowComposting.COMPOUND -> handleFailure("idType = ${item.type.first} not found in typeListPCOMPCompound", Errors.INVALID)
            }
            return
        }
        val allChecksPassed = when (type.second) {
            ITEM_NORMAL,
            WEIGHING_TARE -> {
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
        val check = hasWill().getOrElse {
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
        val check = hasCompostingInputLoadSentOpen().getOrElse {
            handleFailure(it)
            return false
        }
        if (!check) {
            handleFailure("Without loading input!", Errors.WITHOUT_LOADING_INPUT)
            return false
        }
        return true
    }

    private suspend fun checkTrailer(flowTrailer: FlowTrailer): Boolean {
        val result = hasCouplingTrailer()
        result.onFailure {
            handleFailure(it)
            return false
        }
        val check = result.getOrNull()!!
        when (flowTrailer) {
            FlowTrailer.UNCOUPLING -> {
                if(!check) {
                    handleFailure("Need coupling trailer!", Errors.NEED_COUPLING_TRAILER)
                    return false
                } else {
                    _uiState.update {
                        it.copy(flagDialogCheck = true, typeMsg = TypeMsg.UNCOUPLING_TRAILER)
                    }
                    return false
                }
            }
            FlowTrailer.COUPLING -> {
                if(check) {
                    handleFailure("Need uncoupling trailer!", Errors.NEED_UNCOUPLING_TRAILER)
                    return false
                }
            }
        }
        return true
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
                typeMsg = TypeMsg.ITEM_NORMAL
            )
        }
    }

    private suspend fun handleFieldArrival(item: ItemMenuModel) {
        val status = setDatePreCEC(item).getOrElse {
            handleFailure(it)
            return
        }
        when (status) {
            StatusPreCEC.EXIT_MILL -> {
                handleFailure("PRE CEC without exit mill!", Errors.WITHOUT_EXIT_MILL_PRE_CEC)
            }
            StatusPreCEC.EXIT_FIELD -> {
                handleFailure("PRE CEC with arrival field!", Errors.WITH_FIELD_ARRIVAL_PRE_CEC)
            }
            StatusPreCEC.FIELD_ARRIVAL -> handleSetNote(item)
        }
    }

    private suspend fun handleExitWill(item: ItemMenuModel): Boolean {
        val status = setDatePreCEC(item).getOrElse {
            handleFailure(it)
            return false
        }
        if (status != StatusPreCEC.EXIT_MILL) {
            handleFailure("PRE CEC already started!", Errors.PRE_CEC_STARTED)
            return false
        }
        return true
    }

    private suspend fun handleNoteMechanical(): Boolean {
        val typeNote = getTypeLastNote().getOrElse {
            handleFailure(it)
            return false
        }
        if(typeNote == null) {
            handleFailure("Without Note!", Errors.WITHOUT_NOTE)
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
                handleFailure("Without note or last is not type work!", Errors.WITHOUT_NOTE_TRANSHIPMENT)
                false
            }
            StatusTranshipment.TIME_INVALID -> {
                handleFailure("Time invalid!", Errors.TIME_INVALID_TRANSHIPMENT)
                false
            }
        }
    }

    private suspend fun handleImplement(): Boolean {
        val check = checkHasNote() ?: return false
        if (!check) {
            handleFailure("Header without note!", Errors.HEADER_EMPTY)
            return false
        }
        return true
    }

    private suspend fun handleFinishMechanicalDialog() {
        finishNoteMechanic()
            .onSuccess {
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = false,
                        typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL,
                    )
                }
            }
            .onFailure(::handleFailure)
    }

    private suspend fun handleUncouplingTrailer() {
        uncouplingTrailer()
            .onSuccess {
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = false,
                        typeMsg = TypeMsg.UNCOUPLING_TRAILER,
                    )
                }
            }
            .onFailure(::handleFailure)
    }

    private suspend fun checkHasNote(): Boolean? {
        val result = hasNoteMotoMec()
        result.onFailure {
            handleFailure(it)
            return null
        }
        return result.getOrNull()!!
    }

    private suspend fun checkHasOpenMechanic(): Boolean? {
        val result = hasNoteOpenMechanic()
        result.onFailure {
            handleFailure(it)
            return null
        }
        return result.getOrNull()!!
    }

    private fun handleFailure(failure: String, errors: Errors = Errors.EXCEPTION) {
        val fail = "${getClassAndMethod()} -> $failure"
        Timber.e(fail)
        _uiState.update {
            it.copy(
                flagDialog = true,
                failure = fail,
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