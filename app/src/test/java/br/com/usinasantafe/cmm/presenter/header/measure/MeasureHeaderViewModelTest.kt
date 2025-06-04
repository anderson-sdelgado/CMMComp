package br.com.usinasantafe.cmm.presenter.header.measure

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.header.CheckMeasure
import br.com.usinasantafe.cmm.domain.usecases.header.SetMeasureInitial
import br.com.usinasantafe.cmm.presenter.model.CheckMeasureModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MeasureHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkMeasure = mock<CheckMeasure>()
    private val setMeasureInitial = mock<SetMeasureInitial>()
    private val viewModel = MeasureHeaderViewModel(
        checkMeasure = checkMeasure,
        setMeasureInitial = setMeasureInitial
    )
    
    @Test
    fun `setTextField - Check return failure if value measure is 0`() =
        runTest {
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MeasureViewModel.setTextField.OK -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELDEMPTY
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `checkMeasureHeader - Check return failure if have error in CheckMeasure`() =
        runTest {
            whenever(
                checkMeasure("10.000,0")
            ).thenReturn(
                resultFailure(
                    context = "ICheckMeasure",
                    message = "-",
                    cause = Exception()
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
                "MeasureViewModel.checkMeasureHeader -> ICheckMeasure -> java.lang.Exception"
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
    fun `checkMeasureHeader - Check return invalid if CheckMeasure to return false`() =
        runTest {
            whenever(
                checkMeasure("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "20.0000,0",
                        check = false
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
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.measureOld,
                "20.0000,0"
            )
        }

    @Test
    fun `setMeasureInitialHeader - Check return failure if have error in SetMeasureInitial`() =
        runTest {
            whenever(
                checkMeasure("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "5.0000,0",
                        check = true
                    )
                )
            )
            whenever(
                setMeasureInitial("10.000,0")
            ).thenReturn(
                resultFailure(
                    context = "ISetMeasureInitial",
                    message = "-",
                    cause = Exception()
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
                "MeasureViewModel.setMeasureInitialHeader -> ISetMeasureInitial -> java.lang.Exception"
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
    fun `setMeasureInitialHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkMeasure("10.000,0")
            ).thenReturn(
                Result.success(
                    CheckMeasureModel(
                        measureBD = "5.0000,0",
                        check = true
                    )
                )
            )
            whenever(
                setMeasureInitial("10.000,0")
            ).thenReturn(
                Result.success(true)
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