package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipPneuRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipPneuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipPneuRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipPneuRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IREquipPneuRepositoryTest {

    private val rEquipPneuRoomDatasource = mock<REquipPneuRoomDatasource>()
    private val rEquipPneuRetrofitDatasource = mock<REquipPneuRetrofitDatasource>()
    private val repository = IREquipPneuRepository(
        rEquipPneuRetrofitDatasource = rEquipPneuRetrofitDatasource,
        rEquipPneuRoomDatasource = rEquipPneuRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                REquipPneuRoomModel(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                )
            )
            val entityList = listOf(
                REquipPneu(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                )
            )
            whenever(
                rEquipPneuRoomDatasource.addAll(roomModelList)
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
                "IREquipPneuRepository.addAll -> Unknown Error"
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
                REquipPneuRoomModel(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                )
            )
            val entityList = listOf(
                REquipPneu(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                )
            )
            whenever(
                rEquipPneuRoomDatasource.addAll(roomModelList)
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
                rEquipPneuRoomDatasource.deleteAll()
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
                "IREquipPneuRepository.deleteAll -> Unknown Error"
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
                rEquipPneuRoomDatasource.deleteAll()
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
                rEquipPneuRetrofitDatasource.recoverAll("token")
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
                "IREquipPneuRepository.recoverAll -> Unknown Error"
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
                REquipPneuRetrofitModel(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                ),
                REquipPneuRetrofitModel(
                    idREquipPneu = 2,
                    idEquip = 10,
                    idPosConfPneu = 2,
                    posPneu = 2,
                    statusPneu = 1
                )
            )
            val entityList = listOf(
                REquipPneu(
                    idREquipPneu = 1,
                    idEquip = 10,
                    idPosConfPneu = 1,
                    posPneu = 1,
                    statusPneu = 1
                ),
                REquipPneu(
                    idREquipPneu = 2,
                    idEquip = 10,
                    idPosConfPneu = 2,
                    posPneu = 2,
                    statusPneu = 1
                )
            )
            whenever(
                rEquipPneuRetrofitDatasource.recoverAll("token")
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
