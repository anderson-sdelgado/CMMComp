package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RItemMenuStopApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class IRItemMenuStopRetrofitDatasourceTest {

    private val result = """
        [
            {
                "id": 1,
                "idFunction": 1,
                "idApp": 1,
                "idStop": 1
            },
            {
                "id": 2,
                "idFunction": 2,
                "idApp": 2,
                "idStop": 2
            }
        ]
    """.trimIndent()

    @Test
    fun `Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(RItemMenuStopApi::class.java)
            val datasource = IRItemMenuStopRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRItemMenuStopRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(RItemMenuStopApi::class.java)
            val datasource = IRItemMenuStopRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRItemMenuStopRetrofitDatasource.listAll",
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException",
            )
            server.shutdown()
        }

    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(result)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(RItemMenuStopApi::class.java)
            val datasource = IRItemMenuStopRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        RItemMenuStopRetrofitModel(
                            id = 1,
                            idFunction = 1,
                            idApp = 1,
                            idStop = 1
                        ),
                        RItemMenuStopRetrofitModel(
                            id = 2,
                            idFunction = 2,
                            idApp = 2,
                            idStop = 2
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}