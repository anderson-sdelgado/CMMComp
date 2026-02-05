package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
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
class IListPerformanceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListPerformance

    @Inject
    lateinit var headerSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_table_header_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListPerformance -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_in_table_performance_room() =
        runTest {
            headerSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_data_fielded_in_table_performance_room() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123456
                )
            )
            headerSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun check_return_list_if_have_data_fielded_in_table_performance_room() =
        runTest {
            performanceDao.insert(
            PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    value = 10.0
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123456
                )
            )
            headerSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.value,
                10.0
            )
        }
}