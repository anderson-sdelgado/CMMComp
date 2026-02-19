package br.com.usinasantafe.cmm.presenter.view.fertigation.speedList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListSpeed
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetSpeedPressure
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
class SpeedListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listSpeed = mock<ListSpeed>()
    private val setSpeedPressure = mock<SetSpeedPressure>()
    private val updateTablePressure = mock<UpdateTablePressure>()
    private val viewModel = SpeedListViewModel(
        listSpeed = listSpeed,
        setSpeedPressure = setSpeedPressure,
        updateTablePressure = updateTablePressure
    )


    @Test
    fun `list - Check return failure if have error in ListSpeed`() =
        runTest {
            whenever(
                listSpeed()
            ).thenReturn(
                resultFailure(
                    context = "ListSpeed",
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
                "SpeedListViewModel.list -> ListSpeed -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
        }

    @Test
    fun `list - Check return true if ListSpeed execute successfully`() =
        runTest {
            whenever(
                listSpeed()
            ).thenReturn(
                Result.success(
                    listOf(
                        Pressure(
                            id = 1,
                            idNozzle = 1,
                            value = 1.0,
                            speed = 1
                        )
                    )
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
                entity.id,
                1
            )
            assertEquals(
                entity.idNozzle,
                1
            )
            assertEquals(
                entity.value,
                1.0
            )
            assertEquals(
                entity.speed,
                1
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
                SpeedListState(
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
                SpeedListState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "SpeedListViewModel.updateAllDatabase -> UpdateTablePressure -> java.lang.NullPointerException",
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
                "SpeedListViewModel.updateDatabase -> SpeedListViewModel.updateAllDatabase -> UpdateTablePressure -> java.lang.NullPointerException"
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
                SpeedListState(
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
                SpeedListState(
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
                SpeedListState(
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
                SpeedListState(
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
    fun `set - Check return failure if have error in SetSpeedPressure`() =
        runTest {
            whenever(
                setSpeedPressure(1)
            ).thenReturn(
                resultFailure(
                    context = "SetSpeedPressure",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.set(1)
            assertEquals(
                viewModel.uiState.value.status.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.status.failure,
                "SpeedListViewModel.set -> SetSpeedPressure -> java.lang.Exception"
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
                setSpeedPressure(1)
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.set(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }


}