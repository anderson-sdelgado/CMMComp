package br.com.usinasantafe.cmm.presenter.view.mechanic.inputItem

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.mechanic.SetSeqItem
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class InputItemMechanicViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setSeqItem = mock<SetSeqItem>()
    private val viewModel = InputItemMechanicViewModel(
        setSeqItem = setSeqItem
    )

    @Test
    fun `setTextField - Check digit numeric`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.CLEAN)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.seqItem,
                "15"
            )
        }

    @Test
    fun `set - Check return failure if seqItem is empty`() =
        runTest {
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "InputItemMechanicViewModel.setTextField -> InputItemMechanicViewModel.set -> Field Empty!"
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
    fun `set - Check return failure if input is incorrect`() =
        runTest {
            viewModel.setTextField("1dfa456ds4fe", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "InputItemMechanicViewModel.setTextField -> InputItemMechanicViewModel.set -> For input string: \"1dfa456ds4fe\""
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return failure if input is value invalid`() =
        runTest {
            viewModel.setTextField("19759", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "InputItemMechanicViewModel.setTextField -> InputItemMechanicViewModel.set -> Value invalid!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID_VALUE
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return failure if have error in SetSeqItem`() =
        runTest {
            whenever(
                setSeqItem(1)
            ).thenReturn(
                resultFailure(
                    context = "SetSeqItem",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "InputItemMechanicViewModel.setTextField -> InputItemMechanicViewModel.set -> SetSeqItem -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return true if SetSeqItem execute successfully`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            verify(setSeqItem, atLeastOnce()).invoke(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
        }

}