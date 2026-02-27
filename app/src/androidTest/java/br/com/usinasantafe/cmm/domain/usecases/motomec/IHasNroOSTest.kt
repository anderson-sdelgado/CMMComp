package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.WEB_OS_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.lib.WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@HiltAndroidTest
class IHasNroOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IHasNroOS

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var rOSActivityDao: ROSActivityDao

    private val resultOSRetrofitEmptyList = """
        []
    """.trimIndent()

    private val resultOSRetrofitOne = """
        [
          {"idOS":1,"nroOS":123456,"idLibOS":10,"idPropAgr":20,"areaOS":150.75,"typoOS":1,"idEquip":30}
        ]
    """.trimIndent()

    private val resultROSActivity = """
        [
          {"idOS":1,"idActivity":10}
        ]
    """.trimIndent()

    @Test
    fun check_return_true_if_have_os_digit_flow_app_note_work() =
        runTest {
            hiltRule.inject()
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun check_return_failure_if_have_os_digit_flow_app_header_initial() =
        runTest {
            hiltRule.inject()
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_flow_app_note_work() =
        runTest {
            hiltRule.inject()
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_return_web_service_flow_app_note_work() =
        runTest {
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getListByNroOS -> IOSRetrofitDatasource.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_true_if_web_service_return_timeout_flow_app_note_work() =
        runTest(
            timeout = 15.seconds
        ) {
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(resultOSRetrofitEmptyList)
                    .setBodyDelay(12, TimeUnit.SECONDS)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGet = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.statusCon,
                false
            )
        }

    @Test
    fun check_return_false_if_web_service_return_list_empty_flow_app_note_work() =
        runTest {
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(resultOSRetrofitEmptyList)
                    .setBodyDelay(4, TimeUnit.SECONDS)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun check_return_failure_if_web_service_return_list_not_empty_flow_app_note_work_but_return_incorrect_r_os_activity() =
        runTest(
            timeout = 1.minutes
        ) {
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(resultOSRetrofitOne)
                    .setBodyDelay(4, TimeUnit.SECONDS)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun check_return_true_if_web_service_return_correct() =
        runTest {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_OS_LIST_BY_NRO_OS" -> MockResponse().setBody(resultOSRetrofitOne)
                        "/$WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS" -> MockResponse().setBody(
                            resultROSActivity
                        )
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
            hiltRule.inject()
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idServ = 1,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    checkMotoMec = true
                )
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val osList = osDao.listAll()
            assertEquals(
                osList.size,
                1
            )
            val os = osList[0]
            assertEquals(
                os.nroOS,
                123456
            )
            assertEquals(
                os.idLibOS,
                10
            )
            assertEquals(
                os.idPropAgr,
                20
            )
            assertEquals(
                os.areaOS,
                150.75,
                0.0
            )
            assertEquals(
                os.idEquip,
                30
            )
            val rOSActivityList = rOSActivityDao.listAll()
            assertEquals(
                rOSActivityList.size,
                1
            )
            val rOSActivity = rOSActivityList[0]
            assertEquals(
                rOSActivity.idROSActivity,
                1
            )
            assertEquals(
                rOSActivity.idOS,
                1
            )
            assertEquals(
                rOSActivity.idActivity,
                10
            )
        }

}