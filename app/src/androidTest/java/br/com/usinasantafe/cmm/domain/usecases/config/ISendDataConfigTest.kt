package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISendDataConfigTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SendDataConfig

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""{"idBD":1,"idEquip":1}""")
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "PMM",
                version = "1.00"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Config(
                    idBD = 1,
                    idEquip = 1
                )
            )
            server.shutdown()
        }

    @Test
    fun check_return_failure_if_return_web_service_missing_fields() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""{"idBD":1}""")
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "PMM",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig -> IConfigRepository.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: idEquip is 0"
            )
            server.shutdown()
        }

    @Test
    fun check_return_failure_if_response_is_404() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "PMM",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun check_return_failure_if_input_data_config_is_incorrect() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody("""{"idBD":1}""")
            )
            val result = usecase(
                number = "16997417840a",
                password = "12345",
                nroEquip = "310",
                app = "PMM",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"16997417840a\""
            )
            server.shutdown()
        }

    @Test
    fun check_return_failure_if_service_return_empty() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(MockResponse().setBody(""))
            val result = usecase(
                number = "16997417840",
                password = "12345",
                nroEquip = "310",
                app = "PMM",
                version = "1.00"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ConfigRetrofitDatasource.recoverToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.io.EOFException: End of input at line 1 column 1 path \$"
            )
            server.shutdown()
        }

}