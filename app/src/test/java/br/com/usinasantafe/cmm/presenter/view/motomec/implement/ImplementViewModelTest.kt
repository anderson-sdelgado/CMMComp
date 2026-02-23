package br.com.usinasantafe.cmm.presenter.view.motomec.implement

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.common.HasEquipSecondary
import br.com.usinasantafe.cmm.domain.usecases.implement.SetNroEquipImplement
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableEquip
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.view.implement.ImplementState
import br.com.usinasantafe.cmm.presenter.view.implement.ImplementViewModel
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ImplementViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableEquip = mock<UpdateTableEquip>()
    private val hasEquipSecondary = mock<HasEquipSecondary>()
    private val setNroEquipImplement = mock<SetNroEquipImplement>()

    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = ImplementViewModel(
        savedStateHandle = savedStateHandle,
        updateTableEquip = updateTableEquip,
        hasEquipSecondary = hasEquipSecondary,
        setNroEquipImplement = setNroEquipImplement
    )

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
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
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
                ImplementState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                ImplementState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ImplementViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.updateAllDatabase -> CleanEquip -> java.lang.NullPointerException"
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
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(2f, 4f)
                    ),
                    UpdateStatusState(
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
                ImplementState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                ImplementState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(2f, 4f),
                    )
                )
            )
            assertEquals(
                result[2],
                ImplementState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_equip",
                        currentProgress = percentage(3f, 4f),
                    )
                )
            )
            assertEquals(
                result[3],
                ImplementState(
                    status = UpdateStatusState(
                        flagDialog = true,
                        flagProgress = false,
                        flagFailure = false,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.setTextField(
                "ATUALIZAR DADOS",
                TypeButton.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
        }

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
    fun `setTextField - Check return failure if field is empty`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.FIELD_EMPTY
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.updateState -> ImplementViewModel.set -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
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
                    TypeEquip.IMPLEMENT
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
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.set -> IHasEquipSecondary -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
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
                    TypeEquip.IMPLEMENT
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
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase SetNroEquipImplement`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetNroEquipImplement",
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
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.set -> ISetNroEquipImplement -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check add implement 1 if executed successfully`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
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
                viewModel.uiState.value.status.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                false
            )
            assertEquals(
                viewModel.uiState.value.pos,
                2
            )
            assertEquals(
                viewModel.uiState.value.nroEquip,
                ""
            )
        }

    @Test
    fun `setTextField - Check return if the screen is in implement 2`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
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
            viewModel.setTextField(
                "500",
                TypeButton.NUMERIC
            )
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                false
            )
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.nroEquip,
                ""
            )
        }

    @Test
    fun `setTextField - Check return access if implement 2 is null`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
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
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }


    @Test
    fun `setTextField - Check return failure if have error in usecase HasEquipSecondary and implement 2`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                hasEquipSecondary(
                    "700",
                    TypeEquip.IMPLEMENT
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
            viewModel.setTextField(
                "700",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.set -> IHasEquipSecondary -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return false if not have reg in table and implement 2`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                hasEquipSecondary(
                    "700",
                    TypeEquip.IMPLEMENT
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
            viewModel.setTextField(
                "700",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in usecase SetNroEquipImplement and implement 2`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                hasEquipSecondary(
                    "700",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "700",
                    pos = 2
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetNroEquipImplement",
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
            viewModel.setTextField(
                "700",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "ImplementViewModel.setTextField -> ImplementViewModel.set -> ISetNroEquipImplement -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check access if add implement 2 is executed successfully`() =
        runTest {
            whenever(
                hasEquipSecondary(
                    "2200",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "2200",
                    pos = 1
                )
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                hasEquipSecondary(
                    "700",
                    TypeEquip.IMPLEMENT
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroEquipImplement(
                    nroEquip = "700",
                    pos = 2
                )
            ).thenReturn(
                Result.success(Unit)
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
            viewModel.setTextField(
                "700",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }


}