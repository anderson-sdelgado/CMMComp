package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IItemCheckListRepositoryTest {

    private val itemCheckListRoomDatasource = mock<ItemCheckListRoomDatasource>()
    private val itemCheckListRetrofitDatasource = mock<ItemCheckListRetrofitDatasource>()
    private val repository = IItemCheckListRepository(
        itemCheckListRetrofitDatasource = itemCheckListRetrofitDatasource,
        itemCheckListRoomDatasource = itemCheckListRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ItemCheckListRoomModel(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                )
            )
            val entityList = listOf(
                ItemCheckList(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                )
            )
            whenever(
                itemCheckListRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.addAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ItemCheckListRoomModel(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                )
            )
            val entityList = listOf(
                ItemCheckList(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                )
            )
            whenever(
                itemCheckListRoomDatasource.addAll(roomModelList)
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
                itemCheckListRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.deleteAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                itemCheckListRoomDatasource.deleteAll()
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
                itemCheckListRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.recoverAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ItemCheckListRetrofitModel(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                ),
                ItemCheckListRetrofitModel(
                    idItemCheckList = 2,
                    descrItemCheckList = "VERIFICAR PNEUS",
                    idCheckList = 10
                )
            )
            val entityList = listOf(
                ItemCheckList(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                ),
                ItemCheckList(
                    idItemCheckList = 2,
                    descrItemCheckList = "VERIFICAR PNEUS",
                    idCheckList = 10
                )
            )
            whenever(
                itemCheckListRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll("token")
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
