package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IMechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetNroOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNroOS

    @Inject
    lateinit var mechanicSharedPreferencesDatasource: IMechanicSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_value_of_field_is_incorrect() =
        runTest {
            val result = usecase(
                nroOS = "125dfd453"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOS -> toInt"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"125dfd453\""
            )
        }

    @Test
    fun check_alter_data_correct() =
        runTest {
            val data = MechanicSharedPreferencesModel(
                nroOS = 123456
            )
            mechanicSharedPreferencesDatasource.save(data)
            val resultGetBefore = mechanicSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                123456
            )
            val result = usecase("456789")
            assertEquals(
                result.isSuccess,
                true
            )
            val resultGetAfter = mechanicSharedPreferencesDatasource.get()
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
}