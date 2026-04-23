package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderPreCECSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.intArrayOf
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderPreCECSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IHeaderPreCECSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IHeaderPreCECSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `get - Check return data`() =
        runTest {
            val data = HeaderPreCECSharedPreferencesModel(
                nroEquip = 1
            )
            datasource.save(data)
            val result = datasource.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.nroEquip,
                1
            )
        }

    @Test
    fun `setDateExitMill - Check return data`() =
        runTest {
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.dateExitMill,
                null
            )
            val result = datasource.setDateExitMill(Date(1764861924000))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.dateExitMill,
                Date(1764861924000)
            )
        }

    @Test
    fun `setDateFieldArrival - Check return data`() =
        runTest {
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.dateFieldArrival,
                null
            )
            val result = datasource.setDateFieldArrival(Date(1764861924000))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.dateFieldArrival,
                Date(1764861924000)
            )
        }

    @Test
    fun `setDateExitField - Check return data`() =
        runTest {
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.dateExitField,
                null
            )
            val result = datasource.setDateExitField(Date(1764861924000))
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.dateExitField,
                Date(1764861924000)
            )
        }

    @Test
    fun `setData - Check return data`() =
        runTest {
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroEquip,
                null
            )
            assertEquals(
                modelBefore.regColab,
                null
            )
            assertEquals(
                modelBefore.nroTurn,
                null
            )
            val result = datasource.setData(2200, 19759, 2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroEquip,
                2200
            )
            assertEquals(
                modelAfter.regColab,
                19759
            )
            assertEquals(
                modelAfter.nroTurn,
                2
            )
        }

    @Test
    fun `setIdRelease - Check return data`() =
        runTest {
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idRelease,
                null
            )
            val result = datasource.setIdRelease(10_000)
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
                modelAfter.idRelease,
                10_000
            )
        }
}