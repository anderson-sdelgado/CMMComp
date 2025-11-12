package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.LeiraRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class ILeiraRetrofitDatasourceTest {

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
            val service = retrofit.create(LeiraApi::class.java)
            val datasource = ILeiraRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "ILeiraRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(LeiraApi::class.java)
            val datasource = ILeiraRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "ILeiraRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultLeiraRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(LeiraApi::class.java)
            val datasource = ILeiraRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        LeiraRetrofitModel(
                            idLeira = 1,
                            codLeira = 1001,
                            statusLeira = 1
                        ),
                        LeiraRetrofitModel(
                            idLeira = 2,
                            codLeira = 1002,
                            statusLeira = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultLeiraRetrofit = """
    [
      {"idLeira":1,"codLeira":1001,"statusLeira":1},
      {"idLeira":2,"codLeira":1002,"statusLeira":1}
    ]
""".trimIndent()
