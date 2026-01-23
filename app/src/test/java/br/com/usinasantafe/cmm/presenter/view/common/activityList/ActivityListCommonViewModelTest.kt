package br.com.usinasantafe.cmm.presenter.view.common.activityList

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListActivity
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.update.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.LevelUpdate
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
class ActivityListCommonViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableActivity = mock<UpdateTableActivity>()
    private val listActivity = mock<ListActivity>()
    private val setIdActivityCommon = mock<SetIdActivityCommon>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = ActivityListCommonViewModel(
        savedStateHandle,
        updateTableActivity = updateTableActivity,
        listActivity = listActivity,
        setIdActivityCommon = setIdActivityCommon
    )

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateTableActivity`() =
        runTest {
            whenever(
                updateTableActivity(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableActivity -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                ActivityListCommonState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                ActivityListCommonState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ActivityListCommonViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException",
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
                "ActivityListCommonViewModel.updateDatabase -> ActivityListCommonViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                updateTableActivity(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                result[0],
                ActivityListCommonState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                ActivityListCommonState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                ActivityListCommonState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                ActivityListCommonState(
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

    @Test
    fun `activityList - Check return failure if have error in GetActivityList`() =
        runTest {
            whenever(
                listActivity()
            ).thenReturn(
                resultFailure(
                    context = "GetActivityList",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.activityList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ActivityListCommonViewModel.activityList -> GetActivityList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `activityList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listActivity()
            ).thenReturn(
                Result.success(
                    listOf(
                        Activity(
                            idActivity = 1,
                            codActivity = 1,
                            descrActivity = "Test"
                        )
                    )
                )
            )
            val viewModel = createViewModel()
            viewModel.activityList()
            val list = viewModel.uiState.value.activityList
            assertEquals(
                list.size,
                1
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                1
            )
            assertEquals(
                entity1.descrActivity,
                "Test"
            )
        }
    
    @Test
    fun `setIdActivity - Check return failure if have error in SetIdActivityCommon - HEADER_DEFAULT`() =
        runTest {
            whenever(
                setIdActivityCommon(
                    id = 1,
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                resultFailure(
                    context = "GetActivityList",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setIdActivity(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ActivityListCommonViewModel.setIdActivity -> GetActivityList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `setIdActivity - Check return correct if function execute successfully - HEADER_DEFAULT`() =
        runTest {
            whenever(
                setIdActivityCommon(
                    id = 1,
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(
                    FlowApp.HEADER_INITIAL
                )
            )
            val viewModel = createViewModel()
            viewModel.setIdActivity(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flowApp,
                FlowApp.HEADER_INITIAL
            )
        }

}