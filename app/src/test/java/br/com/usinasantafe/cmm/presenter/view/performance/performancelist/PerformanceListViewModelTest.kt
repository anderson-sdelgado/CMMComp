package br.com.usinasantafe.cmm.presenter.view.performance.performancelist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.performance.CheckClosePerformance
import br.com.usinasantafe.cmm.domain.usecases.performance.ListPerformance
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.presenter.view.performance.performanceList.PerformanceListViewModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PerformanceListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listPerformance = mock<ListPerformance>()
    private val checkClosePerformance = mock<CheckClosePerformance>()
    private val viewModel = PerformanceListViewModel(
        savedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.PERFORMANCE.ordinal,
            )
        ),
        listPerformance = listPerformance,
        checkClosePerformance = checkClosePerformance
    )

    @Test
    fun `list - Check return failure if have error in usecase ListPerformance`() =
        runTest {
            whenever(
                listPerformance()
            ).thenReturn(
                resultFailure(
                    context = "ListPerformance",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "PerformanceListViewModel.list -> ListPerformance -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `list - Check return true if listPerformance execute successfully`() =
        runTest {
            whenever(
                listPerformance()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemValueOSScreenModel(
                            id = 1,
                            nroOS = 123456,
                            value = null
                        )
                    )
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.list,
                listOf(
                    ItemValueOSScreenModel(
                        id = 1,
                        nroOS = 123456,
                        value = null
                    )
                )
            )
        }

    @Test
    fun `checkClose - Check return failure if have error in CheckClosePerformance`() =
        runTest {
            whenever(
                checkClosePerformance()
            ).thenReturn(
                resultFailure(
                    context = "CheckClosePerformance",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.checkClose()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "PerformanceListViewModel.checkClose -> CheckClosePerformance -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `checkClose - Check return failure if CheckClosePerformance return false`() =
        runTest {
            whenever(
                checkClosePerformance()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.checkClose()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "PerformanceListViewModel.checkClose -> "
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID_CLOSE_PERFORMANCE
            )
        }

    @Test
    fun `checkClose - Check return true if CheckClosePerformance return true`() =
        runTest {
            whenever(
                checkClosePerformance()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.checkClose()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }
}