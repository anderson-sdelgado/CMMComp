package br.com.usinasantafe.cmm.presenter.view.fertigation.collection

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetCollection
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CollectionViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setCollection = mock<SetCollection>()
    private val viewModel = CollectionViewModel(
        saveStateHandle = SavedStateHandle(
            mapOf(
                Args.ID_ARG to 1
            )
        ),
        setCollection = setCollection
    )

    @Test
    fun `setTextField - Check return failure if have error in `() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.collection,
                "0,1"
            )
        }

    @Test
    fun `setTextField - Check return failure if value hour meter is 0`() =
        runTest {
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "CollectionViewModel.setTextField -> CollectionViewModel.validateAndSet -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELD_EMPTY
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return failure if have error in SetCollection and CheckCollection return true`() =
        runTest {
            whenever(
                setCollection(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetCollection",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "CollectionViewModel.setTextField -> CollectionViewModel.validateAndSet -> CollectionViewModel.set -> SetCollection -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check data if SetCollection execute successfully and CheckCollection return true`() =
        runTest {
            whenever(
                setCollection(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                Result.success(Unit)
            )
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}