package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IRAtivStopRepositoryTest {

    private val rActivityStopRoomDatasource = mock<RActivityStopRoomDatasource>()
    private val rActivityStopRetrofitDatasource = mock<RActivityStopRetrofitDatasource>()
    private val repository = IRActivityStopRepository(
        rActivityStopRetrofitDatasource = rActivityStopRetrofitDatasource,
        rActivityStopRoomDatasource = rActivityStopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RActivityStopRoomModel(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            whenever(
                rActivityStopRoomDatasource.addAll(roomModelList)
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
                RActivityStopRoomModel(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            whenever(
                rActivityStopRoomDatasource.addAll(roomModelList)
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
                rActivityStopRoomDatasource.deleteAll()
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
                rActivityStopRoomDatasource.deleteAll()
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
                rActivityStopRetrofitDatasource.recoverAll("token")
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
                RActivityStopRetrofitModel(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                ),
                RActivityStopRetrofitModel(
                    idRActivityStop = 2,
                    idActivity = 11,
                    idStop = 21
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                ),
                RActivityStop(
                    idRActivityStop = 2,
                    idActivity = 11,
                    idStop = 21
                )
            )
            whenever(
                rActivityStopRetrofitDatasource.recoverAll("token")
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
