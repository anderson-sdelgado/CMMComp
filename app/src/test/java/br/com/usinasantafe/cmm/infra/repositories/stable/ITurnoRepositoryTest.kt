package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turno
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnoRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnoRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ITurnoRepositoryTest {

    private val turnoRoomDatasource = mock<TurnoRoomDatasource>()
    private val turnoRetrofitDatasource = mock<TurnoRetrofitDatasource>()
    private val repository = ITurnoRepository(
        turnoRetrofitDatasource = turnoRetrofitDatasource,
        turnoRoomDatasource = turnoRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                TurnoRoomModel(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                )
            )
            val entityList = listOf(
                Turno(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                )
            )
            whenever(
                turnoRoomDatasource.addAll(roomModelList)
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
                "ITurnoRepository.addAll -> Unknown Error"
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
                TurnoRoomModel(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                )
            )
            val entityList = listOf(
                Turno(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                )
            )
            whenever(
                turnoRoomDatasource.addAll(roomModelList)
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
                turnoRoomDatasource.deleteAll()
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
                "ITurnoRepository.deleteAll -> Unknown Error"
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
                turnoRoomDatasource.deleteAll()
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
                turnoRetrofitDatasource.recoverAll("token")
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
                "ITurnoRepository.recoverAll -> Unknown Error"
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
                TurnoRetrofitModel(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                ),
                TurnoRetrofitModel(
                    idTurno = 2,
                    codTurno = 102,
                    nroTurno = 2,
                    descrTurno = "TURNO 2"
                )
            )
            val entityList = listOf(
                Turno(
                    idTurno = 1,
                    codTurno = 101,
                    nroTurno = 1,
                    descrTurno = "TURNO 1"
                ),
                Turno(
                    idTurno = 2,
                    codTurno = 102,
                    nroTurno = 2,
                    descrTurno = "TURNO 2"
                )
            )
            whenever(
                turnoRetrofitDatasource.recoverAll("token")
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
