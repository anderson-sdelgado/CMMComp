package br.com.usinasantafe.cmm.presenter.view.fertigation.reelList

import br.com.usinasantafe.cmm.MainCoroutineRule
import br.com.usinasantafe.cmm.domain.usecases.fertigation.ListHeaderSec
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetHeaderEquipMain
import br.com.usinasantafe.cmm.domain.usecases.fertigation.SetHeaderPointing
import br.com.usinasantafe.cmm.presenter.model.ItemDefaultScreenModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ReelListFertigationViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listHeaderSec = mock<ListHeaderSec>()
    private val setHeaderPointing = mock<SetHeaderPointing>()
    private val setHeaderEquipMain = mock<SetHeaderEquipMain>()
    private val viewModel = ReelListFertigationViewModel(
        listHeaderSec = listHeaderSec,
        setHeaderPointing = setHeaderPointing,
        setHeaderEquipMain = setHeaderEquipMain
    )

    @Test
    fun `list - Check return failure if have error in ListHeaderSec`() =
        runTest {
            whenever(
                listHeaderSec()
            ).thenReturn(
                resultFailure(
                    context = "ListHeaderSec",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReelListFertigationViewModel.list -> ListHeaderSec -> java.lang.Exception"
            )
        }

    @Test
    fun `list - Check return list if ListHeaderSec execute successfully`() =
        runTest {
            whenever(
                listHeaderSec()
            ).thenReturn(
                Result.success(
                    listOf(
                        ItemDefaultScreenModel(
                            id = 1,
                            descr = "2200"
                        )
                    )
                )
            )
            viewModel.list()
            assertEquals(
                viewModel.uiState.value.list,
                listOf(
                    ItemDefaultScreenModel(
                        id = 1,
                        descr = "2200"
                    )
                )
            )
        }

    @Test
    fun `set - Check return failure if have error in SetHeaderPointing`() =
        runTest {
            whenever(
                setHeaderPointing(1)
            ).thenReturn(
                resultFailure(
                    context = "SetHeaderPointing",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.set(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReelListFertigationViewModel.set -> SetHeaderPointing -> java.lang.Exception"
            )
        }

    @Test
    fun `set - Check return true if SetHeaderPointing execute successfully`() =
        runTest {
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            viewModel.set(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `ret - Check return failure if have error in SetHeaderEquipMain`() =
        runTest {
            whenever(
                setHeaderEquipMain()
            ).thenReturn(
                resultFailure(
                    context = "SetHeaderEquipMain",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ReelListFertigationViewModel.ret -> SetHeaderEquipMain -> java.lang.Exception"
            )
        }

    @Test
    fun `ret - Check return true if SetHeaderEquipMain execute successfully`() =
        runTest {
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            verify(setHeaderEquipMain, atLeastOnce()).invoke()
        }
    
}