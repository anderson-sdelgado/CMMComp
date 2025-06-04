package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RActivityStopApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IRAtivStopRetrofitDatasourceTest {

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
            val service = retrofit.create(RActivityStopApi::class.java)
            val datasource = IRActivityStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IRAtivParadaRetrofitDatasource.recoverAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
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
            val service = retrofit.create(RActivityStopApi::class.java)
            val datasource = IRActivityStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IRAtivParadaRetrofitDatasource.recoverAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }


    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultRAtivParadaRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(RActivityStopApi::class.java)
            val datasource = IRActivityStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        RActivityStopRetrofitModel(
                            idRActivityStop = 1,
                            idActivity = 10,
                            idStop = 401
                        ),
                        RActivityStopRetrofitModel(
                            idRActivityStop = 2,
                            idActivity = 10,
                            idStop = 402
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultRAtivParadaRetrofit = """
    [
      {"idRAtivParada":1,"idAtiv":10,"idParada":401},
      {"idRAtivParada":2,"idAtiv":10,"idParada":402}
    ]
""".trimIndent()
