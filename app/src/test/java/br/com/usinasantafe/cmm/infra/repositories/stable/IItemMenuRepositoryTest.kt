package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.lib.WORK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IItemMenuRepositoryTest {

    private val itemMenuRoomDatasource = mock<ItemMenuRoomDatasource>()
    private val itemMenuRetrofitDatasource = mock<ItemMenuRetrofitDatasource>()
    private val repository = IItemMenuRepository(
        itemMenuRetrofitDatasource = itemMenuRetrofitDatasource,
        itemMenuRoomDatasource = itemMenuRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "ITEM 1",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                )
            )
            val entityList = listOf(
                ItemMenu(
                    id = 1,
                    descr = "ITEM 1",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                )
            )
            whenever(
                itemMenuRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuRoomDatasource.addAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuRepository.addAll -> IItemMenuRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "ITEM 1",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                )
            )
            val entityList = listOf(
                ItemMenu(
                    id = 1,
                    descr = "ITEM 1",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                )
            )
            whenever(
                itemMenuRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                itemMenuRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuRoomDatasource.deleteAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuRepository.deleteAll -> IItemMenuRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                itemMenuRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                itemMenuRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuRetrofitDatasource.listAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuRepository.listAll -> IItemMenuRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ItemMenuRetrofitModel(
                    id = 1,
                    descr = "ITEM 1",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                ),
                ItemMenuRetrofitModel(
                    id = 2,
                    descr = "ITEM 2",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                )
            )
            val entityList = listOf(
                ItemMenu(
                    id = 1,
                    descr = "ITEM 1",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                ),
                ItemMenu(
                    id = 2,
                    descr = "ITEM 2",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                )
            )
            whenever(
                itemMenuRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `listByTypeList - Check return failure if have error in ItemMenuRoomDatasource listByTypeList - typeList`() =
        runTest {
            whenever(
                itemMenuRoomDatasource.listByTypeList(
                        listOf(
                        1 to ITEM_NORMAL,
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IItemMenuRoomDatasource.listByByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByTypeList(
                listOf(
                    1 to ITEM_NORMAL,
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuRepository.listByTypeList -> IItemMenuRoomDatasource.listByByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByTypeList - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "ITEM 1",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                ),
            )
            val entityList = listOf(
                ItemMenu(
                    id = 1,
                    descr = "ITEM 1",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                ),
            )
            whenever(
                itemMenuRoomDatasource.listByTypeList(
                    listOf(
                        1 to ITEM_NORMAL,
                    )
                )
            ).thenReturn(
                Result.success(roomModelList)
            )
            val result = repository.listByTypeList(
                listOf(
                    1 to ITEM_NORMAL,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `listByApp - Check return failure if have error in ItemMenuDatasource listByTypeList`() =
        runTest {
            whenever(
                itemMenuRoomDatasource.listByTypeList(app = 1 to PMM)
            ).thenReturn(
                resultFailure(
                    "IItemMenuDatasource.listByTypeList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByApp(
                app = 1 to PMM
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuRepository.listByApp -> IItemMenuDatasource.listByTypeList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByApp - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "ITEM 1",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1
                ),
            )
            val entityList = listOf(
                ItemMenu(
                    id = 1,
                    descr = "ITEM 1",
                    type = 1 to ITEM_NORMAL,
                    pos = 1,
                    function = 1 to WORK,
                    app = 1 to PMM
                ),
            )
            whenever(
                itemMenuRoomDatasource.listByTypeList(app = 1 to PMM)
            ).thenReturn(
                Result.success(roomModelList)
            )
            val result = repository.listByApp(
                app = 1 to PMM
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

}