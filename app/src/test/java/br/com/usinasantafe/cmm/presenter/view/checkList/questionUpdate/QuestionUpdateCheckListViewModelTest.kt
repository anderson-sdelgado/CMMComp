package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.checkList.CheckUpdateCheckList
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableItemCheckListByNroEquip
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
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
class QuestionUpdateCheckListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkUpdateCheckList = mock<CheckUpdateCheckList>()
    private val updateTableItemCheckListByNroEquip = mock<UpdateTableItemCheckListByNroEquip>()
    private val viewModel = QuestionUpdateCheckListViewModel(
        checkUpdateCheckList = checkUpdateCheckList,
        updateTableItemCheckListByNroEquip = updateTableItemCheckListByNroEquip
    )

    @Test
    fun `checkUpdate - Check return failure if have error in CheckUpdateCheckList`() =
        runTest {
            whenever(
                checkUpdateCheckList()
            ).thenReturn(
                resultFailure(
                    context = "CheckUpdateCheckList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.checkUpdate()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "QuestionUpdateCheckListViewModel.checkUpdate -> CheckUpdateCheckList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `checkUpdate - Check return false if CheckUpdateCheckList return true`() =
        runTest {
            whenever(
                checkUpdateCheckList()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.checkUpdate()
            assertEquals(
                viewModel.uiState.value.flagCheckUpdate,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableItemCheckListByNroEquip`() =
        runTest {
            whenever(
                updateTableItemCheckListByNroEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableItemCheckListByNroEquip -> java.lang.NullPointerException",
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
                QuestionUpdateCheckListState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                QuestionUpdateCheckListState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "QuestionUpdateCheckListViewModel.updateAllDatabase -> UpdateTableItemCheckListByNroEquip -> java.lang.NullPointerException",
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
                "QuestionUpdateCheckListViewModel.updateAllDatabase -> UpdateTableItemCheckListByNroEquip -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableItemCheckListByNroEquip(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_item_check_list",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                result[0],
                QuestionUpdateCheckListState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                QuestionUpdateCheckListState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                QuestionUpdateCheckListState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                QuestionUpdateCheckListState(
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
        }

}