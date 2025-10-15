package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ROSActivityApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IROSActivityRetrofitDatasourceTest {

    @Test
    fun `recoverAll - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IROSActivityRetrofitDatasource.recoverAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `recoverAll - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IROSActivityRetrofitDatasource.recoverAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }


    @Test
    fun `recoverAll - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultROSAtivRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ROSActivityRetrofitModel(
                            idOS = 12345,
                            idActivity = 10
                        ),
                        ROSActivityRetrofitModel(
                            idOS = 67890,
                            idActivity = 11
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

    @Test
    fun `getListByNroOS - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IROSActivityRetrofitDatasource.getListByNroOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `getListByNroOS - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IROSActivityRetrofitDatasource.getListByNroOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `getListByNroOS - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultROSAtivRetrofitOne)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ROSActivityApi::class.java)
            val datasource = IROSActivityRetrofitDatasource(service)
            val result = datasource.listByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ROSActivityRetrofitModel(
                            idOS = 12345,
                            idActivity = 10
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

    private val resultROSAtivRetrofit = """
        [
          {"idROSAtiv":1,"idOS":12345,"idAtiv":10},
          {"idROSAtiv":2,"idOS":67890,"idAtiv":11}
        ]
    """.trimIndent()

    private val resultROSAtivRetrofitOne = """
        [
          {"idROSAtiv":1,"idOS":12345,"idAtiv":10}
        ]
    """.trimIndent()
}
