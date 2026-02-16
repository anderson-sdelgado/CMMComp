package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.usecases.performance.SetPerformance
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
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
class ISetPerformanceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetPerformance

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
                "ISetPerformance -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"as1dfs52fda,5\""
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_fielded() =
        runTest {
            val result = usecase(1, "50.0")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPerformance -> IMotoMecRepository.updatePerformance -> IPerformanceRoomDatasource.update"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel.setValue(java.lang.Double)' on a null object reference"
            )
        }

    @Test
    fun check_value_altered_if_have_data_fielded() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = usecase(1, "50.0")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val list = performanceDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.value,
                50.0
            )
        }
}