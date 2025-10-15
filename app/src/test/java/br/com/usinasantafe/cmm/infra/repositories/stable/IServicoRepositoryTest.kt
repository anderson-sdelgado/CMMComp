package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Servico
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ServicoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ServicoRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServicoRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ServicoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IServicoRepositoryTest {

    private val servicoRoomDatasource = mock<ServicoRoomDatasource>()
    private val servicoRetrofitDatasource = mock<ServicoRetrofitDatasource>()
    private val repository = IServicoRepository(
        servicoRetrofitDatasource = servicoRetrofitDatasource,
        servicoRoomDatasource = servicoRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ServicoRoomModel(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                )
            )
            val entityList = listOf(
                Servico(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                )
            )
            whenever(
                servicoRoomDatasource.addAll(roomModelList)
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
                "IServicoRepository.addAll -> Unknown Error"
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
                ServicoRoomModel(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                )
            )
            val entityList = listOf(
                Servico(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                )
            )
            whenever(
                servicoRoomDatasource.addAll(roomModelList)
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
                servicoRoomDatasource.deleteAll()
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
                "IServicoRepository.deleteAll -> Unknown Error"
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
                servicoRoomDatasource.deleteAll()
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
                servicoRetrofitDatasource.listAll("token")
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
                "IServicoRepository.recoverAll -> Unknown Error"
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
                ServicoRetrofitModel(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                ),
                ServicoRetrofitModel(
                    idServico = 2,
                    codServico = 102,
                    descrServico = "REPARO MOTOR"
                )
            )
            val entityList = listOf(
                Servico(
                    idServico = 1,
                    codServico = 101,
                    descrServico = "TROCA DE OLEO"
                ),
                Servico(
                    idServico = 2,
                    codServico = 102,
                    descrServico = "REPARO MOTOR"
                )
            )
            whenever(
                servicoRetrofitDatasource.listAll("token")
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
