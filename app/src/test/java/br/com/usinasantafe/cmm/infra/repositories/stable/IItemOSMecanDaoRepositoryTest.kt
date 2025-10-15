package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMecan
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemOSMecanRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMecanRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMecanRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IItemOSMecanDaoRepositoryTest {

    private val itemOSMecanRoomDatasource = mock<ItemOSMecanRoomDatasource>()
    private val itemOSMecanRetrofitDatasource = mock<ItemOSMecanRetrofitDatasource>()
    private val repository = IItemOSMecanRepository(
        itemOSMecanRetrofitDatasource = itemOSMecanRetrofitDatasource,
        itemOSMecanRoomDatasource = itemOSMecanRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ItemOSMecanRoomModel(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                )
            )
            val entityList = listOf(
                ItemOSMecan(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                )
            )
            whenever(
                itemOSMecanRoomDatasource.addAll(roomModelList)
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
                "IItemOSMecanRepository.addAll -> Unknown Error"
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
                ItemOSMecanRoomModel(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                )
            )
            val entityList = listOf(
                ItemOSMecan(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                )
            )
            whenever(
                itemOSMecanRoomDatasource.addAll(roomModelList)
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
                itemOSMecanRoomDatasource.deleteAll()
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
                "IItemOSMecanRepository.deleteAll -> Unknown Error"
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
                itemOSMecanRoomDatasource.deleteAll()
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
                itemOSMecanRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemOSMecanRepository.recoverAll -> Unknown Error"
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
                ItemOSMecanRetrofitModel(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                ),
                ItemOSMecanRetrofitModel(
                    idItemOS = 2,
                    idOS = 100,
                    seqItemOS = 2,
                    idServItemOS = 51,
                    idCompItemOS = 21
                )
            )
            val entityList = listOf(
                ItemOSMecan(
                    idItemOS = 1,
                    idOS = 100,
                    seqItemOS = 1,
                    idServItemOS = 50,
                    idCompItemOS = 20
                ),
                ItemOSMecan(
                    idItemOS = 2,
                    idOS = 100,
                    seqItemOS = 2,
                    idServItemOS = 51,
                    idCompItemOS = 21
                )
            )
            whenever(
                itemOSMecanRetrofitDatasource.listAll("token")
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
