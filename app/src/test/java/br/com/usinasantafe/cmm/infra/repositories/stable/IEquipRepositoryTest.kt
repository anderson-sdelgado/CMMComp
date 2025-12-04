package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            whenever(
                equipRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IEquipRoomDatasource.addAll",
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
                "IEquipRepository.addAll -> IEquipRoomDatasource.addAll"
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
                EquipRoomModel(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
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
                resultFailure(
                    "IEquipRoomDatasource.deleteAll",
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
                "IEquipRepository.deleteAll -> IEquipRoomDatasource.deleteAll"
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
    fun `getListByIdEquip - Check return failure if have error`() =
        runTest {
            whenever(
                equipRetrofitDatasource.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    "IEquipRetrofitDatasource.listByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdEquip(
                token = "token",
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.listByIdEquip -> IEquipRetrofitDatasource.listByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getListByIdEquip - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                EquipRetrofitModel(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = 1,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = 1,
                    flagTire = 1
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 10000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            whenever(
                equipRetrofitDatasource.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listByIdEquip(
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
    fun `getDescrByIdEquip - Check return failure if have error in EquipRoomDatasource getDescrByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getDescrByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getDescrByIdEquip",
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
                "IEquipRepository.getDescrByIdEquip -> EquipRoomDatasource.getDescrByIdEquip"
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
                equipRoomDatasource.getDescrByIdEquip(1)
            ).thenReturn(
                Result.success("200 - TRATOR")
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
    fun `getCodTurnEquipByIdEquip - Check return failure if have error in EquipRoomDatasource getCodTurnEquipByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getCodTurnEquipByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getCodTurnEquipByIdEquip",
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
                "IEquipRepository.getCodTurnEquipByIdEquip -> EquipRoomDatasource.getCodTurnEquipByIdEquip"
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
                equipRoomDatasource.getCodTurnEquipByIdEquip(1)
            ).thenReturn(
                Result.success(1)
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

    @Test
    fun `getMeasureByIdEquip - Check return failure if have error in EquipRoomDatasource getMeasureByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getHourMeterByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getHourMeterByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getHourMeterByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getHourMeterByIdEquip -> EquipRoomDatasource.getHourMeterByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getMeasureByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getHourMeterByIdEquip(1)
            ).thenReturn(
                Result.success(1.0)
            )
            val result = repository.getHourMeterByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1.0,
                0.0
            )
        }

    @Test
    fun `updateMeasureByIdEquip - Check return failure if have error in EquipRoomDatasource updateMeasureByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.updateHourMeterByIdEquip(
                    hourMeter = 1.0,
                    idEquip = 1
                )
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.updateHourMeterByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.updateHourMeterByIdEquip(
                hourMeter = 1.0,
                idEquip = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.updateHourMeterByIdEquip -> EquipRoomDatasource.updateHourMeterByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getTypeEquipByIdEquip - Check return failure if have error in EquipRoomDatasource getTypeEquipByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getTypeEquipByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getTypeEquipByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getTypeEquipByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getTypeEquipByIdEquip -> EquipRoomDatasource.getTypeEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getTypeFertByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getTypeEquipByIdEquip(1)
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            val result = repository.getTypeEquipByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeEquip.NORMAL
            )
        }

    @Test
    fun `getIdCheckListByIdEquip - Check return failure if have error in EquipRoomDatasource getIdCheckListByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getIdCheckListByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "EquipRoomDatasource.getIdCheckListByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdCheckListByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getIdCheckListByIdEquip -> EquipRoomDatasource.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getIdCheckListByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getIdCheckListByIdEquip(1)
            ).thenReturn(
                Result.success(10)
            )
            val result = repository.getIdCheckListByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                10
            )
        }

    @Test
    fun `getFlagMechanicByIdEquip - Check return failure if have error in EquipRoomDatasource getFlagMechanicByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRoomDatasource.getFlagMechanicByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlagMechanicByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getFlagMechanicByIdEquip -> IEquipRoomDatasource.getFlagMechanicByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlagMechanicByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getFlagMechanicByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.getFlagMechanicByIdEquip(1)
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
    fun `getFlagTireByIdEquip - Check return failure if have error in EquipRoomDatasource getFlagTireByIdEquip`() =
        runTest {
            whenever(
                equipRoomDatasource.getFlagTireByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRoomDatasource.getFlagTireByIdEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlagTireByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getFlagTireByIdEquip -> IEquipRoomDatasource.getFlagTireByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlagTireByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipRoomDatasource.getFlagTireByIdEquip(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.getFlagTireByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}