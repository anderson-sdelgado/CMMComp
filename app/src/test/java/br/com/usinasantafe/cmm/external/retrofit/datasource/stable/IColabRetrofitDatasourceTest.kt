package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ColabRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IColabRetrofitDatasourceTest {
    private val resultColabRetrofit = """
        [
          {"regColab":12345,"nameColab":"João da Silva"},
          {"regColab":67890,"nameColab":"Maria Oliveira"}
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
            val service = retrofit.create(ColabApi::class.java)
            val datasource = IColabRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IColabRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(ColabApi::class.java)
            val datasource = IColabRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IColabRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultColabRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ColabApi::class.java)
            val datasource = IColabRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ColabRetrofitModel(
                            regColab = 12345L,
                            nameColab = "João da Silva"
                        ),
                        ColabRetrofitModel(
                            regColab = 67890L,
                            nameColab = "Maria Oliveira"
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}


