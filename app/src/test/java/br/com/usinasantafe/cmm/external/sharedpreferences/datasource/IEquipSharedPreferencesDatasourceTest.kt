package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IEquipSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IEquipSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IEquipSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check get and save`() =
        runTest {
            val data = EquipSharedPreferencesModel(
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
            datasource.save(data)
            val result = datasource.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.id,
                10
            )
            assertEquals(
                model.nro,
                2200
            )
            assertEquals(
                model.codClass,
                1
            )
            assertEquals(
                model.descrClass,
                "TRATOR"
            )
            assertEquals(
                model.codTurnEquip,
                1
            )
            assertEquals(
                model.idCheckList,
                1
            )
            assertEquals(
                model.typeEquip,
                TypeEquipMain.NORMAL
            )
            assertEquals(
                model.hourMeter,
                5000.0
            )
            assertEquals(
                model.classify,
                1
            )
            assertEquals(
                model.flagMechanic,
                true
            )
            assertEquals(
                model.flagTire,
                true
            )
        }

    @Test
    fun `Check getDescr return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
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
            datasource.save(data)
            val result = datasource.getDescr()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "2200 - TRATOR"
            )
        }

    @Test
    fun `Check getCodTurnEquip return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getCodTurnEquip()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                5
            )
        }

    @Test
    fun `Check getHourMeter return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getHourMeter()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                5000.0
            )
        }

    @Test
    fun `Check updateHourMeter execute successfully`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val resultBefore = datasource.getHourMeter()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            assertEquals(
                resultBefore.getOrNull()!!,
                5000.0
            )
            val result = datasource.updateHourMeter(10000.0)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultAfter = datasource.getHourMeter()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            assertEquals(
                resultAfter.getOrNull()!!,
                10000.0
            )
        }

    @Test
    fun `Check getTypeEquip return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getTypeEquip()
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
    fun `Check getIdCheckList return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getIdCheckList()
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
    fun `Check getFlagMechanic return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = false,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getFlagMechanic()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check getFlagTire return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = false,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getFlagTire()
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
    fun `Check getId return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 100,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getId()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                100
            )
        }

    @Test
    fun `Check getNro return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 100,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getNro()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2200
            )
        }
}