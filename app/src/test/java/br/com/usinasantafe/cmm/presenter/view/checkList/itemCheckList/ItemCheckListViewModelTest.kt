package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.usecases.checkList.DelLastRespItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.GetItemCheckList
import br.com.usinasantafe.cmm.domain.usecases.checkList.SetRespItemCheckList
import br.com.usinasantafe.cmm.presenter.model.ItemCheckListModel
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ItemCheckListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getItemCheckList = mock<GetItemCheckList>()
    private val setRespItemCheckList = mock<SetRespItemCheckList>()
    private val delLastRespItemCheckList = mock<DelLastRespItemCheckList>()
    private val viewModel = ItemCheckListViewModel(
        getItemCheckList = getItemCheckList,
        setRespItemCheckList = setRespItemCheckList,
        delLastRespItemCheckList = delLastRespItemCheckList
    )

    @Test
    fun `get - Check return failure if have error in GetItemCheckList and pos is initial`() =
        runTest { //
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                resultFailure(
                    context = "GetItemCheckList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ItemCheckListViewModel.get -> GetItemCheckList -> java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return failure if have error in GetItemCheckList and pos is 3`() =
        runTest { //
            whenever(
                getItemCheckList(3)
            ).thenReturn(
                resultFailure(
                    context = "GetItemCheckList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.get(3)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ItemCheckListViewModel.get -> GetItemCheckList -> java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return true if GetItemCheckList execute successfully and pos is initial`() =
        runTest {
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 1,
                        descr = "test",
                    )
                )
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.id,
                1
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test"
            )
        }

    @Test
    fun `get - Check return true if GetItemCheckList execute successfully and pos is 3`() =
        runTest {
            whenever(
                getItemCheckList(3)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 3,
                        descr = "test 3",
                    )
                )
            )
            viewModel.get(3)
            assertEquals(
                viewModel.uiState.value.pos,
                3
            )
            assertEquals(
                viewModel.uiState.value.id,
                3
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 3"
            )
        }

    @Test
    fun `ret - Check pos if pos is initial`() =
        runTest {
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 1,
                        descr = "test 1",
                    )
                )
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.id,
                1
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 1"
            )
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
        }

    @Test
    fun `ret - Check return failure if have error in DelLastRespItemCheckList and pos is 3 `() =
        runTest {
            whenever(
                getItemCheckList(3)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 3,
                        descr = "test 3",
                    )
                )
            )
            whenever(
                delLastRespItemCheckList()
            ).thenReturn(
                resultFailure(
                    context = "DelLastRespItemCheckList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.get(3)
            assertEquals(
                viewModel.uiState.value.pos,
                3
            )
            assertEquals(
                viewModel.uiState.value.id,
                3
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 3"
            )
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ItemCheckListViewModel.ret -> DelLastRespItemCheckList -> java.lang.Exception"
            )
        }


    @Test
    fun `ret - Check return correct if DelLastRespItemCheckList execute successfully and pos is 3 `() =
        runTest {
            whenever(
                getItemCheckList(2)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 2,
                        descr = "test 2",
                    )
                )
            )
            whenever(
                getItemCheckList(3)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 3,
                        descr = "test 3",
                    )
                )
            )
            whenever(
                delLastRespItemCheckList()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.get(3)
            assertEquals(
                viewModel.uiState.value.pos,
                3
            )
            assertEquals(
                viewModel.uiState.value.id,
                3
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 3"
            )
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.pos,
                2
            )
            assertEquals(
                viewModel.uiState.value.id,
                2
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 2"
            )
        }

    @Test
    fun `set - Check return failure if have error in SetRespItemCheckList`() =
        runTest { //
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 1,
                        descr = "test 1",
                    )
                )
            )
            whenever(
                setRespItemCheckList(
                    pos = 1,
                    id = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            ).thenReturn(
                resultFailure(
                    context = "SetRespItemCheckList",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.id,
                1
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 1"
            )
            viewModel.set(OptionRespCheckList.ACCORDING)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ItemCheckListViewModel.set -> SetRespItemCheckList -> java.lang.Exception"
            )
        }

    @Test
    fun `set - Check set next if SetRespItemCheckList execute successfully and return is true`() =
        runTest {
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 1,
                        descr = "test 1",
                    )
                )
            )
            whenever(
                getItemCheckList(2)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 2,
                        descr = "test 2",
                    )
                )
            )
            whenever(
                setRespItemCheckList(
                    pos = 1,
                    id = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.id,
                1
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 1"
            )
            viewModel.set(OptionRespCheckList.ACCORDING)
            assertEquals(
                viewModel.uiState.value.pos,
                2
            )
            assertEquals(
                viewModel.uiState.value.id,
                2
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 2"
            )
        }

    @Test
    fun `set - Check finish if SetRespItemCheckList execute successfully and return is false`() =
        runTest {
            whenever(
                getItemCheckList(1)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 1,
                        descr = "test 1",
                    )
                )
            )
            whenever(
                getItemCheckList(2)
            ).thenReturn(
                Result.success(
                    ItemCheckListModel(
                        id = 2,
                        descr = "test 2",
                    )
                )
            )
            whenever(
                setRespItemCheckList(
                    pos = 1,
                    id = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            ).thenReturn(
                Result.success(false)
            )
            viewModel.get()
            assertEquals(
                viewModel.uiState.value.pos,
                1
            )
            assertEquals(
                viewModel.uiState.value.id,
                1
            )
            assertEquals(
                viewModel.uiState.value.descr,
                "test 1"
            )
            viewModel.set(OptionRespCheckList.ACCORDING)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }
}