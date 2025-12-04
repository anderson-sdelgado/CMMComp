package br.com.usinasantafe.cmm.presenter.view.configuration.initial

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.CheckAccessInitial
import br.com.usinasantafe.cmm.domain.usecases.config.GetStatusSend
import br.com.usinasantafe.cmm.lib.StatusSend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class InitialMenuViewModelTest {

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
                    "GetStatusSend",
                    "-",
                    Exception()
                )
            )
            viewModel.recoverStatusSend()
            val uiState = viewModel.uiState.value
            assertEquals(
                uiState.failure,
                "InitialMenuViewModel.recoverStatusSend -> GetStatusSend -> java.lang.Exception",
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
                uiState.failure,
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
                    "CheckAccessInitial",
                    "-",
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
                "InitialMenuViewModel.checkAccess -> CheckAccessInitial -> java.lang.Exception",
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