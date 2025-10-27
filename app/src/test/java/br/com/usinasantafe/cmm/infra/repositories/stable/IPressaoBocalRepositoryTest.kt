package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PressaoBocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PressaoBocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.PressaoBocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IPressaoBocalRepositoryTest {

    private val pressaoBocalRoomDatasource = mock<PressaoBocalRoomDatasource>()
    private val pressaoBocalRetrofitDatasource = mock<PressaoBocalRetrofitDatasource>()
    private val repository = IPressaoBocalRepository(
        pressaoBocalRetrofitDatasource = pressaoBocalRetrofitDatasource,
        pressaoBocalRoomDatasource = pressaoBocalRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                PressaoBocalRoomModel(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                )
            )
            val entityList = listOf(
                PressaoBocal(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                )
            )
            whenever(
                pressaoBocalRoomDatasource.addAll(roomModelList)
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
                "IPressaoBocalRepository.addAll -> Unknown Error"
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
                PressaoBocalRoomModel(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                )
            )
            val entityList = listOf(
                PressaoBocal(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                )
            )
            whenever(
                pressaoBocalRoomDatasource.addAll(roomModelList)
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
                pressaoBocalRoomDatasource.deleteAll()
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
                "IPressaoBocalRepository.deleteAll -> Unknown Error"
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
                pressaoBocalRoomDatasource.deleteAll()
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
                pressaoBocalRetrofitDatasource.listAll("token")
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
                "IPressaoBocalRepository.recoverAll -> Unknown Error"
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
                PressaoBocalRetrofitModel(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                ),
                PressaoBocalRetrofitModel(
                    idPressaoBocal = 2,
                    idBocal = 10,
                    valorPressao = 4.0,
                    valorVeloc = 6
                )
            )
            val entityList = listOf(
                PressaoBocal(
                    idPressaoBocal = 1,
                    idBocal = 10,
                    valorPressao = 3.5,
                    valorVeloc = 5
                ),
                PressaoBocal(
                    idPressaoBocal = 2,
                    idBocal = 10,
                    valorPressao = 4.0,
                    valorVeloc = 6
                )
            )
            whenever(
                pressaoBocalRetrofitDatasource.listAll("token")
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
