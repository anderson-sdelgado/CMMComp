package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Componente
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponenteRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponenteRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponenteRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponenteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IComponenteRepositoryTest {

    private val componenteRoomDatasource = mock<ComponenteRoomDatasource>()
    private val componenteRetrofitDatasource = mock<ComponenteRetrofitDatasource>()
    private val repository = IComponenteRepository(
        componenteRetrofitDatasource = componenteRetrofitDatasource,
        componenteRoomDatasource = componenteRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ComponenteRoomModel(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                )
            )
            val entityList = listOf(
                Componente(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                )
            )
            whenever(
                componenteRoomDatasource.addAll(roomModelList)
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
                "IComponenteRepository.addAll -> Unknown Error"
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
                ComponenteRoomModel(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                )
            )
            val entityList = listOf(
                Componente(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                )
            )
            whenever(
                componenteRoomDatasource.addAll(roomModelList)
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
                componenteRoomDatasource.deleteAll()
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
                "IComponenteRepository.deleteAll -> Unknown Error"
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
                componenteRoomDatasource.deleteAll()
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
                componenteRetrofitDatasource.listAll("token")
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
                "IComponenteRepository.recoverAll -> Unknown Error"
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
                ComponenteRetrofitModel(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                ),
                ComponenteRetrofitModel(
                    idComponente = 2,
                    codComponente = 654,
                    descrComponente = "BOMBA HIDRAULICA"
                )
            )
            val entityList = listOf(
                Componente(
                    idComponente = 1,
                    codComponente = 987,
                    descrComponente = "MOTOR PRINCIPAL"
                ),
                Componente(
                    idComponente = 2,
                    codComponente = 654,
                    descrComponente = "BOMBA HIDRAULICA"
                )
            )
            whenever(
                componenteRetrofitDatasource.listAll("token")
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
