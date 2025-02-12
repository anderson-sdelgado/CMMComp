package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IConfigSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IConfigSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IConfigSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `get - Check return data correct the Config SharedPreferences internal`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                statusSend = StatusSend.SENT
            )
            datasource.save(data)
            val result = datasource.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val config = result.getOrNull()!!
            assertEquals(
                config.statusSend,
                StatusSend.SENT
            )
        }

    @Test
    fun `has - Check return false if not have data`() =
        runTest {
            val result = datasource.has()
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
    fun `has - Check return true if have data`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                statusSend = StatusSend.SENT
            )
            datasource.save(data)
            val result = datasource.has()
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