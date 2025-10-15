package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.PressaoBocalApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressaoBocalRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IPressaoBocalRetrofitDatasourceTest {

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
            val service = retrofit.create(PressaoBocalApi::class.java)
            val datasource = IPressaoBocalRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IPressaoBocalRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(PressaoBocalApi::class.java)
            val datasource = IPressaoBocalRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IPressaoBocalRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultPressaoBocalRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(PressaoBocalApi::class.java)
            val datasource = IPressaoBocalRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        PressaoBocalRetrofitModel(
                            idPressaoBocal = 1,
                            idBocal = 10,
                            valorPressao = 3.5,
                            valorVeloc = 50
                        ),
                        PressaoBocalRetrofitModel(
                            idPressaoBocal = 2,
                            idBocal = 11,
                            valorPressao = 4.0,
                            valorVeloc = 60
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultPressaoBocalRetrofit = """
    [
      {"idPressaoBocal":1,"idBocal":10,"valorPressao":3.5,"valorVeloc":50},
      {"idPressaoBocal":2,"idBocal":11,"valorPressao":4.0,"valorVeloc":60}
    ]
""".trimIndent()
