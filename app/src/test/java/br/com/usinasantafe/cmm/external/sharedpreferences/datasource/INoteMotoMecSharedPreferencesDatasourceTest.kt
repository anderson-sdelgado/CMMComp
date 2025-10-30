package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class INoteMotoMecSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: INoteMotoMecSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = INoteMotoMecSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setNroOS - Check alter data correct`() =
        runTest {
            val data = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                statusCon = false
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                123456
            )
            assertEquals(
                modelBefore.statusCon,
                false
            )
            val result = datasource.setNroOSAndStatusCon(
                nroOS = 456789,
                statusCon = true
            )
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
                modelAfter.nroOS,
                456789
            )
            assertEquals(
                modelAfter.statusCon,
                true
            )
        }

    @Test
    fun `setIdActivity - Check alter data correct`() =
        runTest {
            val data = NoteMotoMecSharedPreferencesModel(
                idActivity = 1,
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idActivity,
                1
            )
            val result = datasource.setIdActivity(2)
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
                modelAfter.idActivity,
                2
            )
        }

    @Test
    fun `getIdActivity - Check return failure if field is null`() =
        runTest {
            val result = datasource.getIdActivity()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getIdActivity - Check return correct if function execute successfully`() =
        runTest {
            val data = NoteMotoMecSharedPreferencesModel(
                idActivity = 1
            )
            datasource.save(data)
            val result = datasource.getIdActivity()
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
    fun `setIdStop - Check alter data correct`() =
        runTest {
            val data = NoteMotoMecSharedPreferencesModel(
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
            val data = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1,
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
            assertEquals(
                modelBefore.idActivity,
                1
            )
            assertEquals(
                modelBefore.nroOS,
                123456
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
                NoteMotoMecSharedPreferencesModel()
            )
        }
}



