package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.usecases.performance.CheckClosePerformance
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckClosePerformanceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckClosePerformance

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: IHeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckClosePerformance -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_not_have_data_in_table_performance() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 2
                )
            )
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

    @Test
    fun check_return_true_if_not_have_idHeader_fielded_in_table_performance() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 2
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
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

    @Test
    fun check_return_true_if_not_have_idHeader_and_value_fielded_in_table_performance() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 2
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123789,
                    value = 10.0
                )
            )
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

    @Test
    fun check_return_false_if_have_idHeader_and_value_fielded_in_table_performance() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 2
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123789,
                    value = 10.0
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 456789,
                )
            )
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

}