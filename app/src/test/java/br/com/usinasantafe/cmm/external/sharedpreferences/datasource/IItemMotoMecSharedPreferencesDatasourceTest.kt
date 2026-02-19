package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
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
class IItemMotoMecSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IItemMotoMecSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IItemMotoMecSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setIdStop - Check alter data correct`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                idStop = 1
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idStop,
                1
            )
            val result = datasource.setIdStop(2)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.idStop,
                2
            )
        }

    @Test
    fun `clean - Check clean table`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                idStop = 1
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idStop,
                1
            )
            val result = datasource.clean()
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter,
                ItemMotoMecSharedPreferencesModel()
            )
        }

    @Test
    fun `setNroEquipTranshipment - Check alter data correct`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                nroEquipTranshipment = 100
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroEquipTranshipment,
                100
            )
            val result = datasource.setNroEquipTranshipment(200)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroEquipTranshipment,
                200
            )
        }

    @Test
    fun `setIdNozzle - Check alter data correct`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                idNozzle = 1
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idNozzle,
                1
            )
            val result = datasource.setIdNozzle(2)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.idNozzle,
                2
            )
        }

    @Test
    fun `getIdNozzle - Check return failure if field is null`() =
        runTest {
            val result = datasource.getIdNozzle()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMotoMecSharedPreferencesDatasource.getIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getIdNozzle - Check return correct if field have value`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                idNozzle = 1
            )
            datasource.save(data)
            val result = datasource.getIdNozzle()
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
    fun `setValuePressure - Check alter data correct`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                valuePressure = 10.0,
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.valuePressure,
                10.0
            )
            val result = datasource.setValuePressure(20.0)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.valuePressure,
                20.0
            )
        }

    @Test
    fun `getValuePressure - Check return failure if field is null`() =
        runTest {
            val result = datasource.getValuePressure()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMotoMecSharedPreferencesDatasource.getValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getValuePressure - Check return correct if field have value`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                valuePressure = 1.0
            )
            datasource.save(data)
            val result = datasource.getValuePressure()
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
    fun `setSpeedPressure - Check alter data correct`() =
        runTest {
            val data = ItemMotoMecSharedPreferencesModel(
                speedPressure = 10
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.speedPressure,
                10
            )
            val result = datasource.setSpeedPressure(20)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.speedPressure,
                20
            )
        }

}