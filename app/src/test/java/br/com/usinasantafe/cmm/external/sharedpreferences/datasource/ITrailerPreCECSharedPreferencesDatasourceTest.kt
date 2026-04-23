package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
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
class ITrailerPreCECSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: ITrailerPreCECSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = ITrailerPreCECSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `count - Check return count`() =
        runTest {
            val result = datasource.count()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                0
            )
        }

    @Test
    fun `setNroEquipTrailer - Check return data and trailer first`() =
        runTest {
            val resultBefore = datasource.count()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            assertEquals(
                resultBefore.getOrNull()!!,
                0
            )
            val result = datasource.setNroEquipTrailer(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val resultAfter = datasource.count()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            assertEquals(
                resultAfter.getOrNull()!!,
                1
            )
            val resultList = datasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()!!
            val model1 = list[0]
            assertEquals(
                model1.nroEquip,
                1
            )
            assertEquals(
                model1.idRelease,
                null
            )
        }

}