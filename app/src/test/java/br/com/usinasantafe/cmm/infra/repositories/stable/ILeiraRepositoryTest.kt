package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Leira
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.LeiraRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.LeiraRoomModel
import br.com.usinasantafe.cmm.utils.StatusLeira
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ILeiraRepositoryTest {

    private val leiraRoomDatasource = mock<LeiraRoomDatasource>()
    private val leiraRetrofitDatasource = mock<LeiraRetrofitDatasource>()
    private val repository = ILeiraRepository(
        leiraRetrofitDatasource = leiraRetrofitDatasource,
        leiraRoomDatasource = leiraRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                LeiraRoomModel(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA.ordinal
                )
            )
            val entityList = listOf(
                Leira(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA
                )
            )
            whenever(
                leiraRoomDatasource.addAll(roomModelList)
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
                "ILeiraRepository.addAll -> Unknown Error"
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
                LeiraRoomModel(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA.ordinal
                )
            )
            val entityList = listOf(
                Leira(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA
                )
            )
            whenever(
                leiraRoomDatasource.addAll(roomModelList)
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
                leiraRoomDatasource.deleteAll()
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
                "ILeiraRepository.deleteAll -> Unknown Error"
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
                leiraRoomDatasource.deleteAll()
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
                leiraRetrofitDatasource.listAll("token")
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
                "ILeiraRepository.recoverAll -> Unknown Error"
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
                LeiraRetrofitModel(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA.ordinal
                ),
                LeiraRetrofitModel(
                    idLeira = 2,
                    codLeira = 102,
                    statusLeira = StatusLeira.DESCARGA.ordinal
                )
            )
            val entityList = listOf(
                Leira(
                    idLeira = 1,
                    codLeira = 101,
                    statusLeira = StatusLeira.LIBERADA
                ),
                Leira(
                    idLeira = 2,
                    codLeira = 102,
                    statusLeira = StatusLeira.DESCARGA
                )
            )
            whenever(
                leiraRetrofitDatasource.listAll("token")
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
