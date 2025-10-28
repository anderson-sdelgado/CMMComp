package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenuPMM
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuPMMRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuPMMRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuPMMRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IItemMenuPMMRepositoryTest {

    private val itemMenuPMMRoomDatasource = mock<ItemMenuPMMRoomDatasource>()
    private val itemMenuPMMRetrofitDatasource = mock<ItemMenuPMMRetrofitDatasource>()
    private val repository = IItemMenuPMMRepository(
        itemMenuPMMRetrofitDatasource = itemMenuPMMRetrofitDatasource,
        itemMenuPMMRoomDatasource = itemMenuPMMRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ItemMenuPMMRoomModel(
                    id = 1,
                    title = "ITEM 1",
                    type = TypeItemMenu.ITEM_NORMAL,
                )
            )
            val entityList = listOf(
                ItemMenuPMM(
                    id = 1,
                    title = "ITEM 1",
                    type = TypeItemMenu.ITEM_NORMAL,
                )
            )
            whenever(
                itemMenuPMMRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuPMMRoomDatasource.addAll",
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
                "IItemMenuPMMRepository.addAll -> IItemMenuPMMRoomDatasource.addAll"
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
                ItemMenuPMMRoomModel(
                    id = 1,
                    title = "ITEM 1",
                    type = TypeItemMenu.ITEM_NORMAL,
                )
            )
            val entityList = listOf(
                ItemMenuPMM(
                    id = 1,
                    title = "ITEM 1",
                    type = TypeItemMenu.ITEM_NORMAL,
                )
            )
            whenever(
                itemMenuPMMRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                itemMenuPMMRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuPMMRoomDatasource.deleteAll",
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
                "IItemMenuPMMRepository.deleteAll -> IItemMenuPMMRoomDatasource.deleteAll"
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
                itemMenuPMMRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `recoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                itemMenuPMMRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IItemMenuPMMRetrofitDatasource.listAll",
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
                "IItemMenuPMMRepository.listAll -> IItemMenuPMMRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ItemMenuPMMRetrofitModel(
                    id = 1,
                    title = "ITEM 1",
                    type = 1,
                ),
                ItemMenuPMMRetrofitModel(
                    id = 2,
                    title = "ITEM 2",
                    type = 2,
                )
            )
            val entityList = listOf(
                ItemMenuPMM(
                    id = 1,
                    title = "ITEM 1",
                    type = TypeItemMenu.ITEM_NORMAL,
                ),
                ItemMenuPMM(
                    id = 2,
                    title = "ITEM 2",
                    type = TypeItemMenu.BUTTON_FINISH_HEADER,
                )
            )
            whenever(
                itemMenuPMMRetrofitDatasource.listAll("token")
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

}