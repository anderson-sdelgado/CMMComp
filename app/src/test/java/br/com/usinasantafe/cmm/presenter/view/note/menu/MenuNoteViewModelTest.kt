package br.com.usinasantafe.cmm.presenter.view.note.menu

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.note.CheckHasNoteHeaderOpen
import br.com.usinasantafe.cmm.domain.usecases.common.ListItemMenu
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.FlowNote
import br.com.usinasantafe.cmm.utils.TypeView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MenuNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listItemMenu = mock<ListItemMenu>()
    private val getDescrEquip = mock<GetDescrEquip>()
    private val checkHasNoteHeaderOpen = mock<CheckHasNoteHeaderOpen>()
    private val viewModel = MenuNoteViewModel(
        listItemMenu = listItemMenu,
        getDescrEquip = getDescrEquip,
        checkHasNoteHeaderOpen = checkHasNoteHeaderOpen
    )

    @Test
    fun `menuList - Check return failure if have error in GetItemMenuList`() =
        runTest {
            whenever(
                listItemMenu()
            ).thenReturn(
                resultFailure(
                    context = "GetItemMenuList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.menuList -> GetItemMenuList -> java.lang.Exception"
            )
        }

    @Test
    fun `menuList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listItemMenu()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            title = "TRABALHANDO",
                            TypeView.ITEM
                        ),
                        ItemMenuModel(
                            id = 2,
                            title = "PARADO",
                            TypeView.ITEM
                        ),
                        ItemMenuModel(
                            id = 3,
                            title = "FINALIZAR BOLETIM",
                            TypeView.BUTTON
                        )
                    )
                )
            )
            viewModel.menuList()
            val menuList = viewModel.uiState.value.menuList
            assertEquals(
                menuList.size,
                2
            )
            val item1 = menuList[0]
            assertEquals(
                item1.id,
                1
            )
            assertEquals(
                item1.title,
                "TRABALHANDO"
            )
            val item2 = menuList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.title,
                "PARADO"
            )
            assertEquals(
                viewModel.uiState.value.textButtonReturn,
                "FINALIZAR BOLETIM"
            )
        }

    @Test
    fun `descrEquip - Check return failure if have error in getDescrEquip`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                resultFailure(
                    context = "GetDescrEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.descrEquip()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.descrEquip -> GetDescrEquip -> java.lang.Exception"
            )
        }

    @Test
    fun `descrEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDescrEquip()
            ).thenReturn(
                Result.success("2200 - TRATOR")
            )
            viewModel.descrEquip()
            assertEquals(
                viewModel.uiState.value.descrEquip,
                "2200 - TRATOR"
            )
        }

    @Test
    fun `setSelection - Check return failure if option selection is invalid`() =
        runTest {
            viewModel.setSelection(0)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> Option Invalid!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setSelection - Check return correct if function execute successfully`() =
        runTest {
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flowNote,
                FlowNote.WORK
            )
        }

    @Test
    fun `onButtonReturn - Check return failure if have error in CheckNoteHeaderOpen`() =
        runTest {
            whenever(
                checkHasNoteHeaderOpen()
            ).thenReturn(
                resultFailure(
                    context = "CheckNoteHeaderOpen",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onButtonReturn -> CheckNoteHeaderOpen -> java.lang.Exception"
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
    fun `onButtonReturn - Check return failure Header Empty if CheckNoteHeaderOpen return false`() =
        runTest {
            whenever(
                checkHasNoteHeaderOpen()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.onButtonReturn -> CheckNoteHeaderOpen -> Header empty!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.HEADER_EMPTY
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `onButtonReturn - Check return access true and FlowMenu FINISH if CheckNoteHeaderOpen return true`() =
        runTest {
            whenever(
                checkHasNoteHeaderOpen()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flowNote,
                FlowNote.FINISH
            )
        }
}