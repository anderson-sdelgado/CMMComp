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
    fun `Check save, get, has data and clean table`() =
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
                true
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
}