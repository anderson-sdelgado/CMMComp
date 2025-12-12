package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.EquipMainRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IEquipRepositoryTest {

    private val equipRetrofitDatasource = mock<EquipRetrofitDatasource>()
    private val equipRoomDatasource = mock<EquipRoomDatasource>()
    private val equipSharedPreferencesDatasource = mock<EquipSharedPreferencesDatasource>()
    private val repository = IEquipRepository(
        equipRetrofitDatasource = equipRetrofitDatasource,
        equipRoomDatasource = equipRoomDatasource,
        equipSharedPreferencesDatasource = equipSharedPreferencesDatasource
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
                    typeEquip = TypeEquipSecondary.TRANSHIPMENT
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    typeEquipSecondary = TypeEquipSecondary.TRANSHIPMENT
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
                    typeEquip = TypeEquipSecondary.TRANSHIPMENT
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    typeEquipSecondary = TypeEquipSecondary.TRANSHIPMENT
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
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                equipRetrofitDatasource.listAll(
                    token = "token"
                )
            ).thenReturn(
                resultFailure(
                    "IEquipRetrofitDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll(
                token = "token"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.listAll -> IEquipRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                EquipMainRetrofitModel(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    typeEquip = 1,
                )
            )
            val entityList = listOf(
                Equip(
                    id = 1,
                    nro = 10,
                    codClass = 100,
                    descrClass = "CAMINHAO",
                    typeEquipSecondary = TypeEquipSecondary.REEL

                )
            )
            whenever(
                equipRetrofitDatasource.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll(
                token = "token"
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
    fun `getIdEquipMain - Check return failure if have error in EquipSharedPreferencesDatasource getId`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getId",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdEquipMain()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdEquipMain - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdEquipMain()
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
    fun `getNroEquipMain - Check return failure if have error in EquipSharedPreferencesDatasource getNro`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getNro()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getNro",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNroEquipMain()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getNroEquipMain -> IEquipSharedPreferencesDatasource.getNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroEquipMain - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getNro()
            ).thenReturn(
                Result.success(2200)
            )
            val result = repository.getNroEquipMain()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2200
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return failure if have error in EquipSharedPreferencesDatasource getId`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getId",
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
                "IEquipRepository.getDescrByIdEquip -> IEquipSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return failure if have error in EquipSharedPreferencesDatasource getDescrClass and idEquip is equals to idEquipSharedPreferences`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipSharedPreferencesDatasource.getDescr()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getDescrClass",
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
                "IEquipRepository.getDescrByIdEquip -> IEquipSharedPreferencesDatasource.getDescrClass"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return correct if function execute successfully and idEquip is equals to idEquipSharedPreferences`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                equipSharedPreferencesDatasource.getDescr()
            ).thenReturn(
                Result.success("TRATOR")
            )
            val result = repository.getDescrByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TRATOR"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return failure if have error in EquipRoomDatasource getDescrByIdEquip and idEquip is different to idEquipSharedPreferences`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRoomDatasource.getDescrByIdEquip(1)
            ).thenReturn(
                resultFailure(
                    "IEquipRoomDatasource.getDescrByIdEquip",
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
                "IEquipRepository.getDescrByIdEquip -> IEquipRoomDatasource.getDescrByIdEquip"
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
                equipSharedPreferencesDatasource.getId()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                equipRoomDatasource.getDescrByIdEquip(1)
            ).thenReturn(
                Result.success("TRATOR")
            )
            val result = repository.getDescrByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TRATOR"
            )
        }

    @Test
    fun `getCodTurnEquip - Check return failure if have error in EquipSharedPreferencesDatasource getCodTurnEquip`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getCodTurnEquip()
            ).thenReturn(
                resultFailure(
                    "EquipSharedPreferencesDatasource.getCodTurnEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getCodTurnEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getCodTurnEquip -> EquipSharedPreferencesDatasource.getCodTurnEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getCodTurnEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getCodTurnEquip()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getCodTurnEquip()
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
    fun `getHourMete - Check return failure if have error in EquipSharedPreferencesDatasource getHourMeter`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getHourMeter()
            ).thenReturn(
                resultFailure(
                    "EquipSharedPreferencesDatasource.getHourMeter",
                    "-",
                    Exception()
                )
            )
            val result = repository.getHourMeter()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getHourMeter -> EquipSharedPreferencesDatasource.getHourMeter"
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
                equipSharedPreferencesDatasource.getHourMeter()
            ).thenReturn(
                Result.success(1.0)
            )
            val result = repository.getHourMeter()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1.0
            )
        }

    @Test
    fun `updateHourMeter - Check return failure if have error in EquipSharedPreferencesDatasource updateHourMeter`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.updateHourMeter(
                    hourMeter = 1.0
                )
            ).thenReturn(
                resultFailure(
                    "EquipSharedPreferencesDatasource.updateHourMeter",
                    "-",
                    Exception()
                )
            )
            val result = repository.updateHourMeter(
                hourMeter = 1.0
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.updateHourMeter -> EquipSharedPreferencesDatasource.updateHourMeter"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getTypeEquip - Check return failure if have error in EquipSharedPreferencesDatasource getTypeEquip`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getTypeEquip()
            ).thenReturn(
                resultFailure(
                    "EquipSharedPreferencesDatasource.getTypeEquip",
                    "-",
                    Exception()
                )
            )
            val result = repository.getTypeEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getTypeEquip -> EquipSharedPreferencesDatasource.getTypeEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getTypeFert - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getTypeEquip()
            ).thenReturn(
                Result.success(TypeEquipMain.NORMAL)
            )
            val result = repository.getTypeEquip()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeEquipMain.NORMAL
            )
        }

    @Test
    fun `getIdCheckList - Check return failure if have error in EquipSharedPreferencesDatasource getIdCheckListByIdEquip`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getIdCheckList()
            ).thenReturn(
                resultFailure(
                    "EquipSharedPreferencesDatasource.getIdCheckList",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdCheckList()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getIdCheckList -> EquipSharedPreferencesDatasource.getIdCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception",
            )
        }

    @Test
    fun `getIdCheckList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getIdCheckList()
            ).thenReturn(
                Result.success(10)
            )
            val result = repository.getIdCheckList()
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
    fun `getFlagMechanic - Check return failure if have error in EquipSharedPreferencesDatasource getFlagMechanic`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getFlagMechanic()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getFlagMechanic",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlagMechanic()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getFlagMechanic -> IEquipSharedPreferencesDatasource.getFlagMechanic"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlagMechanic - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getFlagMechanic()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.getFlagMechanic()
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
    fun `getFlagTire - Check return failure if have error in EquipSharedPreferencesDatasource getFlagTire`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getFlagTire()
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.getFlagTire",
                    "-",
                    Exception()
                )
            )
            val result = repository.getFlagTire()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.getFlagTire -> IEquipSharedPreferencesDatasource.getFlagTire"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getFlagTire - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                equipSharedPreferencesDatasource.getFlagTire()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.getFlagTire()
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
    fun `saveEquipMain - Check return failure if have error in EquipSharedPreferencesDatasource save`() =
        runTest {
            val entity = Equip(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquipMain = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            val model = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            whenever(
                equipSharedPreferencesDatasource.save(model)
            ).thenReturn(
                resultFailure(
                    "IEquipSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveEquipMain(entity)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRepository.saveEquipMain -> IEquipSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveEquipMain - Check return correct if function execute successfully`() =
        runTest {
            val entity = Equip(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquipMain = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            val model = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            whenever(
                equipSharedPreferencesDatasource.save(model)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveEquipMain(entity)
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