package br.com.usinasantafe.cmm.presenter.configuration.initial

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.CheckAccessInitial
import br.com.usinasantafe.cmm.domain.usecases.common.GetStatusSend
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class InitialMenuNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getStatusSend = mock<GetStatusSend>()
    private val checkAccessInitial = mock<CheckAccessInitial>()
    private val viewModel = InitialMenuViewModel(
        getStatusSend = getStatusSend,
        checkAccessInitial = checkAccessInitial
    )
    
    @Test
    fun `recoverStatusSend - Check return failure if have error in GetStatusSend`() =
        runTest {
            whenever(
                getStatusSend()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            viewModel.recoverStatusSend()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.failureStatus,
                "InitialMenuViewModel.recoverStatusSend -> Error -> Exception -> java.lang.Exception",
            )
        }

    @Test
    fun `recoverStatusSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getStatusSend()
            ).thenReturn(
                Result.success(
                    StatusSend.SENT
                )
            )
            viewModel.recoverStatusSend()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.failureStatus,
                ""
            )
            assertEquals(
                uiState.statusSend,
                StatusSend.SENT
            )
        }

    @Test
    fun `checkAccess - Check return failure if have error in CheckAccessInitial`() =
        runTest {
            whenever(
                checkAccessInitial()
            ).thenReturn(
                resultFailure(
                    "Error",
                    "Exception",
                    Exception()
                )
            )
            viewModel.checkAccess()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagAccess,
                false
            )
            assertEquals(
                uiState.flagFailure,
                true
            )
            assertEquals(
                uiState.failure,
                "InitialMenuViewModel.checkAccess -> Error -> Exception -> java.lang.Exception",
            )
        }

    @Test
    fun `checkAccess - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkAccessInitial()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.checkAccess()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.flagDialog,
                true
            )
            assertEquals(
                uiState.flagAccess,
                false
            )
            assertEquals(
                uiState.flagFailure,
                false
            )
        }

}