package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemCheckListApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemCheckListRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IItemCheckListRetrofitDatasourceTest {

    private val resultItemCheckListRetrofit = """
        [
          {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
          {"idItemCheckList":2,"idCheckList":101,"descrItemCheckList":"Verificar Freios"}
        ]
    """.trimIndent()

    @Test
    fun `listBryNroEquip - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.listByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRetrofitDatasource.listByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `listBryNroEquip - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.listByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRetrofitDatasource.listByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `listBryNroEquip - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemCheckListRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.listByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        ItemCheckListRetrofitModel(
                            idItemCheckList = 1,
                            idCheckList = 101,
                            descrItemCheckList = "Verificar Nível de Óleo"
                        ),
                        ItemCheckListRetrofitModel(
                            idItemCheckList = 2,
                            idCheckList = 101,
                            descrItemCheckList = "Verificar Freios"
                        )
                    )
                )
            )
            server.shutdown()
        }

    @Test
    fun `checkUpdateByNroEquip - Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.checkUpdateByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRetrofitDatasource.checkUpdateByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 4 path \$."
            )
            server.shutdown()
        }

    @Test
    fun `checkUpdateByNroEquip - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.checkUpdateByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemCheckListRetrofitDatasource.checkUpdateByNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `checkUpdateByNroEquip - Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("""{"qtd":1}""")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemCheckListApi::class.java)
            val datasource = IItemCheckListRetrofitDatasource(service)
            val result = datasource.checkUpdateByNroEquip(
                token = "TOKEN",
                nroEquip = 10L
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.qtd,
                1
            )
            server.shutdown()
        }
}
