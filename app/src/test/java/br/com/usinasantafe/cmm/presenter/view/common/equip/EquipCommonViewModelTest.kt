package br.com.usinasantafe.cmm.presenter.view.common.equip

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.cec.SetNroEquipPreCEC
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdEquipMotoMec
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.Args
import br.com.usinasantafe.cmm.presenter.view.common.equip.EquipCommonViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EquipCommonViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDescrEquip = mock<GetDescrEquip>()
    private val setIdEquipMotoMec = mock<SetIdEquipMotoMec>()
    private val setNroEquipPreCEC = mock<SetNroEquipPreCEC>()
    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARG to FlowApp.HEADER_INITIAL.ordinal,
            )
        )
    ) = EquipCommonViewModel(
        savedStateHandle,
        getDescrEquip = getDescrEquip,
        setIdEquipMotoMec = setIdEquipMotoMec,
        setNroEquipPreCEC = setNroEquipPreCEC
    )

    @Test
    fun `get - Check return failure if have error in GetDescrEquip`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                resultFailure(
                    context = "IGetDescrEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "EquipCommonViewModel.get -> IGetDescrEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                Result.success("teste")
            )
            val viewModel = createViewModel()
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.description,
                "teste"
            )
        }

    @Test
    fun `set - Check return failure if have error in SetIdEquip`() =
        runTest {
            whenever(
                setIdEquipMotoMec()
            ).thenReturn(
                resultFailure(
                    context = "ISetIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "EquipCommonViewModel.set -> ISetIdEquip -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.checkClass,
                null
            )
        }

    @Test
    fun `set - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setIdEquipMotoMec()
            ).thenReturn(
                Result.success(Unit)
            )
            val viewModel = createViewModel()
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.checkClass,
                null
            )
        }

    @Test
    fun `set - Check return failure if have error in SetNroEquipPreCEC - FlowApp PRE_CEC`() =
        runTest {
            whenever(
                setNroEquipPreCEC()
            ).thenReturn(
                resultFailure(
                    context = "ISetNroEquipPreCEC",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.PRE_CEC.ordinal,
                    )
                )
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "EquipCommonViewModel.set -> ISetNroEquipPreCEC -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.checkClass,
                null
            )
        }

    @Test
    fun `set - Check return true if SetNroEquipPreCEC execute successfully and return is true - FlowApp PRE_CEC`() =
        runTest {
            whenever(
                setNroEquipPreCEC()
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.PRE_CEC.ordinal,
                    )
                )
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.checkClass,
                true
            )
        }

    @Test
    fun `set - Check return false if SetNroEquipPreCEC execute successfully and return is false - FlowApp PRE_CEC`() =
        runTest {
            whenever(
                setNroEquipPreCEC()
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = createViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.FLOW_APP_ARG to FlowApp.PRE_CEC.ordinal,
                    )
                )
            )
            viewModel.set()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.checkClass,
                false
            )
        }

}