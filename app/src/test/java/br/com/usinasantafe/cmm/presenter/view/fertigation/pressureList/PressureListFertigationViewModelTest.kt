package br.com.usinasantafe.cmm.presenter.view.fertigation.pressureList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListPressure
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetValuePressure
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTablePressure
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.percentage
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PressureListFertigationViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listPressure = mock<ListPressure>()
    private val setValuePressure = mock<SetValuePressure>()
    private val updateTablePressure = mock<UpdateTablePressure>()
    private val viewModel = PressureListFertigationViewModel(
        listPressure = listPressure,
        setValuePressure = setValuePressure,
        updateTablePressure = updateTablePressure
    )

    @Test
    fun `list - Check return failure if have error in ListPressure`() =
        runTest {
            whenever(
                listPressure()
            ).thenReturn(
                resultFailure(
                    context = "ListPressure",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "PressureListViewModel.list -> ListPressure -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
        }

    @Test
    fun `list - Check return true if ListPressure execute successfully`() =
        runTest {
            whenever(
                listPressure()
            ).thenReturn(
                Result.success(
                    listOf(1.0)
                )
            )
            viewModel.list()
            val list = viewModel.uiState.value.list
            assertEquals(
                list.count(),
                1
            )
            val entity = list[0]
            assertEquals(
                entity,
                1.0
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTablePressure`() =
        runTest {
            whenever(
                updateTablePressure(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTablePressure -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "PressureListViewModel.updateAllDatabase -> UpdateTablePressure -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "PressureListViewModel.updateDatabase -> PressureListViewModel.updateAllDatabase -> UpdateTablePressure -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTablePressure(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(2f, 4f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(2f, 4f),
                    )
                )
            )
            assertEquals(
                result[2],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_pressure",
                        currentProgress = percentage(3f, 4f),
                    )
                )
            )
            assertEquals(
                result[3],
                PressureListFertigationState(
                    status = UpdateStatusState(
                        flagDialog = true,
                        flagProgress = false,
                        flagFailure = false,
                        levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                        currentProgress = 1f,
                    )
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
        }

    @Test
    fun `set - Check return failure if have error in SetValuePressure`() =
        runTest {
            whenever(
                setValuePressure(
                    value = 1.0
                )
            ).thenReturn(
                resultFailure(
                    context = "SetValuePressure",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.set(1.0)
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "PressureListViewModel.set -> SetValuePressure -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
        }

    @Test
    fun `set - Check access release if function execute successfully`() =
        runTest {
            whenever(
                setValuePressure(
                    value = 1.0
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.set(1.0)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}