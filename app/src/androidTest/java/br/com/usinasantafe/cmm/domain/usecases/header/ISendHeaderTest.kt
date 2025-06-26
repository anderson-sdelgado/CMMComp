package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class ISendHeaderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISendHeader

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendHeader -> IConfigRepository.getNumber -> IConfigSharedPreferencesDatasource.getNumber"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_to_send() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    nroEquip = 310,
                    app = "PMM",
                    version = "1.00",
                    checkMotoMec = false,
                    idBD = 1,
                    idEquip = 20
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendHeader -> IMotoMecRepository.send -> IMotoMecRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_error() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    nroEquip = 310,
                    app = "PMM",
                    version = "1.00",
                    checkMotoMec = false,
                    idBD = 1,
                    idEquip = 20
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = true
                )
            )

            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""{"idBD":1,"idEquip":1}""")
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendHeader -> IMotoMecRepository.send -> IMotoMecRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    nroEquip = 310,
                    app = "PMM",
                    version = "1.00",
                    checkMotoMec = false,
                    idBD = 1,
                    idEquip = 20
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    id = 1,
                    regOperator = 19759,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    id = 1,
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = true
                )
            )
            val headerListBefore = headerMotoMecDao.listAll()
            assertEquals(
                headerListBefore.size,
                1
            )
            val headerModelBefore = headerListBefore[0]
            assertEquals(
                headerModelBefore.id,
                1
            )
            assertEquals(
                headerModelBefore.regOperator,
                19759
            )
            assertEquals(
                headerModelBefore.idEquip,
                1
            )
            assertEquals(
                headerModelBefore.idTurn,
                1
            )
            assertEquals(
                headerModelBefore.nroOS,
                123456
            )
            assertEquals(
                headerModelBefore.idActivity,
                1
            )
            assertEquals(
                headerModelBefore.hourMeterInitial,
                10.0,
                0.0
            )
            assertEquals(
                headerModelBefore.dateHourInitial.time,
                1748359002
            )
            assertEquals(
                headerModelBefore.statusCon,
                true
            )
            assertEquals(
                headerModelBefore.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                headerModelBefore.idBD,
                null
            )
            val noteListBefore = noteMotoMecDao.listAll()
            assertEquals(
                noteListBefore.size,
                1
            )
            val noteModelBefore = noteListBefore[0]
            assertEquals(
                noteModelBefore.id,
                1
            )
            assertEquals(
                noteModelBefore.idHeader,
                1
            )
            assertEquals(
                noteModelBefore.nroOS,
                123456
            )
            assertEquals(
                noteModelBefore.idActivity,
                1
            )
            assertEquals(
                noteModelBefore.statusCon,
                true
            )
            assertEquals(
                noteModelBefore.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                noteModelBefore.idBD,
                null
            )
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
//                MockResponse().setBody("""[{"idBD":1,"id":1,"noteMotoMecList":[{"idBD":1,"id":1}]}]""")
                MockResponse().setBody("""[{"idBD":"1071061","id":1}]""")
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
            val headerListAfter = headerMotoMecDao.listAll()
            assertEquals(
                headerListAfter.size,
                1
            )
            val headerModelAfter = headerListAfter[0]
            assertEquals(
                headerModelAfter.id,
                1
            )
            assertEquals(
                headerModelAfter.regOperator,
                19759
            )
            assertEquals(
                headerModelAfter.idEquip,
                1
            )
            assertEquals(
                headerModelAfter.idTurn,
                1
            )
            assertEquals(
                headerModelAfter.nroOS,
                123456
            )
            assertEquals(
                headerModelAfter.idActivity,
                1
            )
            assertEquals(
                headerModelAfter.hourMeterInitial,
                10.0,
                0.0
            )
            assertEquals(
                headerModelAfter.dateHourInitial.time,
                1748359002
            )
            assertEquals(
                headerModelAfter.statusCon,
                true
            )
            assertEquals(
                headerModelAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                headerModelAfter.idBD,
                1L
            )
            val noteListAfter = noteMotoMecDao.listAll()
            assertEquals(
                noteListAfter.size,
                1
            )
            val noteModelAfter = noteListAfter[0]
            assertEquals(
                noteModelAfter.id,
                1
            )
            assertEquals(
                noteModelAfter.idHeader,
                1
            )
            assertEquals(
                noteModelAfter.nroOS,
                123456
            )
            assertEquals(
                noteModelAfter.idActivity,
                1
            )
            assertEquals(
                noteModelAfter.statusCon,
                true
            )
            assertEquals(
                noteModelAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                noteModelAfter.idBD,
                1L
            )
            server.shutdown()
        }

}