package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.CheckUpdateCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
                resultFailure(
                    "IItemCheckListRoomDatasource.addAll",
                    "-",
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
                "IItemCheckListRepository.addAll -> IItemCheckListRoomDatasource.addAll"
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
                itemCheckListRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRoomDatasource.deleteAll",
                    "-",
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
                "IItemCheckListRepository.deleteAll -> IItemCheckListRoomDatasource.deleteAll"
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
                itemCheckListRoomDatasource.deleteAll()
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
    fun `listByNroEquip - Check return failure if have error`() =
        runTest {
            whenever(
                itemCheckListRetrofitDatasource.listByNroEquip(
                    token = "token",
                    nroEquip = 10L
                )
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRetrofitDatasource.listByNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByNroEquip(
                token = "token",
                nroEquip = 10L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.listByNroEquip -> IItemCheckListRetrofitDatasource.listByNroEquip"
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
                itemCheckListRetrofitDatasource.listByNroEquip(
                    token = "token",
                    nroEquip = 10L
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listByNroEquip(
                token = "token",
                nroEquip = 10L
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
    fun `checkUpdateByNroEquip - Check return failure if have error in ItemCheckListRetrofitDatasource checkUpdateByNroEquip`() =
        runTest {
            whenever(
                itemCheckListRetrofitDatasource.checkUpdateByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRetrofitDatasource.checkUpdateByNroEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkUpdateByNroEquip(
                token = "token",
                nroEquip = 1L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.checkUpdateByNroEquip -> IItemCheckListRetrofitDatasource.checkUpdateByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkUpdateByNroEquip - Check return true if function execute successfully and qtd is greater than zero`() =
        runTest {
            whenever(
                itemCheckListRetrofitDatasource.checkUpdateByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                Result.success(
                    CheckUpdateCheckListRetrofitModel(
                        qtd = 1
                    )
                )
            )
            val result = repository.checkUpdateByNroEquip(
                token = "token",
                nroEquip = 1L
            )
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
    fun `checkUpdateByNroEquip - Check return false if function execute successfully and qtd is zero`() =
        runTest {
            whenever(
                itemCheckListRetrofitDatasource.checkUpdateByNroEquip(
                    token = "token",
                    nroEquip = 1L
                )
            ).thenReturn(
                Result.success(
                    CheckUpdateCheckListRetrofitModel(
                        qtd = 0
                    )
                )
            )
            val result = repository.checkUpdateByNroEquip(
                token = "token",
                nroEquip = 1L
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `listByIdCheckList - Check return failure if have error in ItemCheckListRoomDatasource listByIdCheckList`() =
        runTest {
            whenever(
                itemCheckListRoomDatasource.listByIdCheckList(1)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRoomDatasource.listByIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdCheckList(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.listByIdCheckList -> IItemCheckListRoomDatasource.listByIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdCheckList - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                ItemCheckListRoomModel(
                    idItemCheckList = 1,
                    descrItemCheckList = "VERIFICAR NIVEL OLEO",
                    idCheckList = 10
                ),
                ItemCheckListRoomModel(
                    idItemCheckList = 2,
                    descrItemCheckList = "VERIFICAR PNEUS",
                    idCheckList = 10
                )
            )
            whenever(
                itemCheckListRoomDatasource.listByIdCheckList(10)
            ).thenReturn(
                Result.success(roomModelList)
            )
            val result = repository.listByIdCheckList(10)
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                2
            )
            val entity1 = entityList[0]
            assertEquals(
                entity1.idItemCheckList,
                1
            )
            assertEquals(
                entity1.descrItemCheckList,
                "VERIFICAR NIVEL OLEO"
            )
            assertEquals(
                entity1.idCheckList,
                10
            )
            val entity2 = entityList[1]
            assertEquals(
                entity2.idItemCheckList,
                2
            )
            assertEquals(
                entity2.descrItemCheckList,
                "VERIFICAR PNEUS"
            )
            assertEquals(
                entity2.idCheckList,
                10
            )
        }

    @Test
    fun `countByIdCheckList - Check return failure if have error in ItemCheckListRoomDatasource countByIdCheckList`() =
        runTest {
            whenever(
                itemCheckListRoomDatasource.countByIdCheckList(1)
            ).thenReturn(
                resultFailure(
                    "IItemCheckListRoomDatasource.countByIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = repository.countByIdCheckList(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRepository.countByIdCheckList -> IItemCheckListRoomDatasource.countByIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `countByIdCheckList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                itemCheckListRoomDatasource.countByIdCheckList(1)
            ).thenReturn(
                Result.success(10)
            )
            val result = repository.countByIdCheckList(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                10
            )
        }
}
