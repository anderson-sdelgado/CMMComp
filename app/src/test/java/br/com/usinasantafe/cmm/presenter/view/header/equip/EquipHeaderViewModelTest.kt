package br.com.usinasantafe.cmm.presenter.view.header.equip

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetIdEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EquipHeaderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDescrEquip = mock<GetDescrEquip>()
    private val setIdEquip = mock<SetIdEquip>()
    private val viewModel = EquipHeaderViewModel(
        getDescrEquip = getDescrEquip,
        setIdEquip = setIdEquip
    )

    @Test
    fun `getDescr - Check return failure if have error in GetDescrEquip`() =
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
            viewModel.getDescr()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "EquipHeaderViewModel.getDescr -> IGetDescrEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `getDescr - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                Result.success("teste")
            )
            viewModel.getDescr()
            assertEquals(
                viewModel.uiState.value.description,
                "teste"
            )
        }

    @Test
    fun `setEquip - Check return failure if have error in SetIdEquip`() =
        runTest {
            whenever(
                setIdEquip()
            ).thenReturn(
                resultFailure(
                    context = "ISetIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setIdEquipHeader()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "EquipHeaderViewModel.setIdEquipHeader -> ISetIdEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `setEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setIdEquip()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setIdEquipHeader()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}