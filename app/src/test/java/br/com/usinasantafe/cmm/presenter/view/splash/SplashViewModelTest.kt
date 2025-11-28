package br.com.usinasantafe.cmm.presenter.view.splash

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.domain.usecases.common.FlowAppOpen
import br.com.usinasantafe.cmm.utils.FlowApp
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

    private val flowAppOpen = mock<FlowAppOpen>()
    private val startWorkManager = mock<StartWorkManager>()
    private val viewModel = SplashViewModel(
        flowAppOpen = flowAppOpen,
        startWorkManager = startWorkManager
    )

    @Test
    fun `flowAppOpen - Check return failure if have error in CheckFlowAppOpen`() =
        runTest {
            whenever(
                flowAppOpen()
            ).thenReturn(
                resultFailure(
                    "CheckFlowAppOpen",
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
                "SplashViewModel.startApp -> CheckFlowAppOpen -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `checkOpen - Check return FlowApp HEADER_INITIAL if function execute successfully`() =
        runTest {
            whenever(
                flowAppOpen()
            ).thenReturn(
                Result.success(FlowApp.HEADER_INITIAL)
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
                viewModel.uiState.value.flowApp,
                FlowApp.HEADER_INITIAL
            )
        }

}