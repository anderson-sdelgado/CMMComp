package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RAtivParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RAtivParadaRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RAtivParadaRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RAtivParadaRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IRAtivParadaRepositoryTest {

    private val rAtivParadaRoomDatasource = mock<RAtivParadaRoomDatasource>()
    private val rAtivParadaRetrofitDatasource = mock<RAtivParadaRetrofitDatasource>()
    private val repository = IRAtivParadaRepository(
        rAtivParadaRetrofitDatasource = rAtivParadaRetrofitDatasource,
        rAtivParadaRoomDatasource = rAtivParadaRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RAtivParadaRoomModel(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                )
            )
            val entityList = listOf(
                RAtivParada(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                )
            )
            whenever(
                rAtivParadaRoomDatasource.addAll(roomModelList)
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
                "IRAtivParadaRepository.addAll -> Unknown Error"
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
                RAtivParadaRoomModel(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                )
            )
            val entityList = listOf(
                RAtivParada(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                )
            )
            whenever(
                rAtivParadaRoomDatasource.addAll(roomModelList)
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
                rAtivParadaRoomDatasource.deleteAll()
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
                "IRAtivParadaRepository.deleteAll -> Unknown Error"
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
                rAtivParadaRoomDatasource.deleteAll()
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
                rAtivParadaRetrofitDatasource.recoverAll("token")
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
                "IRAtivParadaRepository.recoverAll -> Unknown Error"
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
                RAtivParadaRetrofitModel(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                ),
                RAtivParadaRetrofitModel(
                    idRAtivParada = 2,
                    idAtiv = 11,
                    idParada = 21
                )
            )
            val entityList = listOf(
                RAtivParada(
                    idRAtivParada = 1,
                    idAtiv = 10,
                    idParada = 20
                ),
                RAtivParada(
                    idRAtivParada = 2,
                    idAtiv = 11,
                    idParada = 21
                )
            )
            whenever(
                rAtivParadaRetrofitDatasource.recoverAll("token")
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
