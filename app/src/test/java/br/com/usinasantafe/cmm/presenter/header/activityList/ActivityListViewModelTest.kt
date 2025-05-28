package br.com.usinasantafe.cmm.presenter.header.activityList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.header.GetActivityList
import br.com.usinasantafe.cmm.domain.usecases.header.SetIdActivity
import br.com.usinasantafe.cmm.domain.usecases.updatetable.UpdateTableActivity
import br.com.usinasantafe.cmm.presenter.header.turnlist.TurnListState
import br.com.usinasantafe.cmm.utils.Errors
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
class ActivityListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableActivity = mock<UpdateTableActivity>()
    private val getActivityList = mock<GetActivityList>()
    private val setIdActivity = mock<SetIdActivity>()
    private val viewModel = ActivityListViewModel(
        updateTableActivity = updateTableActivity,
        getActivityList = getActivityList,
        setIdActivity = setIdActivity
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
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                ActivityListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_activity",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                ActivityListState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "ActivityListViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException",
                    msgProgress = "ActivityListViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException",
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
                "ActivityListViewModel.updateAllDatabase -> UpdateTableActivity -> java.lang.NullPointerException"
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
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 4)
            assertEquals(
                result[0],
                ActivityListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_activity",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                ActivityListState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_activity do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            assertEquals(
                result[2],
                ActivityListState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_activity",
                    currentProgress = percentage(3f, 4f),
                )
            )
            assertEquals(
                result[3],
                ActivityListState(
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
            viewModel.activityList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ActivityListViewModel.activityList -> GetActivityList -> java.lang.Exception"
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
    fun `setIdActivityHeader - Check return failure if have error in SetIdActivity`() =
        runTest {
            whenever(
                setIdActivity(1)
            ).thenReturn(
                resultFailure(
                    context = "GetActivityList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setIdActivityHeader(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ctivityListViewModel.setIdActivityHeader -> GetActivityList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
        }

    @Test
    fun `setIdActivityHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setIdActivity(1)
            ).thenReturn(
                Result.success(
                    true
                )
            )
            viewModel.setIdActivityHeader(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}