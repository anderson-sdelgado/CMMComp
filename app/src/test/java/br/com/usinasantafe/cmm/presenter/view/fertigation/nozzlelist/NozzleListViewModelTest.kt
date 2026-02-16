package br.com.usinasantafe.cmm.presenter.view.fertigation.nozzlelist

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListNozzle
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetIdNozzle
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableNozzle
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
class NozzleListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listNozzle = mock<ListNozzle>()
    private val setIdNozzle = mock<SetIdNozzle>()
    private val updateTableNozzle = mock<UpdateTableNozzle>()
    private val viewModel = NozzleListViewModel(
        listNozzle = listNozzle,
        setIdNozzle = setIdNozzle,
        updateTableNozzle = updateTableNozzle
    )

    @Test
    fun `list - Check return failure if have error in ListNozzle`() =
        runTest {
            whenever(
                listNozzle()
            ).thenReturn(
                resultFailure(
                    context = "ListNozzle",
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
                "NozzleListViewModel.list -> ListNozzle -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.status.flagFailure,
                true
            )
        }

    @Test
    fun `list - Check return true if ListNozzle execute successfully`() =
        runTest {
            whenever(
                listNozzle()
            ).thenReturn(
                Result.success(
                    listOf(
                        Nozzle(
                            id = 1,
                            cod = 1,
                            descr = "Item"
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
                entity.cod,
                1
            )
            assertEquals(
                entity.descr,
                "Item"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableNozzle`() =
        runTest {
            whenever(
                updateTableNozzle(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableNozzle -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                NozzleListState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                NozzleListState(
                    status = UpdateStatusState(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "NozzleListViewModel.updateAllDatabase -> UpdateTableNozzle -> java.lang.NullPointerException",
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
                "NozzleListViewModel.updateDatabase -> NozzleListViewModel.updateAllDatabase -> UpdateTableNozzle -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableNozzle(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(1f, 4f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(2f, 4f)
                    ),
                    UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                NozzleListState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(1f, 4f)
                    )
                )
            )
            assertEquals(
                result[1],
                NozzleListState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(2f, 4f),
                    )
                )
            )
            assertEquals(
                result[2],
                NozzleListState(
                    status = UpdateStatusState(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_nozzle",
                        currentProgress = percentage(3f, 4f),
                    )
                )
            )
            assertEquals(
                result[3],
                NozzleListState(
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
    fun `set - Check return failure if have error in SetIdTurn`() =
        runTest {
            whenever(
                setIdNozzle(
                    id = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "SetIdNozzle",
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
                "NozzleListViewModel.set -> SetIdNozzle -> java.lang.Exception"
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
                setIdNozzle(
                    id = 1
                )
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