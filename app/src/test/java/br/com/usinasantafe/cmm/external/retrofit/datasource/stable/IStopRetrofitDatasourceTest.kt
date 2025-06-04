package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.StopApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IStopRetrofitDatasourceTest {

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
            val service = retrofit.create(StopApi::class.java)
            val datasource = IStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IParadaRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(StopApi::class.java)
            val datasource = IStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IParadaRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultParadaRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(StopApi::class.java)
            val datasource = IStopRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        StopRetrofitModel(
                            idStop = 1,
                            codStop = 401,
                            descrStop = "MANUTENCAO MECANICA"
                        ),
                        StopRetrofitModel(
                            idStop = 2,
                            codStop = 402,
                            descrStop = "ABASTECIMENTO"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultParadaRetrofit = """
    [
      {"idParada":1,"codParada":401,"descrParada":"MANUTENCAO MECANICA"},
      {"idParada":2,"codParada":402,"descrParada":"ABASTECIMENTO"}
    ]
""".trimIndent()
