package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.TurnRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
                turnoRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "ITurnRetrofitDatasource.recoverAll",
                    "-",
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
                turnoRetrofitDatasource.listAll("token")
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

    @Test
    fun `listByCodTurnEquip - Check return failure if have error in TurnRoomDatasource listByCodTurnEquip`() =
        runTest {
            whenever(
                turnRoomDatasource.listByCodTurnEquip(1)
            ).thenReturn(
                resultFailure(
                    context = "ITurnRoomDatasource.listByCodTurnEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByCodTurnEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ITurnRepository.listByCodTurnEquip -> ITurnRoomDatasource.listByCodTurnEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByCodTurnEquip - Check return true if function execute successfully`() =
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
                turnRoomDatasource.listByCodTurnEquip(1)
            ).thenReturn(
                Result.success(
                    roomModelList
                )
            )
            val result = repository.listByCodTurnEquip(1)
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

    @Test
    fun `getNroTurnByIdTurn - Check return failure if have error in TurnRoomDatasource getNroTurnByIdTurn`() =
        runTest {
            whenever(
                turnRoomDatasource.getNroTurnByIdTurn(1)
            ).thenReturn(
                resultFailure(
                    "ITurnRoomDatasource.getNroTurnByIdTurn",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNroTurnByIdTurn(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ITurnRepository.getNroTurnByIdTurn -> ITurnRoomDatasource.getNroTurnByIdTurn"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroTurnByIdTurn - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                turnRoomDatasource.getNroTurnByIdTurn(1)
            ).thenReturn(
                Result.success(2)
            )
            val result = repository.getNroTurnByIdTurn(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2
            )
        }

}
