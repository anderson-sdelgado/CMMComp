package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ServicoApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServicoRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IServicoRetrofitDatasourceTest {

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
            val service = retrofit.create(ServicoApi::class.java)
            val datasource = IServicoRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IServicoRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(ServicoApi::class.java)
            val datasource = IServicoRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IServicoRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultServicoRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ServicoApi::class.java)
            val datasource = IServicoRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ServicoRetrofitModel(
                            idServico = 1,
                            codServico = 301,
                            descrServico = "TROCA DE OLEO"
                        ),
                        ServicoRetrofitModel(
                            idServico = 2,
                            codServico = 302,
                            descrServico = "REPARO HIDRAULICO"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultServicoRetrofit = """
    [
      {"idServico":1,"codServico":301,"descrServico":"TROCA DE OLEO"},
      {"idServico":2,"codServico":302,"descrServico":"REPARO HIDRAULICO"}
    ]
""".trimIndent()
