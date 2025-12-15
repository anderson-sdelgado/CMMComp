package br.com.usinasantafe.cmm.presenter.view.note.transhipment

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroTranshipment
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.presenter.view.header.operator.OperatorHeaderState
import br.com.usinasantafe.cmm.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class TranshipmentViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableEquip = mock<UpdateTableEquip>()
    private val hasEquipSecondary = mock<HasEquipSecondary>()
    private val setNroTranshipment = mock<SetNroTranshipment>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
            )
        )
    ) = TranshipmentViewModel(
        savedStateHandle,
        updateTableEquip = updateTableEquip,
        hasEquipSecondary = hasEquipSecondary,
        setNroTranshipment = setNroTranshipment
    )

    @Test
    fun `setTextField - Check add char`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            "1",
            viewModel.uiState.value.nroEquip
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroEquip,
            "191"
        )
    }

    @Test
    fun `setTextField - Check msg of empty field`() {
        val viewModel = createViewModel()
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELD_EMPTY
        )
    }

    @Test
    fun `setTextField - Check return failure usecase if have error in usecase CleanEquip`() =
        runTest {
            whenever(
                updateTableEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "CleanEquip -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                TranshipmentState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TranshipmentState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "TranshipmentViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TranshipmentViewModel.setTextField -> TranshipmentViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `setTextField - Check return success in updateAllDatabase`() =
        runTest {
            whenever(
                updateTableEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                TranshipmentState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                TranshipmentState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                TranshipmentState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_equip",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                TranshipmentState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
        }

    @Test
    fun `setTextField - Check return failure if field is empty`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELD_EMPTY
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TranshipmentViewModel.setTextField -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase HasEquipSecondary`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquipSecondary.TRANSHIPMENT
                )
            ).thenReturn(
                resultFailure(
                    context = "IHasEquipSecondary",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "2200",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TranshipmentViewModel.setTextField -> TranshipmentViewModel.setNroTranshipment -> IHasEquipSecondary -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return false if not have reg in table`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquipSecondary.TRANSHIPMENT
                )
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "2200",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase SetNroTranshipment`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquipSecondary.TRANSHIPMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroTranshipment(
                    nroTranshipment = "2200",
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetNroTranshipment",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "2200",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TranshipmentViewModel.setTextField -> TranshipmentViewModel.setNroTranshipment -> ISetNroTranshipment -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check access release if executed successfully`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquipSecondary.TRANSHIPMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroTranshipment(
                    nroTranshipment = "2200",
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "2200",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}