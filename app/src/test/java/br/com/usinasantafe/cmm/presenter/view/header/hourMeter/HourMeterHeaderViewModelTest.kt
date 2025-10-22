package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.checkList.CheckOpenCheckList
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHourMeter
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetHourMeter
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowApp
import br.com.usinasantafe.cmm.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val checkOpenCheckList = mock<CheckOpenCheckList>()
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
        checkOpenCheckList = checkOpenCheckList
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
                "HourMeterHeaderViewModel.setTextField.OK -> Field Empty!"
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
                "HourMeterHeaderViewModel.checkHourMeterHeader -> ICheckHourMeter -> java.lang.Exception"
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
    fun `setHourMeterHeader - Check return failure if have error in SetHourMeterInitial - FlowApp HEADER_INITIAL`() =
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
                setHourMeter(hourMeter = "10.000,0")
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
                "HourMeterHeaderViewModel.setHourMeterHeader -> ISetHourMeter -> java.lang.Exception"
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
    fun `setHourMeterHeader - Check return failure if have error in CheckOpenCheckList - FlowApp HEADER_INITIAL`() =
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
                setHourMeter("10.000,0")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkOpenCheckList()
            ).thenReturn(
                resultFailure(
                    context = "ICheckOpenCheckList",
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
                "HourMeterHeaderViewModel.setHourMeterHeader -> ICheckOpenCheckList -> java.lang.Exception"
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
    fun `setHourMeterHeader - Check return flowApp is HEADER_INITIAL if CheckOpenCheckList is false - FlowApp HEADER_INITIAL`() =
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
                setHourMeter("10.000,0")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkOpenCheckList()
            ).thenReturn(
                Result.success(false)
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

    @Test
    fun `setHourMeterHeader - Check return flowApp is CHECK_LIST if CheckOpenCheckList is true - FlowApp HEADER_INITIAL`() =
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
                setHourMeter("10.000,0")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkOpenCheckList()
            ).thenReturn(
                Result.success(false)
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

    @Test
    fun `setHourMeterHeader - Check return failure if have error in SetHourMeterInitial - FlowApp HEADER_FINISH`() =
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
                    flowApp = FlowApp.HEADER_FINISH
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetHourMeter",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                savedStateHandle = SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.HEADER_FINISH.ordinal,
                    )
                )
            )
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
                "HourMeterHeaderViewModel.setHourMeterHeader -> ISetHourMeter -> java.lang.Exception"
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
    fun `setHourMeterHeader - Check return correct if function execute successfully - FlowApp HEADER_FINISH`() =
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
                    flowApp = FlowApp.HEADER_FINISH
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel(
                savedStateHandle = SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.HEADER_FINISH.ordinal,
                    )
                )
            )
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
        }

}