package br.com.usinasantafe.cmm.presenter.view.splash

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.cmm.domain.usecases.header.CheckHeaderOpen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkHeaderOpen = mock<CheckHeaderOpen>()
    private val startWorkManager = mock<StartWorkManager>()
    private val viewModel = SplashViewModel(
        checkHeaderOpen = checkHeaderOpen,
        startWorkManager = startWorkManager
    )

    @Test
    fun `checkOpen - Check return failure if have error in CheckHeaderOpen`() =
        runTest {
            whenever(
                checkHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "CheckHeaderOpen",
                    "-",
                    Exception()
                )
            )
            viewModel.startApp()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SplashViewModel.startApp -> CheckHeaderOpen -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `checkOpen - Check return true if function execute successfully`() =
        runTest {
            whenever(
                checkHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.startApp()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.failure,
                ""
            )
            assertEquals(
                viewModel.uiState.value.flagHeaderOpen,
                true
            )
        }

    @Test
    fun `checkOpen - Check return false if function execute successfully`() =
        runTest {
            whenever(
                checkHeaderOpen()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.startApp()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.failure,
                ""
            )
            assertEquals(
                viewModel.uiState.value.flagHeaderOpen,
                false
            )
        }
}