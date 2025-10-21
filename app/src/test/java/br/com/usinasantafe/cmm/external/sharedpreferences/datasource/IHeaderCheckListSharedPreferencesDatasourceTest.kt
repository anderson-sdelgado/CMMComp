package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
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
class IHeaderCheckListSharedPreferencesDatasourceTest {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IHeaderCheckListSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IHeaderCheckListSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check save data, get data e clean table`() =
        runTest {
            val data = HeaderCheckListSharedPreferencesModel(
                nroEquip = 1,
                regOperator = 1,
                nroTurn = 1,
                dateHour = Date(1760546436)
            )
            val resultSave = datasource.save(data)
            assertEquals(
                resultSave.isSuccess,
                true
            )
            assertEquals(
                resultSave.getOrNull()!!,
                true
            )
            val resultGet = datasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.nroEquip,
                data.nroEquip
            )
            assertEquals(
                model.regOperator,
                data.regOperator
            )
            assertEquals(
                model.nroTurn,
                data.nroTurn
            )
            val resultClean = datasource.clean()
            assertEquals(
                resultClean.isSuccess,
                true
            )
            assertEquals(
                resultClean.getOrNull()!!,
                true
            )
            val resultGetAfterClean = datasource.get()
            assertEquals(
                resultGetAfterClean.isSuccess,
                true
            )
            assertEquals(
                resultGetAfterClean.getOrNull()!!,
                HeaderCheckListSharedPreferencesModel()
            )
        }

    @Test
    fun `Check return false if not have data in header check list`() =
        runTest {
            val result = datasource.checkOpen()
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
    fun `Check return true if have data in header check list`() =
        runTest {
            val data = HeaderCheckListSharedPreferencesModel(
                nroEquip = 1,
                regOperator = 1,
                nroTurn = 1,
                dateHour = Date(1760546436)
            )
            datasource.save(data)
            val result = datasource.checkOpen()
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