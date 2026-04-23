package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderPreCECSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderPreCECSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckAccessCertificateTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ICheckAccessCertificate

    @Inject
    lateinit var preCECSharedPreferencesDatasource: IHeaderPreCECSharedPreferencesDatasource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_dateExitMill_is_null() =
        runTest {
            val result = usecase()
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
    fun check_return_false_if_dateFieldArrival_is_not_null_and_dateFieldArrival_is_null() =
        runTest {
            register(1)
            val result = usecase()
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
    fun check_return_true_if_dateFieldArrival_is_not_null_and_dateFieldArrival_not_is_null() =
        runTest {
            register(2)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }


    private suspend fun register(level: Int) {

        preCECSharedPreferencesDatasource.save(
            HeaderPreCECSharedPreferencesModel(
                dateExitMill = Date()
            )
        )

        if(level == 1) return

        preCECSharedPreferencesDatasource.save(
            HeaderPreCECSharedPreferencesModel(
                dateExitMill = Date(),
                dateFieldArrival = Date()
            )
        )

        if(level == 2) return
    }

}