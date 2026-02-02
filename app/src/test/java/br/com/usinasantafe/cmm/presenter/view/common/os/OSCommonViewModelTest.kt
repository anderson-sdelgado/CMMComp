package br.com.usinasantafe.cmm.presenter.view.common.os

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetNroOSHeader
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroOS
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class OSCommonViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val hasNroOS = mock<HasNroOS>()
    private val setNroOS = mock<SetNroOS>()
    private val getNroOSHeader = mock<GetNroOSHeader>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = OSCommonViewModel(
        savedStateHandle,
        hasNroOS = hasNroOS,
        setNroOS = setNroOS,
        getNroOSHeader = getNroOSHeader
    )

    @Test
    fun `setTextField - Check add char`() {
        val viewModel = createViewModel()
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
        val viewModel = createViewModel()
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
        val viewModel = createViewModel()
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
    fun `setNroOS - Check return failure if have error in CheckNroOS`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                resultFailure(
                    context = "ICheckNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.validateAndSet -> OSCommonViewModel.setNroOS -> ICheckNroOS -> java.lang.Exception"
                )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setNroOS - Check return invalid if data is not found`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = createViewModel()
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
    fun `setNroOS - Check return failure if have error in SetNroOS`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS(
                    nroOS = "123456"
                )
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSCommonViewModel.setTextField -> OSCommonViewModel.validateAndSet -> OSCommonViewModel.setNroOS -> ISetNroOS -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setNroOS - Check return correct if function execute successfully - HEADER_INITIAL`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.HEADER_INITIAL
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS("123456")
            ).thenReturn(
                Result.success(Unit)
            )
            val viewModel = createViewModel()
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

    @Test
    fun `setNroOS - Check return correct if function execute successfully - NOTE_WORK`() =
        runTest {
            whenever(
                hasNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setNroOS(
                    nroOS = "123456",
                    flowApp = FlowApp.NOTE_WORK
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
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

    @Test
    fun `getNroOS - Check return failure if have error in GetNroOSHeader`() =
        runTest {
            whenever(
                getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    context = "IGetNroOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.getNroOS()
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
                "OSCommonViewModel.getNroOS -> IGetNroOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `getNrOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getNroOSHeader()
            ).thenReturn(
                Result.success("10000")
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.NOTE_WORK.ordinal,
                    )
                )
            )
            viewModel.getNroOS()
            assertEquals(
                viewModel.uiState.value.nroOS,
                "10000"
            )
        }

}