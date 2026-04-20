package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip
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
    fun `get - Check execute process correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
                TypeEquip.NORMAL
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
    fun `getDescr - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getCodTurnEquip - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getHourMeter - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
    fun `updateHourMeter - Check if process execute correctly`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
                Unit
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
    fun `getTypeEquip - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 1,
                typeEquip = TypeEquip.NORMAL,
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
                TypeEquip.NORMAL
            )
        }

    @Test
    fun `getIdCheckList - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getFlagMechanic - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getFlagTire - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getId - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 100,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
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
    fun `getNro - Check return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 100,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
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

    @Test
    fun `getCodClass - Check  return correct`() =
        runTest {
            val data = EquipSharedPreferencesModel(
                id = 100,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 5,
                idCheckList = 10,
                typeEquip = TypeEquip.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
            datasource.save(data)
            val result = datasource.getCodClass()
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