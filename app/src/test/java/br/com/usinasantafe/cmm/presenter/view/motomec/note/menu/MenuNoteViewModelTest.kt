package br.com.usinasantafe.cmm.presenter.view.motomec.note.menu

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.cec.SetDatePreCEC
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.composting.HasCompostingInputLoadSentOpen
import br.com.usinasantafe.cmm.domain.usecases.composting.HasWill
import br.com.usinasantafe.cmm.domain.usecases.composting.GetFlowComposting
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.HasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.mechanic.FinishNoteMechanic
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasCouplingTrailer
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetTypeLastNote
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetFlowEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetStatusTranshipment
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNote
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
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.lib.PCOMP
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.REEL
import br.com.usinasantafe.cmm.lib.RETURN_MILL
import br.com.usinasantafe.cmm.lib.STOP
import br.com.usinasantafe.cmm.lib.StatusPreCEC
import br.com.usinasantafe.cmm.lib.StatusTranshipment
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.lib.TypeMsg
import br.com.usinasantafe.cmm.lib.TypeNote
import br.com.usinasantafe.cmm.lib.UNCOUPLING_TRAILER
import br.com.usinasantafe.cmm.lib.UNLOADING_INPUT
import br.com.usinasantafe.cmm.lib.WEIGHING
import br.com.usinasantafe.cmm.lib.WEIGHING_TARE
import br.com.usinasantafe.cmm.lib.WORK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.collections.get
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MenuNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listItemMenu = mock<ListItemMenu>()
    private val getDescrEquip = mock<GetDescrEquip>()
    private val hasNoteMotoMec = mock<HasNoteMotoMec>()
    private val hasNoteOpenMechanic = mock<HasNoteOpenMechanic>()
    private val getFlowEquip = mock<GetFlowEquip>()
    private val getStatusTranshipment = mock<GetStatusTranshipment>()
    private val getTypeLastNote = mock<GetTypeLastNote>()
    private val finishNoteMechanic = mock<FinishNoteMechanic>()
    private val setNote = mock<SetNote>()
    private val setDatePreCEC = mock<SetDatePreCEC>()
    private val hasCouplingTrailer = mock<HasCouplingTrailer>()
    private val getFlowComposting = mock<GetFlowComposting>()
    private val hasCompostingInputLoadSentOpen = mock<HasCompostingInputLoadSentOpen>()
    private val hasWill = mock<HasWill>()
    private val uncouplingTrailer = mock<UncouplingTrailer>()

    private val viewModel = MenuNoteViewModel(
        listItemMenu = listItemMenu,
        getDescrEquip = getDescrEquip,
        hasNoteMotoMec = hasNoteMotoMec,
        hasNoteOpenMechanic = hasNoteOpenMechanic,
        getFlowEquip = getFlowEquip,
        getStatusTranshipment = getStatusTranshipment,
        getTypeLastNote = getTypeLastNote,
        finishNoteMechanic = finishNoteMechanic,
        setNote = setNote,
        setDatePreCEC = setDatePreCEC,
        hasCouplingTrailer = hasCouplingTrailer,
        getFlowComposting = getFlowComposting,
        hasCompostingInputLoadSentOpen = hasCompostingInputLoadSentOpen,
        hasWill = hasWill,
        uncouplingTrailer = uncouplingTrailer
    )

    @Test
    fun `menuList - Check return failure if have error in ListItemMenu`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                resultFailure(
                    context = "ListItemMenu",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.menuList -> ListItemMenu -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `menuList - Check return failure if return is empty list`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(emptyList())
            )
            viewModel.menuList("pmm")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `menuList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 2 to STOP,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            viewModel.menuList("pmm")
            val menuList = viewModel.uiState.value.menuList
            assertEquals(
                menuList.size,
                2
            )
            val item1 = menuList[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.descr,
                "TRABALHANDO"
            )
            assertEquals(
                item1.function.first,
                1
            )
            assertEquals(
                item1.function.second,
                WORK
            )
            val item2 = menuList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.descr,
                "PARADO"
            )
            assertEquals(
                item2.function.first,
                2
            )
            assertEquals(
                item2.function.second,
                STOP
            )
        }

    @Test
    fun `descrEquip - Check return failure if have error in getDescrEquip`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                resultFailure(
                    context = "GetDescrEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.descrEquip()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.descrEquip -> GetDescrEquip -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `descrEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                Result.success("2200 - TRATOR")
            )
            viewModel.descrEquip()
            assertEquals(
                viewModel.uiState.value.descrEquip,
                "2200 - TRATOR"
            )
        }

    @Test
    fun `flowEquipNote - Check return failure if have error in GetFlowEquipNoteMotoMec`() =
        runTest {
            whenever(
                getFlowEquip()
            ).thenReturn(
                resultFailure(
                    context = "GetFlowEquipNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.flowEquipNote()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.flowEquipNote -> GetFlowEquipNoteMotoMec -> java.lang.Exception"
            )
        }

    @Test
    fun `flowEquipNote - Check return true if GetFlowEquipNoteMotoMec execute successfully`() =
        runTest {
            whenever(
                getFlowEquip()
            ).thenReturn(
                Result.success(FlowEquipNote.SECONDARY)
            )
            viewModel.flowEquipNote()
            assertEquals(
                viewModel.uiState.value.flowEquipNote,
                FlowEquipNote.SECONDARY
            )
        }

    @Test
    fun `onButtonReturn - Check return failure if have error in CheckHasNote`() =
        runTest {
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNote",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onButtonReturn -> CheckHasNote -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `onButtonReturn - Check return failure if checkHasNoteMotoMec return false`() =
        runTest {
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onButtonReturn -> Header without note!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.HEADER_EMPTY
            )
        }

    @Test
    fun `onButtonReturn - Check return true if CheckHasNoteMotoMec execute successfully and return true`() =
        runTest {
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `onDialogCheck - Check return failure if have error in FinishNoteMechanical and Pair is FINISH_MECHANICAL`() =
        runTest {
            whenever(
                finishNoteMechanic()
            ).thenReturn(
                resultFailure(
                    context = "FinishNoteMechanical",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onDialogCheck(
                1 to FINISH_MECHANICAL
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onDialogCheck -> MenuNoteViewModel.handleFinishMechanicalDialog -> FinishNoteMechanical -> java.lang.Exception"
            )
        }

    @Test
    fun `onDialogCheck - Check return true if FinishNoteMechanical execute successfully and Pair is FINISH_MECHANICAL`() =
        runTest {
            whenever(
                finishNoteMechanic()
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.onDialogCheck(
                1 to FINISH_MECHANICAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.NOTE_FINISH_MECHANICAL
            )
        }

    @Test
    fun `onDialogCheck - Check return failure if have error in UncouplingTrailer and Pair is UNCOUPLING_TRAILER`() =
        runTest {
            whenever(
                uncouplingTrailer()
            ).thenReturn(
                resultFailure(
                    context = "UncouplingTrailer",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onDialogCheck(
                1 to UNCOUPLING_TRAILER
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onDialogCheck -> MenuNoteViewModel.handleUncouplingTrailer -> UncouplingTrailer -> java.lang.Exception"
            )
        }

    @Test
    fun `onDialogCheck - Check return true if UncouplingTrailer execute successfully and Pair is UNCOUPLING_TRAILER`() =
        runTest {
            whenever(
                uncouplingTrailer()
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.onDialogCheck(
                1 to UNCOUPLING_TRAILER
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.UNCOUPLING_TRAILER
            )
        }

    @Test
    fun `onDialogCheck - Check return true if Pair is DEFAULT`() =
        runTest {
            viewModel.onDialogCheck(
                1 to RETURN_MILL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - Check return failure if option selection is invalid`() =
        runTest {
            viewModel.setSelection(0)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> Item with id = 0 not found in menuList"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if function is invalid`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 12 to UNCOUPLING_TRAILER,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> idFunction = 12 not found in functionListPMM"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have error in CheckHasNoteOpenMechanic`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> CheckHasNoteOpenMechanic -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have CheckHasNoteOpenMechanic return true`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> Note mechanic NOT open!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.NOTE_MECHANICAL_OPEN
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have error in CheckHasNoteOpenMechanic and function is FINISH_MECHANICAL`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 8 to FINISH_MECHANICAL,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleFinishMechanical -> CheckHasNoteOpenMechanic -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if CheckHasNoteOpenMechanic return false and function is FINISH_MECHANICAL`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 8 to FINISH_MECHANICAL,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleFinishMechanical -> Note mechanic NOT open!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.NOTE_MECHANICAL_OPEN
            )
        }

    @Test
    fun `setSelection - PMM - Check return msg if CheckHasNoteOpenMechanic return true and function is FINISH_MECHANICAL`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 8 to FINISH_MECHANICAL,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialogCheck,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.NOTE_FINISH_MECHANICAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have error in GetStatusTranshipment and function is TRANSHIPMENT`() =
        runTest {
            wheneverBasicPMM(
                4 to TRANSHIPMENT
            )
            whenever(
                getStatusTranshipment()
            ).thenReturn(
                resultFailure(
                    context = "GetStatusTranshipment",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleTranshipment -> GetStatusTranshipment -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if GetStatusTranshipment return WITHOUT_NOTE and function is TRANSHIPMENT`() =
        runTest {
            wheneverBasicPMM(
                4 to TRANSHIPMENT
            )
            whenever(
                getStatusTranshipment()
            ).thenReturn(
                Result.success(StatusTranshipment.WITHOUT_NOTE)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleTranshipment -> Without note or last is not type work!"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITHOUT_NOTE_TRANSHIPMENT
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if GetStatusTranshipment return TIME_INVALID and function is TRANSHIPMENT`() =
        runTest {
            wheneverBasicPMM(
                4 to TRANSHIPMENT
            )
            whenever(
                getStatusTranshipment()
            ).thenReturn(
                Result.success(StatusTranshipment.TIME_INVALID)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleTranshipment -> Time invalid!"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.TIME_INVALID_TRANSHIPMENT
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if GetStatusTranshipment return OK and function is TRANSHIPMENT`() =
        runTest {
            wheneverBasicPMM(
                4 to TRANSHIPMENT
            )
            whenever(
                getStatusTranshipment()
            ).thenReturn(
                Result.success(StatusTranshipment.OK)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have error in CheckHasNoteMotoMec and function is IMPLEMENT`() =
        runTest {
            wheneverBasicPMM(
                5 to IMPLEMENT
            )
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleImplement -> CheckHasNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if CheckHasNoteMotoMec return false and function is IMPLEMENT`() =
        runTest {
            wheneverBasicPMM(
                5 to IMPLEMENT
            )
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleImplement -> Header without note!"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.HEADER_EMPTY
            )
        }

    @Test
    fun `setSelection - PMM - Check return access if CheckHasNoteMotoMec return true and function is IMPLEMENT`() =
        runTest {
            wheneverBasicPMM(
                5 to IMPLEMENT
            )
            whenever(
                hasNoteMotoMec()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if have error in CheckTypeLastNote and function is NOTE_MECHANICAL`() =
        runTest {
            wheneverBasicPMM(
                7 to NOTE_MECHANICAL
            )
            whenever(
                getTypeLastNote()
            ).thenReturn(
                resultFailure(
                    context = "CheckTypeLastNote",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleNoteMechanical -> CheckTypeLastNote -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if CheckTypeLastNote return null and function is NOTE_MECHANICAL`() =
        runTest {
            wheneverBasicPMM(
                7 to NOTE_MECHANICAL
            )
            whenever(
                getTypeLastNote()
            ).thenReturn(
                Result.success(null)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleNoteMechanical -> Without Note!"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITHOUT_NOTE
            )
        }

    @Test
    fun `setSelection - PMM - Check return failure if CheckTypeLastNote return WORK and function is NOTE_MECHANICAL`() =
        runTest {
            wheneverBasicPMM(
                7 to NOTE_MECHANICAL
            )
            whenever(
                getTypeLastNote()
            ).thenReturn(
                Result.success(TypeNote.WORK)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPMM -> MenuNoteViewModel.handleNoteMechanical -> Without Note!"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.LAST_NOTE_WORK
            )
        }

    @Test
    fun `setSelection - PMM - Check return access if CheckTypeLastNote return STOP and function is NOTE_MECHANICAL`() =
        runTest {
            wheneverBasicPMM(
                7 to NOTE_MECHANICAL
            )
            whenever(
                getTypeLastNote()
            ).thenReturn(
                Result.success(TypeNote.STOP)
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - PMM - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listItemMenu("PMM")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            type = 1 to ITEM_NORMAL,
                            function = 1 to WORK,
                            app = 1 to PMM
                        ),
                    )
                )
            )
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            whenever(
                hasNoteOpenMechanic()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if type is invalid`() =
        runTest {
            wheneverBasicECM(12 to UNCOUPLING_TRAILER)
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> idType = 12 not found in typeListECM"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in SetNoteMotoMec and type is ITEM_NORMAL`() =
        runTest {
            wheneverBasicECM(1 to ITEM_NORMAL)
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleSetNote -> SetNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - ECM - Check return msg if SetNoteMotoMec execute successfully and type is ITEM_NORMAL`() =
        runTest {
            wheneverBasicECM(1 to ITEM_NORMAL)
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 1 to PMM
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.ITEM_NORMAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.idSelection,
                1
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in SetNoteMotoMec and type is WEIGHING`() =
        runTest {
            wheneverBasicECM(6 to WEIGHING)
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 6 to WEIGHING,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleSetNote -> SetNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - ECM - Check return msg if SetNoteMotoMec execute successfully and type is WEIGHING`() =
        runTest {
            wheneverBasicECM(6 to WEIGHING)
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 6 to WEIGHING,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.ITEM_NORMAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.idSelection,
                1
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in GetStatusPreCEC and type is EXIT_MILL`() =
        runTest {
            wheneverBasicECM(2 to EXIT_MILL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 2 to EXIT_MILL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "GetStatusPreCEC",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleExitWill -> GetStatusPreCEC -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if GetStatusPreCEC not return EMPTY and type is EXIT_MILL`() =
        runTest {
            wheneverBasicECM(2 to EXIT_MILL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 2 to EXIT_MILL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.FIELD_ARRIVAL)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleExitWill -> PRE CEC already started!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.PRE_CEC_STARTED
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return access if GetStatusPreCEC return EMPTY and type is EXIT_MILL`() =
        runTest {
            wheneverBasicECM(2 to EXIT_MILL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 2 to EXIT_MILL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.EXIT_MILL)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in GetStatusPreCEC and type is ARRIVAL_FIELD`() =
        runTest {
            wheneverBasicECM(3 to FIELD_ARRIVAL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "GetStatusPreCEC",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleFieldArrival -> GetStatusPreCEC -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if GetStatusPreCEC return EMPTY and type is ARRIVAL_FIELD`() =
        runTest {
            wheneverBasicECM(3 to FIELD_ARRIVAL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.EXIT_MILL)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleFieldArrival -> Without exit mill in PRE CEC!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITHOUT_EXIT_MILL_PRE_CEC
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if GetStatusPreCEC return ARRIVAL_FIELD and type is ARRIVAL_FIELD`() =
        runTest {
            wheneverBasicECM(3 to FIELD_ARRIVAL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.EXIT_FIELD)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleFieldArrival -> With arrival field in PRE CEC!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITH_FIELD_ARRIVAL_PRE_CEC
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if GetStatusPreCEC return EXIT_MILL and have error in SetNoteMotoMec and type is ARRIVAL_FIELD`() =
        runTest {
            wheneverBasicECM(3 to FIELD_ARRIVAL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.FIELD_ARRIVAL)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.handleFieldArrival -> MenuNoteViewModel.handleSetNote -> SetNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return msg  if GetStatusPreCEC return EXIT_MILL and SetNoteMotoMec execute successfully and type is ARRIVAL_FIELD`() =
        runTest {
            wheneverBasicECM(3 to FIELD_ARRIVAL)
            whenever(
                setDatePreCEC(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(StatusPreCEC.FIELD_ARRIVAL)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 3 to FIELD_ARRIVAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.ITEM_NORMAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.idSelection,
                1
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return msg if type is RETURN_MILL`() =
        runTest {
            wheneverBasicECM(5 to RETURN_MILL)
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialogCheck,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.RETURN_MILL
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in CheckCouplingTrailer and type is UNCOUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(7 to UNCOUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                resultFailure(
                    context = "CheckCouplingTrailer",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.checkTrailer -> CheckCouplingTrailer -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if CheckCouplingTrailer return false and type is UNCOUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(7 to UNCOUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.checkTrailer -> Need coupling trailer!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.NEED_COUPLING_TRAILER
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return check if CheckCouplingTrailer return true and type is UNCOUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(7 to UNCOUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialogCheck,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.UNCOUPLING_TRAILER
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if have error in CheckCouplingTrailer and type is COUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(8 to COUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                resultFailure(
                    context = "CheckCouplingTrailer",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.checkTrailer -> CheckCouplingTrailer -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return failure if CheckCouplingTrailer return true and type is COUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(8 to COUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionECM -> MenuNoteViewModel.checkTrailer -> Need uncoupling trailer!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.NEED_UNCOUPLING_TRAILER
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - ECM - Check return access if CheckCouplingTrailer return false and type is COUPLING_TRAILER`() =
        runTest {
            wheneverBasicECM(8 to COUPLING_TRAILER)
            whenever(
                hasCouplingTrailer()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("ecm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if have error in GetFlowComposting`() =
        runTest {
            wheneverBasicPCOMP(1 to ITEM_NORMAL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                resultFailure(
                    context = "GetFlowComposting",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> GetFlowComposting -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if type is invalid in typeListPCOMPInput`() =
        runTest {
            wheneverBasicPCOMP(11 to REEL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> idType = 11 not found in typeListPCOMPInput"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if type is invalid in typeListPCOMPCompound`() =
        runTest {
            wheneverBasicPCOMP(11 to REEL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> idType = 11 not found in typeListPCOMPCompound"
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if have error in SetNoteMotoMec and type is ITEM_NORMAL`() =
        runTest {
            wheneverBasicPCOMP(1 to ITEM_NORMAL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleSetNote -> SetNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return msg if SetNoteMotoMec execute successfully and type is ITEM_NORMAL`() =
        runTest {
            wheneverBasicPCOMP(1 to ITEM_NORMAL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.ITEM_NORMAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.idSelection,
                1
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if have error in SetNoteMotoMec and type is WEIGHING_TARE`() =
        runTest {
            wheneverBasicPCOMP(2 to WEIGHING_TARE)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 2 to WEIGHING_TARE,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    )
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNoteMotoMec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleSetNote -> SetNoteMotoMec -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return msg if SetNoteMotoMec execute successfully and type is WEIGHING_TARE`() =
        runTest {
            wheneverBasicPCOMP(2 to WEIGHING_TARE)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                setNote(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 2 to WEIGHING_TARE,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.typeMsg,
                TypeMsg.ITEM_NORMAL
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.idSelection,
                1
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if have error in CheckWill and type is CHECK_WILL`() =
        runTest {
            wheneverBasicPCOMP(5 to CHECK_WILL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                hasWill()
            ).thenReturn(
                resultFailure(
                    context = "CheckWill",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleShowInfoWill -> CheckWill -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if CheckWill return false and type is CHECK_WILL`() =
        runTest {
            wheneverBasicPCOMP(5 to CHECK_WILL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                hasWill()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleShowInfoWill -> Without loading composting!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITHOUT_LOADING_COMPOSTING
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return access if CheckWill return true and type is CHECK_WILL`() =
        runTest {
            wheneverBasicPCOMP(5 to CHECK_WILL)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.COMPOUND)
            )
            whenever(
                hasWill()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if have error in CheckInitialLoading and type is UNLOADING_INPUT`() =
        runTest {
            wheneverBasicPCOMP(10 to UNLOADING_INPUT)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                hasCompostingInputLoadSentOpen()
            ).thenReturn(
                resultFailure(
                    context = "CheckInitialLoading",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleLoadingInput -> CheckInitialLoading -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return failure if CheckInitialLoading return false and type is UNLOADING_INPUT`() =
        runTest {
            wheneverBasicPCOMP(10 to UNLOADING_INPUT)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                hasCompostingInputLoadSentOpen()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> MenuNoteViewModel.handleSelectionPCOMP -> MenuNoteViewModel.handleLoadingInput -> Without loading input!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.WITHOUT_LOADING_INPUT
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setSelection - PCOMP - Check return access if CheckInitialLoading return true and type is UNLOADING_INPUT`() =
        runTest {
            wheneverBasicPCOMP(10 to UNLOADING_INPUT)
            whenever(
                getFlowComposting()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                hasCompostingInputLoadSentOpen()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.menuList("pcomp")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    private suspend fun wheneverBasicPMM(
        function: Pair<Int, String> = 1 to WORK
    ) {
        whenever(
            hasNoteOpenMechanic()
        ).thenReturn(
            Result.success(false)
        )
        whenever(
            listItemMenu("PMM")
        ).thenReturn(
            Result.success(
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = 1 to ITEM_NORMAL,
                        function = function,
                        app = 1 to PMM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 1 to PMM
                    ),
                )
            )
        )
    }

    private suspend fun wheneverBasicECM(
        type: Pair<Int, String> = 1 to WORK
    ) {
        whenever(
            listItemMenu("ECM")
        ).thenReturn(
            Result.success(
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = type,
                        function = 1 to WORK,
                        app = 2 to ECM
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 2 to ECM
                    ),
                )
            )
        )
    }

    private suspend fun wheneverBasicPCOMP (
        type: Pair<Int, String> = 1 to WORK
    ) {
        whenever(
            listItemMenu("PCOMP")
        ).thenReturn(
            Result.success(
                listOf(
                    ItemMenuModel(
                        id = 1,
                        descr = "TRABALHANDO",
                        type = type,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    ),
                    ItemMenuModel(
                        id = 2,
                        descr = "PARADO",
                        type = 1 to ITEM_NORMAL,
                        function = 1 to WORK,
                        app = 3 to PCOMP
                    ),
                )
            )
        )
    }
}