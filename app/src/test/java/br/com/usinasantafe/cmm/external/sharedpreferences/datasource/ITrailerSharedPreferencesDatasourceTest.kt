package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.TrailerSharedPreferencesModel
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
class ITrailerSharedPreferencesDatasourceTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: ITrailerSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = ITrailerSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check save, get, has data table`() =
        runTest {
            val resultHasBefore = datasource.has()
            assertEquals(
                resultHasBefore.isSuccess,
                true
            )
            assertEquals(
                resultHasBefore.getOrNull()!!,
                false
            )
            val resultSave = datasource.add(
                TrailerSharedPreferencesModel(
                    idEquip = 1,
                    pos = 1
                )
            )
            assertEquals(
                resultSave.isSuccess,
                true
            )
            assertEquals(
                resultSave.getOrNull()!!,
                Unit
            )
            val resultList = datasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idEquip,
                1
            )
            assertEquals(
                model.pos,
                1
            )
            val resultHas = datasource.has()
            assertEquals(
                resultHas.isSuccess,
                true
            )
            assertEquals(
                resultHas.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check clean table`() =
        runTest {
            val resultHasBefore = datasource.has()
            assertEquals(
                resultHasBefore.isSuccess,
                true
            )
            assertEquals(
                resultHasBefore.getOrNull()!!,
                false
            )
            val resultSave = datasource.add(
                TrailerSharedPreferencesModel(
                    idEquip = 1,
                    pos = 1
                )
            )
            assertEquals(
                resultSave.isSuccess,
                true
            )
            assertEquals(
                resultSave.getOrNull()!!,
                Unit
            )
            val resultList = datasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val listBefore = resultList.getOrNull()!!
            assertEquals(
                listBefore.size,
                1
            )
            val model = listBefore[0]
            assertEquals(
                model.idEquip,
                1
            )
            assertEquals(
                model.pos,
                1
            )
            val resultHas = datasource.has()
            assertEquals(
                resultHas.isSuccess,
                true
            )
            assertEquals(
                resultHas.getOrNull()!!,
                true
            )
            val resultClean = datasource.clean()
            assertEquals(
                resultClean.isSuccess,
                true
            )
            assertEquals(
                resultClean.getOrNull()!!,
                Unit
            )
            val resultHasAfter = datasource.has()
            assertEquals(
                resultHasAfter.isSuccess,
                true
            )
            assertEquals(
                resultHasAfter.getOrNull()!!,
                false
            )
            val resultListAfter = datasource.list()
            assertEquals(
                resultListAfter.isSuccess,
                true
            )
            val listAfter = resultListAfter.getOrNull()!!
            assertEquals(
                listAfter.size,
                0
            )
        }
}