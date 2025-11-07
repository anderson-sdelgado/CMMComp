package br.com.usinasantafe.cmm.presenter.view.note.menu

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.common.GetDescrEquip
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckHasNoteMotoMec
import br.com.usinasantafe.cmm.domain.usecases.motomec.ListItemMenu
import br.com.usinasantafe.cmm.domain.usecases.mechanic.CheckHasNoteOpenMechanic
import br.com.usinasantafe.cmm.domain.usecases.motomec.CheckTypeHeaderMotoMec
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.STOP
import br.com.usinasantafe.cmm.utils.WORK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MenuNoteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listItemMenu = mock<ListItemMenu>()
    private val getDescrEquip = mock<GetDescrEquip>()
    private val checkHasNoteMotoMec = mock<CheckHasNoteMotoMec>()
    private val checkHasNoteOpenMechanic = mock<CheckHasNoteOpenMechanic>()
    private val checkTypeHeaderMotoMec = mock<CheckTypeHeaderMotoMec>()
    private val viewModel = MenuNoteViewModel(
        listItemMenu = listItemMenu,
        getDescrEquip = getDescrEquip,
        checkHasNoteMotoMec = checkHasNoteMotoMec,
        checkHasNoteOpenMechanic = checkHasNoteOpenMechanic,
        checkTypeHeaderMotoMec = checkTypeHeaderMotoMec
    )

    @Test
    fun `menuList - Check return failure if have error in GetItemMenuList`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                resultFailure(
                    context = "GetItemMenuList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.menuList -> GetItemMenuList -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `menuList - Check return failure if return is empty list`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(emptyList())
            )
            viewModel.menuList("pmm")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `menuList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            function = 1 to WORK
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            function = 1 to STOP
                        ),
                    )
                )
            )
            viewModel.menuList("pmm")
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
                item1.descr,
                "TRABALHANDO"
            )
            assertEquals(
                item1.function.first,
                1
            )
            assertEquals(
                item1.function.second,
                WORK
            )
            val item2 = menuList[1]
            assertEquals(
                item2.id,
                2
            )
            assertEquals(
                item2.descr,
                "PARADO"
            )
            assertEquals(
                item2.function.first,
                1
            )
            assertEquals(
                item2.function.second,
                STOP
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
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
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
                "MenuNoteViewModel.setSelection -> Item with id=0 not found in menuList"
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
    fun `setSelection - Check return failure if function is invalid`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            function = 50 to WORK
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            function = 1 to STOP
                        ),
                    )
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(0)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> Item with id = 0 not found in menuList"
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
    fun `setSelection - Check return failure if have error in CheckHasNoteOpenMechanic`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            function = 1 to WORK
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            function = 2 to STOP
                        ),
                    )
                )
            )
            whenever(
                checkHasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> CheckHasNoteOpenMechanic -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setSelection - Check return failure if have CheckHasNoteOpenMechanic return true`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            function = 1 to WORK
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            function = 2 to STOP
                        ),
                    )
                )
            )
            whenever(
                checkHasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            whenever(
                checkHasNoteOpenMechanic()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "MenuNoteViewModel.setSelection -> checkHasNoteOpenMechanic -> Note mechanic open!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.NOTE_MECHANICAL_OPEN
            )
        }

    @Test
    fun `setSelection - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                listItemMenu("pmm")
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemMenuModel(
                            id = 1,
                            descr = "TRABALHANDO",
                            function = 1 to WORK
                        ),
                        ItemMenuModel(
                            id = 2,
                            descr = "PARADO",
                            function = 2 to STOP
                        ),
                    )
                )
            )
            whenever(
                checkHasNoteOpenMechanic()
            ).thenReturn(
                resultFailure(
                    context = "CheckHasNoteOpenMechanic",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.menuList("pmm")
            whenever(
                checkHasNoteOpenMechanic()
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setSelection(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `onButtonReturn - Check return failure if have error in CheckNoteHeaderOpen`() =
        runTest {
            whenever(
                checkHasNoteMotoMec()
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
                checkHasNoteMotoMec()
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
                checkHasNoteMotoMec()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onButtonReturn()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }
}