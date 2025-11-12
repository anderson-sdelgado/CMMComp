package br.com.usinasantafe.cmm.presenter.view.note.historyList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListHistory
import br.com.usinasantafe.cmm.presenter.model.ItemHistoryScreenModel
import br.com.usinasantafe.cmm.utils.WORK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HistoryListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listHistory = mock<ListHistory>()
    private val viewModel = HistoryListViewModel(
        listHistory = listHistory
    )

    @Test
    fun `recoverHistoryList - Check return failure if have error in HistoryList`() =
        runTest {
            whenever(
                listHistory()
            ).thenReturn(
                resultFailure(
                    context = "HistoryList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.recoverHistoryList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HistoryListViewModel.recoverHistoryList -> HistoryList -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverHistoryList - Check return true if HistoryList execute successfully and list is empty`() =
        runTest {
            whenever(
                listHistory()
            ).thenReturn(
                Result.success(emptyList())
            )
            viewModel.recoverHistoryList()
            assertEquals(
                viewModel.uiState.value.historyList,
                emptyList()
            )
        }

    @Test
    fun `recoverHistoryList - Check return true if HistoryList execute successfully`() =
        runTest {
            whenever(
                listHistory()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemHistoryScreenModel(
                            id = 1,
                            function = 1 to WORK,
                            descr = "CARREGAMENTO",
                            dateHour = "22/10/2025 08:20",
                            detail = ""
                        )
                    )
                )
            )
            viewModel.recoverHistoryList()
            val list = viewModel.uiState.value.historyList
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.function.first,
                1
            )
            assertEquals(
                model.function.second,
                WORK
            )
            assertEquals(
                model.descr,
                "CARREGAMENTO"
            )
            assertEquals(
                model.dateHour,
                "22/10/2025 08:20"
            )
            assertEquals(
                model.detail,
                ""
            )
        }

}