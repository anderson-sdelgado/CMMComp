package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuPMMRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IUpdateTableItemMenuPMMTest {

    private val getToken = mock<GetToken>()
    private val itemMenuPMMRepository = mock<ItemMenuPMMRepository>()
    private val updateTableItemMenuPMM = IUpdateTableItemMenuPMM(
        getToken = getToken,
        itemMenuPMMRepository = itemMenuPMMRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemMenuPMM(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemMenuPMM -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuPMMRepository recoverAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemMenuPMMRepository.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IItemMenuPMMRepository.recoverAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemMenuPMM(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemMenuPMM -> IItemMenuPMMRepository.recoverAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuPMMRepository deleteAll`() =
        runTest {
            val list = listOf(
                ItemMenuPMM(
                    id = 1,
                    title = "TRABALHANDO",
                    type = TypeItemMenu.ITEM_NORMAL
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemMenuPMMRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                itemMenuPMMRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemMenuPMMRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemMenuPMM(
                sizeAll = 7f,
                count = 1f
            )
            val resultList = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                resultList[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemMenuPMM -> IItemMenuPMMRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemMenuPMMRepository addAll`() =
        runTest {
            val list = listOf(
                ItemMenuPMM(
                    id = 1,
                    title = "TRABALHANDO",
                    type = TypeItemMenu.ITEM_NORMAL
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                itemMenuPMMRepository.listAll("token")
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                itemMenuPMMRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemMenuPMMRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "IItemMenuPMMRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemMenuPMM(
                sizeAll = 7f,
                count = 1f
            )
            val resultList = result.toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                resultList[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_menu_pmm",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemMenuPMM -> IItemMenuPMMRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}