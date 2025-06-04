package br.com.usinasantafe.cmm.presenter.common.activityList

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.header.GetActivityList
import br.com.usinasantafe.cmm.domain.usecases.common.SetIdActivityCommon
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
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
    private val getActivityList = mock<GetActivityList>()
    private val setIdActivityCommon = mock<SetIdActivityCommon>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.HEADER_DEFAULT.ordinal,
            )
        )
    ) = ActivityListCommonViewModel(
        savedStateHandle,
        updateTableActivity = updateTableActivity,
        getActivityList = getActivityList,
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
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_activity",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "UpdateTableActivity -> java.lang.NullPointerException",
                        msgProgress = "UpdateTableActivity -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                ActivityListCommonState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_activity",
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
                    msgProgress = "ActivityListCommonViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "ActivityListCommonViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException"
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
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_activity",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_activity do Web Service",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_activity",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val viewModel = createViewModel()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                ActivityListCommonState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_activity",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                ActivityListCommonState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_activity do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                ActivityListCommonState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_activity",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                ActivityListCommonState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.updateDatabase()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "Atualização de dados realizado com sucesso!"
            )
        }

    @Test
    fun `activityList - Check return failure if have error in GetActivityList`() =
        runTest {
            whenever(
                getActivityList()
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
                getActivityList()
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
    fun `setIdActivity - Check return failure if have error in SetIdActivity - HEADER_DEFAULT`() =
        runTest {
            whenever(
                setIdActivityCommon(1)
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
                setIdActivityCommon(1)
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val viewModel = createViewModel()
            viewModel.setIdActivity(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `setIdActivity - Check return failure if have error in SetIdActivity - NOTE_WORK`() =
        runTest {
            whenever(
                setIdActivityCommon(
                    id = 1,
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                resultFailure(
                    context = "GetActivityList",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
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
    fun `setIdActivity - Check return correct if function execute successfully - NOTE_WORK`() =
        runTest {
            whenever(
                setIdActivityCommon(
                    id = 1,
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARGS to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.setIdActivity(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}