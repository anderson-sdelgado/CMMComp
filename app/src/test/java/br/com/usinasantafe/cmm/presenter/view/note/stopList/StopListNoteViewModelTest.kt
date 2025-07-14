package br.com.usinasantafe.cmm.presenter.view.note.stopList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.ListStop
import br.com.usinasantafe.cmm.domain.usecases.note.SetIdStopNote
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableRActivityStop
import br.com.usinasantafe.cmm.domain.usecases.updateTable.UpdateTableStop
import br.com.usinasantafe.cmm.presenter.model.StopScreenModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class StopListNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableRActivityStop = mock<UpdateTableRActivityStop>()
    private val updateTableStop = mock<UpdateTableStop>()
    private val listStop = mock<ListStop>()
    private val setIdStopNote = mock<SetIdStopNote>()
    private val viewModel = StopListNoteViewModel(
        updateTableRActivityStop = updateTableRActivityStop,
        updateTableStop = updateTableStop,
        listStop = listStop,
        setIdStopNote = setIdStopNote
    )

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableRActivityStop`() =
        runTest {
            whenever(
                updateTableRActivityStop(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableRActivityStop -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                StopListNoteState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "StopListNoteViewModel.updateAllDatabase -> UpdateTableRActivityStop -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "StopListNoteViewModel.updateAllDatabase -> UpdateTableRActivityStop -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableStop`() =
        runTest {
            whenever(
                updateTableRActivityStop(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(2f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTableStop(
                    count = 2f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(4f, 7f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableStop -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                5
            )
            assertEquals(
                result[0],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(4f, 7f)
                )
            )
            assertEquals(
                result[4],
                StopListNoteState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "StopListNoteViewModel.updateAllDatabase -> UpdateTableStop -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "StopListNoteViewModel.updateAllDatabase -> UpdateTableStop -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableRActivityStop(
                    count = 1f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(1f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(2f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_r_activity_stop",
                        currentProgress = percentage(3f, 7f)
                    ),
                )
            )
            whenever(
                updateTableStop(
                    count = 2f,
                    sizeAll = 7f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(4f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(5f, 7f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_stop",
                        currentProgress = percentage(6f, 7f)
                    )
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                7
            )
            assertEquals(
                result[0],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(4f, 7f)
                )
            )
            assertEquals(
                result[4],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(5f, 7f)
                )
            )
            assertEquals(
                result[5],
                StopListNoteState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_stop",
                    currentProgress = percentage(6f, 7f)
                )
            )
            assertEquals(
                result[6],
                StopListNoteState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.levelUpdate,
                LevelUpdate.FINISH_UPDATE_COMPLETED
            )
        }

    @Test
    fun `stopList - Check return failure if have error in GetStopList`() =
        runTest {
            whenever(
                listStop()
            ).thenReturn(
                resultFailure(
                    context = "GetStopList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.stopList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "StopListNoteViewModel.stopList -> GetStopList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `stopList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listStop()
            ).thenReturn(
                Result.success(
                    listOf(
                        StopScreenModel(
                            id = 1,
                            descr = "20 - PARADA REFEICAO"
                        )
                    )
                )
            )
            viewModel.stopList()
            val list = viewModel.uiState.value.stopList
            assertEquals(
                list.size,
                1
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.descr,
                "20 - PARADA REFEICAO"
            )
        }

    @Test
    fun `setIdStop - Check return failure if have error in SetIdStopNote`() =
        runTest {
            whenever(
                setIdStopNote(
                    id = 1
                )
            ).thenReturn(
                resultFailure(
                    context = "SetIdStopNote",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setIdStop(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "StopListNoteViewModel.setIdStop -> SetIdStopNote -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `setIdStop - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setIdStopNote(
                    id = 1
                )
            ).thenReturn(
                Result.success(
                    true
                )
            )
            viewModel.setIdStop(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `onFieldChanged - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listStop()
            ).thenReturn(
                Result.success(
                    listOf(
                        StopScreenModel(
                            id = 1,
                            descr = "20 - PARADA REFEICAO"
                        ),
                        StopScreenModel(
                            id = 2,
                            descr = "160 - MANUTENCAO"
                        ),
                        StopScreenModel(
                            id = 3,
                            descr = "250 - CHUVA"
                        )
                    )
                )
            )
            viewModel.stopList()
            viewModel.onFieldChanged("MANU")
            val list = viewModel.uiState.value.stopList
            assertEquals(
                list.size,
                1
            )
        }
}