package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.cmm.domain.usecases.common.GetToken
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableItemCheckListByNroEquipTest {

    private val getToken = mock<GetToken>()
    private val itemCheckListRepository = mock<ItemCheckListRepository>()
    private val configRepository = mock<ConfigRepository>()
    private val updateTableItemCheckList = IUpdateTableItemCheckListByNroEquip(
        getToken = getToken,
        itemCheckListRepository = itemCheckListRepository,
        configRepository = configRepository
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
            val result = updateTableItemCheckList(
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemCheckListByNroEquip -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository getNroEquip`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.getNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemCheckList(
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemCheckListByNroEquip -> IConfigRepository.getNroEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository listByNroEquip`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemCheckListRepository.listByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.listByNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemCheckList(
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.listByNroEquip -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository deleteAll`() =
        runTest {
            val list = listOf(
                ItemCheckList(
                    idCheckList = 1,
                    idItemCheckList = 1,
                    descrItemCheckList = "descrItemCheckList"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemCheckListRepository.listByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                itemCheckListRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemCheckList(
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ItemCheckListRepository addAll`() =
        runTest {
            val list = listOf(
                ItemCheckList(
                    idCheckList = 1,
                    idItemCheckList = 1,
                    descrItemCheckList = "descrItemCheckList"
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                configRepository.getNroEquip()
            ).thenReturn(
                Result.success(1L)
            )
            whenever(
                itemCheckListRepository.listByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                Result.success(
                    list
                )
            )
            whenever(
                itemCheckListRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                itemCheckListRepository.addAll(list)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = updateTableItemCheckList(
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                resultList[3],
                ResultUpdateModel(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

}