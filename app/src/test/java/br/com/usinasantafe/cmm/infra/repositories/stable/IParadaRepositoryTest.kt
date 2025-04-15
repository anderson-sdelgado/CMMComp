package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Parada
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ParadaRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ParadaRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ParadaRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IParadaRepositoryTest {

    private val paradaRoomDatasource = mock<ParadaRoomDatasource>()
    private val paradaRetrofitDatasource = mock<ParadaRetrofitDatasource>()
    private val repository = IParadaRepository(
        paradaRetrofitDatasource = paradaRetrofitDatasource,
        paradaRoomDatasource = paradaRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ParadaRoomModel(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Parada(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                paradaRoomDatasource.addAll(roomModelList)
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
                ParadaRoomModel(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Parada(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                paradaRoomDatasource.addAll(roomModelList)
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
                paradaRoomDatasource.deleteAll()
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
                paradaRoomDatasource.deleteAll()
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
                paradaRetrofitDatasource.recoverAll("token")
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
                ParadaRetrofitModel(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                ),
                ParadaRetrofitModel(
                    idParada = 2,
                    codParada = 102,
                    descrParada = "ABASTECIMENTO"
                )
            )
            val entityList = listOf(
                Parada(
                    idParada = 1,
                    codParada = 101,
                    descrParada = "MANUTENCAO MECANICA"
                ),
                Parada(
                    idParada = 2,
                    codParada = 102,
                    descrParada = "ABASTECIMENTO"
                )
            )
            whenever(
                paradaRetrofitDatasource.recoverAll("token")
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
