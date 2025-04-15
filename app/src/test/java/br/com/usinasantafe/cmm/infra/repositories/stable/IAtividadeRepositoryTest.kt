package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Atividade
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.AtividadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.AtividadeRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.AtividadeRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.AtividadeRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IAtividadeRepositoryTest {

    private val atividadeRoomDatasource = mock<AtividadeRoomDatasource>()
    private val atividadeRetrofitDatasource = mock<AtividadeRetrofitDatasource>()
    private val repository = IAtividadeRepository(
        atividadeRetrofitDatasource = atividadeRetrofitDatasource,
        atividadeRoomDatasource = atividadeRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                AtividadeRoomModel(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Atividade(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            whenever(
                atividadeRoomDatasource.addAll(roomModelList)
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
                "IAtividadeRepository.addAll -> Unknown Error"
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
                AtividadeRoomModel(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Atividade(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            whenever(
                atividadeRoomDatasource.addAll(roomModelList)
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
                atividadeRoomDatasource.deleteAll()
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
                "IAtividadeRepository.deleteAll -> Unknown Error"
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
                atividadeRoomDatasource.deleteAll()
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
                atividadeRetrofitDatasource.recoverAll("token")
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
                "IAtividadeRepository.recoverAll -> Unknown Error"
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
                AtividadeRetrofitModel(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Atividade(
                    idAtiv = 1,
                    codAtiv = 10,
                    descrAtiv = "ATIVIDADE"
                )
            )
            whenever(
                atividadeRetrofitDatasource.recoverAll("token")
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