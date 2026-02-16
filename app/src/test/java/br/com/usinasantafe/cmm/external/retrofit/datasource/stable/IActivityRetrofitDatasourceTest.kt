package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ActivityApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class IActivityRetrofitDatasourceTest {

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
            val service = retrofit.create(ActivityApi::class.java)
            val datasource = IActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IActivityRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
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
            val service = retrofit.create(ActivityApi::class.java)
            val datasource = IActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IActivityRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                NullPointerException().toString()
            )
        }


    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultActivityRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ActivityApi::class.java)
            val datasource = IActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        ActivityRetrofitModel(
                            id = 1,
                            cod = 10,
                            descr = "Test"
                        )
                    )
                )
            )
        }

    private val resultActivityRetrofit = """
        [{"id":1,"cod":10,"descr":"Test"}]
    """.trimIndent()
}