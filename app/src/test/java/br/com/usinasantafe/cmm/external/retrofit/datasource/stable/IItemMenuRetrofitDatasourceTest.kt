package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemMenuApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemMenuRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class IItemMenuRetrofitDatasourceTest {

    private val resultItemMenuRetrofit = """
        [
            {
                "id": 1,
                "descr": "ITEM 1",
                "idType": 1,
                "pos": 1,
                "idFunction": 1,
                "idApp": 1
            },
            {
                "id": 2,
                "descr": "ITEM 2",
                "idType": 1,
                "pos": 2,
                "idFunction": 2,
                "idApp": 1
            }
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
            val service = retrofit.create(ItemMenuApi::class.java)
            val datasource = IItemMenuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IItemMenuPMMRetrofitDatasource.listAll",
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
            val service = retrofit.create(ItemMenuApi::class.java)
            val datasource = IItemMenuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IItemMenuPMMRetrofitDatasource.listAll",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemMenuRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemMenuApi::class.java)
            val datasource = IItemMenuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        ItemMenuRetrofitModel(
                            id = 1,
                            descr = "ITEM 1",
                            idType = 1,
                            pos = 1,
                            idFunction = 1,
                            idApp = 1
                        ),
                        ItemMenuRetrofitModel(
                            id = 2,
                            descr = "ITEM 2",
                            idType = 1,
                            pos = 2,
                            idFunction = 2,
                            idApp = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}