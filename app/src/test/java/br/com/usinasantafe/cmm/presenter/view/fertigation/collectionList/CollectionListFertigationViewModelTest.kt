package br.com.usinasantafe.cmm.presenter.view.fertigation.collectionList

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListCollection
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.model.ItemValueOSScreenModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CollectionListFertigationViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listCollection = mock<ListCollection>()
    private val viewModel = CollectionListFertigationViewModel(
        savedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.NOTE_REEL_FERT.ordinal,
            )
        ),
        listCollection = listCollection
    )

    @Test
    fun `list - Check return failure if have error in usecase ListCollection`() =
        runTest {
            whenever(
                listCollection()
            ).thenReturn(
                resultFailure(
                    context = "ListCollection",
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
                "CollectionListViewModel.list -> ListCollection -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `list - Check return list if listCollection execute successfully`() =
        runTest {
            whenever(
                listCollection()
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

}