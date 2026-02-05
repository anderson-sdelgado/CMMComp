package br.com.usinasantafe.cmm.presenter.view.note.performance

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckPerformance
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetPerformance
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
class PerformanceViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setPerformance = mock<SetPerformance>()
    private val checkPerformance = mock<CheckPerformance>()
    private val viewModel = PerformanceViewModel(
        saveStateHandle = SavedStateHandle(
            mapOf(
                Args.ID_ARG to 1
            )
        ),
        setPerformance = setPerformance,
        checkPerformance = checkPerformance
    )

    @Test
    fun `setTextField - Check return failure if have error in `() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.performance,
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
                "PerformanceViewModel.setTextField -> PerformanceViewModel.validateAndSet -> Field Empty!"
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
    fun `set - Check return failure if have error in CheckPerformance`() =
        runTest {
            whenever(
                checkPerformance(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                resultFailure(
                    context = "CheckPerformance",
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
                "PerformanceViewModel.setTextField -> PerformanceViewModel.validateAndSet -> PerformanceViewModel.set -> CheckPerformance -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check data if CheckPerformance return false`() =
        runTest {
            whenever(
                checkPerformance(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                Result.success(false)
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
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `set - Check return failure if have error in SetPerformance and CheckPerformance return true`() =
        runTest {
            whenever(
                checkPerformance(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setPerformance(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetPerformance",
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
                "PerformanceViewModel.setTextField -> PerformanceViewModel.validateAndSet -> PerformanceViewModel.set -> SetPerformance -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check data if SetPerformance execute successfully and CheckPerformance return true`() =
        runTest {
            whenever(
                checkPerformance(
                    id = 1,
                    value = "50,0"
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setPerformance(
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