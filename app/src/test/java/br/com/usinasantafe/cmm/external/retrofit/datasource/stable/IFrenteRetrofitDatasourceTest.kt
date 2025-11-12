package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FrenteRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IFrenteRetrofitDatasourceTest {

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
            val service = retrofit.create(FrenteApi::class.java)
            val datasource = IFrenteRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IFrenteRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(FrenteApi::class.java)
            val datasource = IFrenteRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IFrenteRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultFrenteRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(FrenteApi::class.java)
            val datasource = IFrenteRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        FrenteRetrofitModel(
                            idFrente = 1,
                            codFrente = 10,
                            descrFrente = "FRENTE AGRICOLA 1"
                        ),
                        FrenteRetrofitModel(
                            idFrente = 2,
                            codFrente = 20,
                            descrFrente = "FRENTE AGRICOLA 2"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultFrenteRetrofit = """
    [
      {"idFrente":1,"codFrente":10,"descrFrente":"FRENTE AGRICOLA 1"},
      {"idFrente":2,"codFrente":20,"descrFrente":"FRENTE AGRICOLA 2"}
    ]
""".trimIndent()
