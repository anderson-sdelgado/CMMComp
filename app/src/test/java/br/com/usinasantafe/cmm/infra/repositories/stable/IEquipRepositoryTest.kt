package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IEquipRepositoryTest {

    private val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
    private val equipRoomDatasource = mock<EquipRoomDatasource>()
    private val repository = IEquipRepository(
        equipRetrofitDatasource = equipRetrofitDatasource,
        equipRoomDatasource = equipRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            val entityList = listOf(
                Equip(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            whenever(
                equipRoomDatasource.addAll(roomModelList)
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
                "IEquipRepository.addAll -> Unknown Error"
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
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            val entityList = listOf(
                Equip(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            whenever(
                equipRoomDatasource.addAll(roomModelList)
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
                equipRoomDatasource.deleteAll()
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
                "IEquipRepository.deleteAll -> Unknown Error"
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
                equipRoomDatasource.deleteAll()
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
                equipRetrofitDatasource.recoverAll(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll(
                token = "token",
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.recoverAll -> Unknown Error"
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
                EquipRetrofitModel(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = 1,
                    flagApontPneu = 1,
                )
            )
            val entityList = listOf(
                Equip(
                    idEquip = 1,
                    nroEquip = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourmeter = 10000.0,
                    measurement = 10.0,
                    type = 1,
                    classify = 1,
                    flagApontMecan = true,
                    flagApontPneu = true,
                )
            )
            whenever(
                equipRetrofitDatasource.recoverAll(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll(
                token = "token",
                idEquip = 10
            )
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
    fun `getDescrByIdEquip - Check return failure if have error in EquipRoomDatasource getByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getDescrByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getDescrByIdEquip -> EquipRoomDatasource.getByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getByIdEquip(1)
            ).thenReturn(
                Result.success(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 200L,
                        codClass = 100,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 10000.0,
                        measurement = 1000.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true,
                    )
                )
            )
            val result = repository.getDescrByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "200 - TRATOR"
            )
        }

    @Test
    fun `getCodTurnEquipByIdEquip - Check return failure if have error in EquipRoomDatasource getByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getCodTurnEquipByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getCodTurnEquipByIdEquip -> EquipRoomDatasource.getByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getCodTurnEquipByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getByIdEquip(1)
            ).thenReturn(
                Result.success(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 200L,
                        codClass = 100,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 10000.0,
                        measurement = 10.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true,
                    )
                )
            )
            val result = repository.getCodTurnEquipByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

}