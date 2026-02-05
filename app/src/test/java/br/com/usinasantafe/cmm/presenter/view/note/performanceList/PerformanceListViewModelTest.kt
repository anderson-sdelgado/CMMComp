package br.com.usinasantafe.cmm.presenter.view.note.performanceList

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListPerformance
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args
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

    private val viewModel = PerformanceListViewModel(
        savedStateHandle = SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
                )
        ),
        listPerformance = listPerformance
    )

    @Test
    fun `performanceList - Check return failure if have error in usecase ListPerformance`() =
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
            viewModel.performanceList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TranshipmentViewModel.setTextField -> TranshipmentViewModel.setNroEquip -> IHasEquipSecondary -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }


}