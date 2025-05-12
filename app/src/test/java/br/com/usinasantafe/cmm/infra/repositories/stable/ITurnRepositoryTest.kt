package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ITurnRepositoryTest {

    private val turnRoomDatasource = mock<TurnRoomDatasource>()
    private val turnoRetrofitDatasource = mock<TurnoRetrofitDatasource>()
    private val repository = ITurnRepository(
        turnoRetrofitDatasource = turnoRetrofitDatasource,
        turnRoomDatasource = turnRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
            val entityList = listOf(
                Turn(
                    idTurn = 1,
                    codTurnEquip = 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
            whenever(
                turnRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "ITurnRoomDatasource.addAll",
                    "-",
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
                "ITurnRepository.addAll -> ITurnRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
            val entityList = listOf(
                Turn(
                    idTurn = 1,
                    codTurnEquip = 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
            whenever(
                turnRoomDatasource.addAll(roomModelList)
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
                turnRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "ITurnRoomDatasource.deleteAll",
                    "-",
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
                "ITurnRepository.deleteAll -> ITurnRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                turnRoomDatasource.deleteAll()
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
                resultFailure(
                    context = "ITurnRetrofitDatasource.recoverAll",
                    "-",
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
                "ITurnRepository.recoverAll -> ITurnRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                TurnRetrofitModel(
                    idTurn = 1,
                    codTurnEquip = 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                ),
                TurnRetrofitModel(
                    idTurn = 2,
                    codTurnEquip = 102,
                    nroTurn = 2,
                    descrTurn = "TURNO 2"
                )
            )
            val entityList = listOf(
                Turn(
                    idTurn = 1,
                    codTurnEquip= 101,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                ),
                Turn(
                    idTurn = 2,
                    codTurnEquip = 102,
                    nroTurn = 2,
                    descrTurn = "TURNO 2"
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

    @Test
    fun `getListByCodTurnEquip - Check return failure if have error in TurnRoomDatasource getListByCodTurnEquip`() =
        runTest {
            whenever(
                turnRoomDatasource.getListByCodTurnEquip(1)
            ).thenReturn(
                resultFailure(
                    context = "ITurnRoomDatasource.getListByCodTurnEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getListByCodTurnEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ITurnRepository.getListByCodTurnEquip -> ITurnRoomDatasource.getListByCodTurnEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getListByCodTurnEquip - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 1,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
            whenever(
                turnRoomDatasource.getListByCodTurnEquip(1)
            ).thenReturn(
                Result.success(
                    roomModelList
                )
            )
            val result = repository.getListByCodTurnEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size
                ,1
            )
            val entity = list[0]
            assertEquals(
                entity.idTurn,
                1
            )
            assertEquals(
                entity.codTurnEquip,
                1
            )
            assertEquals(
                entity.nroTurn,
                1
            )
            assertEquals(
                entity.descrTurn,
                "TURNO 1"
            )
        }

}
