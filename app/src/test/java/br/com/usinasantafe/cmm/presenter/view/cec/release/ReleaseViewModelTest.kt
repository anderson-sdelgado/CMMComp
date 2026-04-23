package br.com.usinasantafe.cmm.presenter.view.cec.release

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.cec.CheckIdRelease
import br.com.usinasantafe.cmm.domain.usecases.cec.SetIdRelease
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.TypeButton
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ReleaseViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkIdRelease = mock<CheckIdRelease>()
    private val setIdRelease = mock<SetIdRelease>()
    private val viewModel = ReleaseViewModel(
        checkIdRelease = checkIdRelease,
        setIdRelease = setIdRelease
    )

    @Test
    fun `setTextField - Check add char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.idRelease,
            "1"
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
        viewModel.setTextField(
            "19759",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.idRelease,
            "191"
        )
    }

    @Test
    fun `set - Check msg of empty field`() {
        viewModel.setTextField(
            "OK",
            TypeButton.OK
        )
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELD_EMPTY
        )
    }

    @Test
    fun `set - Check return failure if have error in CheckIdRelease`() =
        runTest {
            whenever(
                checkIdRelease("20000")
            ).thenReturn(
                resultFailure(
                    context = "CheckIdRelease",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "20000",
            TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReleaseViewModel.setTextField -> ReleaseViewModel.set -> CheckIdRelease -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check return failure if CheckIdRelease return false`() =
        runTest {
            whenever(
                checkIdRelease("20000")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setTextField(
                "20000",
                TypeButton.NUMERIC
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReleaseViewModel.set -> INVALID"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `set - Check return failure if have error in SetIdRelease`() =
        runTest {
            whenever(
                checkIdRelease("20000")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setIdRelease("20000")
            ).thenReturn(
                resultFailure(
                    context = "SetIdRelease",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "20000",
                TypeButton.NUMERIC
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReleaseViewModel.set -> SetIdRelease -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `set - Check return true if SetIdRelease execute successfully and return true`() =
        runTest {
            whenever(
                checkIdRelease("20000")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setIdRelease("20000")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "20000",
                TypeButton.NUMERIC
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagEnd,
                true
            )
        }

}