package br.com.usinasantafe.cmm.domain.usecases.config

import br.com.usinasantafe.cmm.domain.entities.variable.Config
import br.com.usinasantafe.cmm.domain.entities.variable.Equip
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

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

    private val result = """
        {
            "idServ": 16,
            "equip": {
                "id": 2065,
                "nro": 2200,
                "classify": 1,
                "codClass": 8,
                "descrClass": "CAVALO CANAVIEIRO",
                "codTurnEquip": 22,
                "idCheckList": 3522,
                "typeEquip": 1,
                "hourMeter": 0,
                "flagMechanic": 0,
                "flagTire": 0
            }
        }
    """.trimIndent()

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            val server = MockWebServer()
            server.start(8080)
            server.enqueue(
                MockResponse().setBody(result)
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
                    idServ = 16,
                    equip = Equip(
                        id = 2065,
                        nro = 2200,
                        codClass = 8,
                        descrClass = "CAVALO CANAVIEIRO",
                        codTurnEquip = 22,
                        idCheckList = 3522,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = false,
                        flagTire = false
                    )
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
                "java.lang.IllegalArgumentException: The field 'idServ' cannot is null."
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