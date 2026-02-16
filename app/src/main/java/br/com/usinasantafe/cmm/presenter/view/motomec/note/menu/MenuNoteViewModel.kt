package br.com.usinasantafe.cmm.presenter.view.motomec.note.menu

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
import br.com.usinasantafe.cmm.domain.usecases.cec.HasCouplingTrailer
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetTypeLastNote
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetFlowEquip
import br.com.usinasantafe.cmm.domain.usecases.transhipment.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNote
import br.com.usinasantafe.cmm.domain.usecases.cec.UncouplingTrailer
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
import br.com.usinasantafe.cmm.utils.handleFailure
import br.com.usinasantafe.cmm.utils.onFailureHandled
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

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
    private val setNote: SetNote,
    private val setDatePreCEC: SetDatePreCEC,
    private val hasCouplingTrailer: HasCouplingTrailer,
    private val getFlowComposting: GetFlowComposting,
    private val hasCompostingInputLoadSentOpen: HasCompostingInputLoadSentOpen,
    private val hasWill: HasWill,
    private val uncouplingTrailer: UncouplingTrailer
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuNoteState())
    val uiState = _uiState.asStateFlow()

    private val state get() = uiState.value

    private fun updateState(block: MenuNoteState.() -> MenuNoteState) {
        _uiState.update(block)
    }

    fun onCloseDialog() = updateState { copy(flagDialog = false) }

    fun onCloseDialogCheck() = updateState { copy(flagDialogCheck = false) }

    fun menuList(flavor: String) = viewModelScope.launch {
        runCatching {
            updateState { copy(flavorApp = flavor.uppercase()) }
            val list = listItemMenu(flavor.uppercase()).getOrThrow()
            if (list.isEmpty()) {
                handleFailure(Errors.EXCEPTION, getClassAndMethod(), ::onError, "listItemMenu -> EmptyList!")
            }
            list
        }
            .onSuccess { updateState { copy(menuList = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun descrEquip() = viewModelScope.launch {
        runCatching {
            getDescrEquip().getOrThrow()
        }
            .onSuccess { updateState { copy(descrEquip = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun flowEquipNote() = viewModelScope.launch {
        runCatching {
            getFlowEquip().getOrThrow()
        }
            .onSuccess { updateState { copy(flowEquipNote = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    fun onButtonReturn() = viewModelScope.launch {
        runCatching {
            val check = hasNoteMotoMec().getOrThrow()
            if (!check) {
                handleFailure(Errors.HEADER_EMPTY, getClassAndMethod(), ::onError)
                return@launch
            }
        }
            .onSuccess { updateState { copy(flagAccess = true, idSelection = null) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
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

    fun setSelection(id: Int) = viewModelScope.launch {
        val item = _uiState.value.menuList.find { it.id == id }
        if (item == null) {
            handleFailure("Item with id = $id not found in menuList", getClassAndMethod(), ::onError)
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
        runCatching {
            val function = functionListPMM.find { it.first == item.function.first }
            if (function == null) {
                handleFailure("idFunction = ${item.function.first} not found in functionListPMM", getClassAndMethod(), ::onError)
                return@runCatching false
            }
            if (function.second != FINISH_MECHANICAL) {
                val check = hasNoteOpenMechanic().getOrThrow()
                if (check) {
                    handleFailure(Errors.NOTE_MECHANICAL_OPEN, getClassAndMethod(), ::onError)
                    return@runCatching false
                }
            }
            when (function.second) {
                FINISH_MECHANICAL -> {
                    handleFinishMechanical()
                    false
                }
                TRANSHIPMENT -> handleTranshipment()
                IMPLEMENT -> handleImplement()
                NOTE_MECHANICAL -> handleNoteMechanical()
                else -> true
            }

        }
            .onSuccess { updateState { copy(flagAccess = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)

    }

    private suspend fun handleSelectionECM(item: ItemMenuModel) {
        runCatching {
            val type = typeListECM.find { it.first == item.type.first }
            if (type == null) {
                handleFailure("idType = ${item.type.first} not found in typeListECM", getClassAndMethod(), ::onError)
                return
            }
            when (type.second) {
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

        }
            .onSuccess { updateState { copy(flagAccess = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private suspend fun handleSelectionPCOMP(item: ItemMenuModel) {
        runCatching {
            val flow = getFlowComposting().getOrThrow()
            val type = when (flow) {
                FlowComposting.INPUT -> typeListPCOMPInput.find { it.first == item.type.first }
                FlowComposting.COMPOUND -> typeListPCOMPCompound.find { it.first == item.type.first }
            }
            if (type == null) {
                when (flow) {
                    FlowComposting.INPUT -> handleFailure( "idType = ${item.type.first} not found in typeListPCOMPInput", getClassAndMethod(), ::onError)
                    FlowComposting.COMPOUND -> handleFailure( "idType = ${item.type.first} not found in typeListPCOMPCompound", getClassAndMethod(), ::onError)
                }
                return
            }
            when (type.second) {
                ITEM_NORMAL,
                WEIGHING_TARE -> {
                    handleSetNote(item)
                    false
                }

                UNLOADING_INPUT -> handleLoadingInput()
                CHECK_WILL -> handleShowInfoWill()
                else -> true
            }

        }
            .onSuccess { updateState { copy(flagAccess = it) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private suspend fun handleShowInfoWill(): Boolean {
        return runCatching {
            val check = hasWill().getOrThrow()
            if (!check) {
                handleFailure(Errors.WITHOUT_LOADING_COMPOSTING, getClassAndMethod(), ::onError)
                return false
            }
            return true
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)
    }

    private suspend fun handleLoadingInput(): Boolean {
        return runCatching {
            val check = hasCompostingInputLoadSentOpen().getOrThrow()
            if (!check) {
                handleFailure(Errors.WITHOUT_LOADING_INPUT, getClassAndMethod(), ::onError)
                return false
            }
            return true
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)
    }

    private suspend fun checkTrailer(flowTrailer: FlowTrailer): Boolean {
        return runCatching {
            val check = hasCouplingTrailer().getOrThrow()
            when (flowTrailer) {
                FlowTrailer.UNCOUPLING -> {
                    if (!check) {
                        handleFailure(Errors.NEED_COUPLING_TRAILER, getClassAndMethod(), ::onError)
                        return false
                    } else {
                        updateState { copy(flagDialogCheck = true, typeMsg = TypeMsg.UNCOUPLING_TRAILER) }
                        return false
                    }
                }

                FlowTrailer.COUPLING -> {
                    if (check) {
                        handleFailure(Errors.NEED_UNCOUPLING_TRAILER, getClassAndMethod(), ::onError)
                        return false
                    }
                }
            }
            return true
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)
    }

    private fun handleReturnMill() {
        _uiState.update {
            it.copy(flagDialogCheck = true, typeMsg = TypeMsg.RETURN_MILL)
        }
    }

    private suspend fun handleSetNote(item: ItemMenuModel) {
        runCatching {
            setNote(item).getOrThrow()
        }
            .onSuccess { onSuccess(TypeMsg.ITEM_NORMAL) }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private suspend fun handleFieldArrival(item: ItemMenuModel) {
        runCatching {
            val status = setDatePreCEC(item).getOrThrow()
            when (status) {
                StatusPreCEC.EXIT_MILL -> handleFailure(Errors.WITHOUT_EXIT_MILL_PRE_CEC, getClassAndMethod(), ::onError)
                StatusPreCEC.EXIT_FIELD -> handleFailure(Errors.WITH_FIELD_ARRIVAL_PRE_CEC, getClassAndMethod(), ::onError)
                StatusPreCEC.FIELD_ARRIVAL -> handleSetNote(item)
            }
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(true)
    }

    private suspend fun handleExitWill(item: ItemMenuModel): Boolean {
        return runCatching {
            val status = setDatePreCEC(item).getOrThrow()
            if (status != StatusPreCEC.EXIT_MILL) {
                handleFailure(Errors.PRE_CEC_STARTED, getClassAndMethod(), ::onError)
                return false
            }
            return true
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)
    }

    private suspend fun handleNoteMechanical(): Boolean {
        return runCatching {
            val typeNote = getTypeLastNote().getOrThrow()
            if (typeNote == null) {
                handleFailure(Errors.WITHOUT_NOTE, getClassAndMethod(), ::onError)
                return false
            }
            if (typeNote == TypeNote.WORK) {
                handleFailure(Errors.LAST_NOTE_WORK, getClassAndMethod(), ::onError)
                return false
            }
            return true
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)
    }

    private suspend fun handleFinishMechanical() {
        runCatching {
            val check = hasNoteOpenMechanic().getOrThrow()
            if (!check) {
                handleFailure(Errors.NOTE_MECHANICAL_OPEN, getClassAndMethod(), ::onError)
                return@runCatching
            }
        }
            .onSuccess { updateState { copy(flagDialogCheck = true, typeMsg = TypeMsg.NOTE_FINISH_MECHANICAL) } }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private suspend fun handleTranshipment(): Boolean {
        return runCatching {
            val status = getStatusTranshipment().getOrThrow()
            return when (status) {
                StatusTranshipment.OK -> true
                StatusTranshipment.WITHOUT_NOTE -> {
                    handleFailure(Errors.WITHOUT_NOTE_TRANSHIPMENT, getClassAndMethod(),  ::onError)
                    false
                }

                StatusTranshipment.TIME_INVALID -> {
                    handleFailure(Errors.TIME_INVALID_TRANSHIPMENT, getClassAndMethod(),  ::onError)
                    false
                }
            }
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)

    }

    private suspend fun handleImplement(): Boolean {
        return runCatching {
            val check = hasNoteMotoMec().getOrThrow()
            if (!check) {
                handleFailure(Errors.HEADER_EMPTY, getClassAndMethod(),  ::onError)
                false
            } else {
                true
            }
        }
            .onFailure { throwable ->
                handleFailure(throwable, getClassAndMethod(), ::onError)
            }
            .getOrDefault(false)

    }

    private suspend fun handleFinishMechanicalDialog() {
        finishNoteMechanic()
            .onSuccess { onSuccess(TypeMsg.NOTE_FINISH_MECHANICAL) }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private suspend fun handleUncouplingTrailer() {
        uncouplingTrailer()
            .onSuccess { onSuccess(TypeMsg.UNCOUPLING_TRAILER) }
            .onFailureHandled(getClassAndMethod(), ::onError)
    }

    private fun onSuccess(typeMsg: TypeMsg) =
        updateState { copy(flagDialog = true, flagFailure = false, typeMsg = typeMsg) }

    private fun onError(failure: String, errors: Errors = Errors.EXCEPTION) = updateState {
        copy(
            flagDialog = true,
            failure = failure,
            errors = errors,
            flagAccess = false,
            flagFailure = true
        )
    }

}