package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHourMeter
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetHourMeter
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HourMeterHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkHourMeter = mock<CheckHourMeter>()
    private val setHourMeter = mock<SetHourMeter>()

    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = HourMeterHeaderViewModel(
        savedStateHandle,
        checkHourMeter = checkHourMeter,
        setHourMeter = setHourMeter,
    )
    
    @Test
    fun `setTextField - Check return failure if value measure is 0`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HourMeterHeaderViewModel.setTextField -> HourMeterHeaderViewModel.validateAndSet -> Field Empty!"
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
    fun `checkHourMeterHeader - Check return failure if have error in CheckHourMeter`() =
        runTest {
            whenever(
                checkHourMeter("10.000,0")
            ).thenReturn(
                resultFailure(
                    context = "ICheckHourMeter",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HourMeterHeaderViewModel.setTextField -> HourMeterHeaderViewModel.validateAndSet -> HourMeterHeaderViewModel.setHourMeterHeader -> ICheckHourMeter -> java.lang.Exception"
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
    fun `checkHourMeterHeader - Check return invalid if CheckHourMeter to return false`() =
        runTest {
            whenever(
                checkHourMeter("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "20.0000,0",
                        check = false
                    )
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
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
                viewModel.uiState.value.hourMeterOld,
                "20.0000,0"
            )
        }

    @Test
    fun `setHourMeterHeader - Check return failure if have error in SetHourMeterInitial`() =
        runTest {
            whenever(
                checkHourMeter("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "5.0000,0",
                        check = true
                    )
                )
            )
            whenever(
                setHourMeter(
                    hourMeter = "10.000,0",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetHourMeter",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)

            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HourMeterHeaderViewModel.setTextField -> HourMeterHeaderViewModel.validateAndSet -> HourMeterHeaderViewModel.setHourMeterHeader -> ISetHourMeter -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flowApp,
                FlowApp.HEADER_INITIAL
            )
        }

    @Test
    fun `setHourMeterHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkHourMeter("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "5.0000,0",
                        check = true
                    )
                )
            )
            whenever(
                setHourMeter(
                    hourMeter = "10.000,0",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(FlowApp.HEADER_INITIAL)
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flowApp,
                FlowApp.HEADER_INITIAL
            )
        }

}