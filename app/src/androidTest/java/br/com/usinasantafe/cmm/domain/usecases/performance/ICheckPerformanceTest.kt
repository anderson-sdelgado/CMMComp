package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckPerformanceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckPerformance

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_value_is_incorrect() =
        runTest {
            val result = usecase(1,"as1dfs52fda,5")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"as1dfs52fda,5\""
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_performance_room() =
        runTest {
            val result = usecase(1,"50,0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPerformance -> IPerformanceRepository.getNroOSById -> IPerformanceRoomDatasource.getNroOSById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel.getNroOS()' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_value_input_is_less_than_value_150_and_OS_not_exists_in_database() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = usecase(1, "50,0")
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
    fun check_return_true_if_value_input_is_greater_than_value_150_and_OS_not_exists_in_database() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = usecase(1, "250,0")
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
    fun check_return_true_if_value_input_is_less_than_value_BD_and_OS_exists_in_database() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            osDao.insert(
                OSRoomModel(
                    idOS = 1,
                    nroOS = 123456,
                    idLibOS = 1,
                    idPropAgr = 1,
                    areaOS = 100.0,
                    idEquip = 1
                )
            )
            val result = usecase(1, "50,0")
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
    fun check_return_true_if_value_input_is_greater_than_value_BD_and_OS_exists_in_database() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            osDao.insert(
                OSRoomModel(
                    idOS = 1,
                    nroOS = 123456,
                    idLibOS = 1,
                    idPropAgr = 1,
                    areaOS = 100.0,
                    idEquip = 1
                )
            )
            val result = usecase(1, "250,0")
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