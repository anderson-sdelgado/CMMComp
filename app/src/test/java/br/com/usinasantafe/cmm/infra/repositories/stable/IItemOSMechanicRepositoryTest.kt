package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMechanicRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMechanicRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelInput
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IItemOSMechanicRepositoryTest {

    private val itemOSMechanicRoomDatasource = mock<ItemOSMechanicRoomDatasource>()
    private val itemOSMechanicRetrofitDatasource = mock<ItemOSMechanicRetrofitDatasource>()
    private val repository = IItemOSMechanicRepository(
        itemOSMechanicRetrofitDatasource = itemOSMechanicRetrofitDatasource,
        itemOSMechanicRoomDatasource = itemOSMechanicRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ItemOSMechanicRoomModel(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                )
            )
            val entityList = listOf(
                ItemOSMechanic(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                )
            )
            whenever(
                itemOSMechanicRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRoomDatasource.addAll",
                    "-",
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
                "IItemOSMechanicRepository.addAll -> IItemOSMechanicRoomDatasource.addAll"
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
                ItemOSMechanicRoomModel(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                )
            )
            val entityList = listOf(
                ItemOSMechanic(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                )
            )
            val result = repository.addAll(entityList)
            verify(itemOSMechanicRoomDatasource, atLeastOnce()).addAll(roomModelList)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                itemOSMechanicRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRoomDatasource.deleteAll",
                    "-",
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
                "IItemOSMechanicRepository.deleteAll -> IItemOSMechanicRoomDatasource.deleteAll"
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
                itemOSMechanicRoomDatasource.deleteAll()
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
    fun `listByIdEquipAndNroOS - Check return failure if have error`() =
        runTest {
            whenever(
                itemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS("token", 1, 1)
            ).thenReturn(
                resultFailure(
                    "IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdEquipAndNroOS("token", 1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemOSMechanicRepository.listByIdEquipAndNroOS -> IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdEquipAndNroOS - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ItemOSMechanicRetrofitModelInput(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                ),
                ItemOSMechanicRetrofitModelInput(
                    id = 2,
                    nroOS = 2,
                    seqItem = 2,
                    idServ = 2,
                    idComp = 2
                )
            )
            val entityList = listOf(
                ItemOSMechanic(
                    id = 1,
                    nroOS = 1,
                    seqItem = 1,
                    idServ = 1,
                    idComp = 1
                ),
                ItemOSMechanic(
                    id = 2,
                    nroOS = 2,
                    seqItem = 2,
                    idServ = 2,
                    idComp = 2
                )
            )
            whenever(
                itemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS("token", 1, 1)
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listByIdEquipAndNroOS("token", 1, 1)
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