package br.com.usinasantafe.cmm.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderMotoMecSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IHeaderMotoMecSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IHeaderMotoMecSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setRegOperator - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                regOperator = 19759
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.regOperator,
                19759
            )
            val result = datasource.setRegOperator(19758)
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
                modelAfter.regOperator,
                19758
            )
            assertEquals(
                modelAfter.statusCon,
                true
            )
        }

    @Test
    fun `setIdEquip - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                idEquip = 100,
                typeEquip = TypeEquip.NORMAL
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idEquip,
                100
            )
            val result = datasource.setDataEquip(
                idEquip = 200,
                typeEquip = TypeEquip.FERT
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
                modelAfter.idEquip,
                200
            )
            assertEquals(
                modelAfter.typeEquip,
                TypeEquip.FERT
            )
        }

    @Test
    fun `setIdTurn - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                idTurn = 1
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.idTurn,
                1
            )
            val result = datasource.setIdTurn(2)
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
                modelAfter.idTurn,
                2
            )
        }

    @Test
    fun `setNroOS - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456
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
            val result = datasource.setNroOS(456789)
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
        }

    @Test
    fun `setIdActivity - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                idActivity = 1
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
    fun `getNroOS - Check return failure if field is null`() =
        runTest {
            val result = datasource.getNroOS()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getNroOS - Check return correct if function execute successfully`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456
            )
            datasource.save(data)
            val result = datasource.getNroOS()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                123456
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
                "IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getIdEquip - Check return correct if function execute successfully`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
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
    fun `setHourMeter - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                hourMeter = 1.0
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.hourMeter,
                1.0
            )
            val result = datasource.setHourMeter(2.0)
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
                modelAfter.hourMeter,
                2.0
            )
        }

    @Test
    fun `clean - Check clean table`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                regOperator = 19759,
                idEquip = 100,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeter = 1.0
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.regOperator,
                19759
            )
            assertEquals(
                modelBefore.idEquip,
                100
            )
            assertEquals(
                modelBefore.idTurn,
                1
            )
            assertEquals(
                modelBefore.nroOS,
                123456
                )
            assertEquals(
                modelBefore.idActivity,
                1
            )
            assertEquals(
                modelBefore.hourMeter,
                1.0
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
                HeaderMotoMecSharedPreferencesModel()
            )
        }

    @Test
    fun `setStatusCon - Check alter data correct`() =
        runTest {
            val data = HeaderMotoMecSharedPreferencesModel(
                statusCon = true
            )
            datasource.save(data)
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.statusCon,
                true
            )
            val result = datasource.setStatusCon(false)
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
                modelAfter.statusCon,
                false
            )
        }

}