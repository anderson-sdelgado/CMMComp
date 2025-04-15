package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Bocal
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.BocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.BocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.BocalRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.BocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IBocalRepositoryTest {

    private val bocalRoomDatasource = mock<BocalRoomDatasource>()
    private val bocalRetrofitDatasource = mock<BocalRetrofitDatasource>()
    private val repository = IBocalRepository(
        bocalRetrofitDatasource = bocalRetrofitDatasource,
        bocalRoomDatasource = bocalRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                BocalRoomModel(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                )
            )
            val entityList = listOf(
                Bocal(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                )
            )
            whenever(
                bocalRoomDatasource.addAll(roomModelList)
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
                "IBocalRepository.addAll -> Unknown Error"
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
                BocalRoomModel(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                )
            )
            val entityList = listOf(
                Bocal(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                )
            )
            whenever(
                bocalRoomDatasource.addAll(roomModelList)
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
                bocalRoomDatasource.deleteAll()
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
                "IBocalRepository.deleteAll -> Unknown Error"
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
                bocalRoomDatasource.deleteAll()
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
                bocalRetrofitDatasource.recoverAll("token")
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
                "IBocalRepository.recoverAll -> Unknown Error"
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
                BocalRetrofitModel(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                ),
                BocalRetrofitModel(
                    idBocal = 2,
                    codBocal = 456,
                    descrBocal = "BOCAL 456"
                )
            )
            val entityList = listOf(
                Bocal(
                    idBocal = 1,
                    codBocal = 123,
                    descrBocal = "BOCAL 123"
                ),
                Bocal(
                    idBocal = 2,
                    codBocal = 456,
                    descrBocal = "BOCAL 456"
                )
            )
            whenever(
                bocalRetrofitDatasource.recoverAll("token")
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
