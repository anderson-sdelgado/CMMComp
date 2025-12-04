package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IConfigSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IConfigSharedPreferencesDatasource

    @Before
    fun setup() {
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

    @Test
    fun `getPassword - Check return failure if field is null`() =
        runTest {
            val result = datasource.getPassword()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigSharedPreferencesDatasource.getPassword"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getPassword - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                password = "12345"
            )
            datasource.save(data)
            val result = datasource.getPassword()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "12345"
            )
        }

    @Test
    fun `getFlagUpdate - Check return FlagUpdate OUTDATED if field is null`() =
        runTest {
            val result = datasource.getFlagUpdate()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlagUpdate.OUTDATED
            )
        }

    @Test
    fun `getFlagUpdate - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                flagUpdate = FlagUpdate.UPDATED
            )
            datasource.save(data)
            val result = datasource.getFlagUpdate()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlagUpdate.UPDATED
            )
        }

    @Test
    fun `getNumber - Check return failure if field is null`() =
        runTest {
            val result = datasource.getNumber()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigSharedPreferencesDatasource.getNumber"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getNumber - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                number = 16997417840
            )
            datasource.save(data)
            val result = datasource.getNumber()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                16997417840
            )
        }

    @Test
    fun `getIdEquip - Check return failure if field is null`() =
        runTest {
            val result = datasource.getIdEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getIdEquip - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                idEquip = 1
            )
            datasource.save(data)
            val result = datasource.getIdEquip()
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
    fun `getIdTurnCheckListLast - Check return null if idTurnCheckListLast not is null`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                idTurnCheckListLast = 1
            )
            datasource.save(data)
            val result = datasource.getIdTurnCheckListLast()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                1
            )
        }

    @Test
    fun `getDateCheckListLast - Check return failure if field is null`() =
        runTest {
            val result = datasource.getDateCheckListLast()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigSharedPreferencesDatasource.getDateCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getDateCheckListLast - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                dateLastCheckList = Date(1750857777000)
            )
            datasource.save(data)
            val result = datasource.getDateCheckListLast()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Date(1750857777000)
            )
        }

    @Test
    fun `getNroEquip - Check return failure if field is null`() =
        runTest {
            val result = datasource.getNroEquip()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigSharedPreferencesDatasource.getNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getNroEquip - Check return correct if function execute successfully`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                nroEquip = 100L
            )
            datasource.save(data)
            val result = datasource.getNroEquip()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                100L
            )
        }

    @Test
    fun `setFlagUpdate - Check return data correct the Config SharedPreferences internal`() =
        runTest {
            val data = ConfigSharedPreferencesModel(
                statusSend = StatusSend.SENT
            )
            datasource.save(data)
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                modelBefore.flagUpdate,
                FlagUpdate.OUTDATED
            )
            val result = datasource.setFlagUpdate(FlagUpdate.UPDATED)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultAfter = datasource.get()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            val modelAfter = resultAfter.getOrNull()!!
            assertEquals(
                modelAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                modelAfter.flagUpdate,
                FlagUpdate.UPDATED
            )
        }
    
}