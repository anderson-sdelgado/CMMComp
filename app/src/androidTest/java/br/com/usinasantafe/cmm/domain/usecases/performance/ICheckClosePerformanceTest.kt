package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
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
class ICheckClosePerformanceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckClosePerformance

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: IHeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao


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
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_table_header_moto_mec_room() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 2
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckClosePerformance -> IMotoMecRepository.finishHeader -> IHeaderMotoMecRoomDatasource.finish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setStatus(br.com.usinasantafe.cmm.lib.Status)' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_not_have_data_in_table_performance() =
        runTest {
            saveHeaderMotoMecRoom()
            headerMotoMecSharedPreferencesDatasource.save(
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
                true
            )
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.status,
                Status.FINISH
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun check_return_true_if_not_have_idHeader_fielded_in_table_performance() =
        runTest {
            saveHeaderMotoMecRoom()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
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
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.status,
                Status.FINISH
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun check_return_true_if_not_have_idHeader_and_value_fielded_in_table_performance() =
        runTest {
            saveHeaderMotoMecRoom()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123456,
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
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
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.status,
                Status.FINISH
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun check_return_false_if_have_idHeader_and_value_fielded_in_table_performance() =
        runTest {
            saveHeaderMotoMecRoom()
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    id = 1
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123456,
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123789,
                    value = 10.0
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
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
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.status,
                Status.OPEN
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SENT
            )
        }


    private suspend fun saveHeaderMotoMecRoom() {
        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 10,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10000.0,
                dateHourInitial = Date(1748359002),
                statusCon = true,
                statusSend = StatusSend.SENT
            )
        )
    }

}