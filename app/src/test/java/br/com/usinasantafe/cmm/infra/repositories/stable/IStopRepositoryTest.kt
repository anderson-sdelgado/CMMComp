package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IStopRepositoryTest {

    private val stopRoomDatasource = mock<StopRoomDatasource>()
    private val stopRetrofitDatasource = mock<StopRetrofitDatasource>()
    private val repository = IStopRepository(
        stopRetrofitDatasource = stopRetrofitDatasource,
        stopRoomDatasource = stopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                stopRoomDatasource.addAll(roomModelList)
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
                "IParadaRepository.addAll -> Unknown Error"
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
                StopRoomModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                stopRoomDatasource.addAll(roomModelList)
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
                stopRoomDatasource.deleteAll()
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
                "IParadaRepository.deleteAll -> Unknown Error"
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
                stopRoomDatasource.deleteAll()
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
                stopRetrofitDatasource.recoverAll("token")
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
                "IParadaRepository.recoverAll -> Unknown Error"
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
                StopRetrofitModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                ),
                StopRetrofitModel(
                    idStop = 2,
                    codStop = 102,
                    descrStop = "ABASTECIMENTO"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                ),
                Stop(
                    idStop = 2,
                    codStop = 102,
                    descrStop = "ABASTECIMENTO"
                )
            )
            whenever(
                stopRetrofitDatasource.recoverAll("token")
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
