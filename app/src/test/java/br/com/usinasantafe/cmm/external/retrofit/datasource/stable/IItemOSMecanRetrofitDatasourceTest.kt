package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemOSMecanApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMecanRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IItemOSMecanRetrofitDatasourceTest {

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
            val service = retrofit.create(ItemOSMecanApi::class.java)
            val datasource = IItemOSMecanRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IItemOSMecanRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(ItemOSMecanApi::class.java)
            val datasource = IItemOSMecanRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IItemOSMecanRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultItemOSMecanRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemOSMecanApi::class.java)
            val datasource = IItemOSMecanRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ItemOSMecanRetrofitModel(
                            idItemOS = 1,
                            idOS = 501,
                            seqItemOS = 1,
                            idServItemOS = 301,
                            idCompItemOS = 201
                        ),
                        ItemOSMecanRetrofitModel(
                            idItemOS = 2,
                            idOS = 501,
                            seqItemOS = 2,
                            idServItemOS = 302,
                            idCompItemOS = 202
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultItemOSMecanRetrofit = """
    [
      {"idItemOS":1,"idOS":501,"seqItemOS":1,"idServItemOS":301,"idCompItemOS":201},
      {"idItemOS":2,"idOS":501,"seqItemOS":2,"idServItemOS":302,"idCompItemOS":202}
    ]
""".trimIndent()
