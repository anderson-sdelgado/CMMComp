package br.com.usinasantafe.cmm.presenter.header.os

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.header.CheckNroOS
import br.com.usinasantafe.cmm.domain.usecases.header.SetNroOS
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
class OSViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkNroOS = mock<CheckNroOS>()
    private val setNroOS = mock<SetNroOS>()
    private val viewModel = OSViewModel(
        checkNroOS = checkNroOS,
        setNroOS = setNroOS
    )

    @Test
    fun `setTextField - Check add char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroOS,
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
            viewModel.uiState.value.nroOS,
            "191"
        )
    }

    @Test
    fun `setTextField - Check msg of empty field`() {
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
            Errors.FIELDEMPTY
        )
    }

    @Test
    fun `setNroOSHeader - Check return failure if have error in CheckNroOS`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                resultFailure(
                    context = "ICheckNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
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
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.setNroOSHeader -> ICheckNroOS -> java.lang.Exception"
                )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `setNroOSHeader - Check return invalid if data is not found`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setTextField(
                "123456",
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
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setNroOSHeader - Check return failure if have error in SetNroOS`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS("123456")
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
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
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
                )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.setNroOSHeader -> ISetNroOS -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `setNroOSHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkNroOS("123456")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS("123456")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
                )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }


}