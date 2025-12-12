package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.EquipApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class IEquipRetrofitDatasourceTest {

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
            val service = retrofit.create(EquipApi::class.java)
            val datasource = IEquipRetrofitDatasource(service)
            val result = datasource.listAll(
                token = "TOKEN"
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
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
            val service = retrofit.create(EquipApi::class.java)
            val datasource = IEquipRetrofitDatasource(service)
            val result = datasource.listAll(
                token = "TOKEN"
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
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
                MockResponse().setBody(resultEquipRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(EquipApi::class.java)
            val datasource = IEquipRetrofitDatasource(service)
            val result = datasource.listAll(
                token = "TOKEN"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0].id,
                1
            )
            assertEquals(
                list[0].nro,
                1000001
            )
            assertEquals(
                list[0].codClass,
                1
            )
            assertEquals(
                list[0].descrClass,
                "Classe 1"
            )
            assertEquals(
                list[0].typeEquip,
                1
            )
            assertEquals(
                list[1].id,
                2
            )
            assertEquals(
                list[1].nro,
                1000002
            )
            assertEquals(
                list[1].codClass,
                2
            )
            assertEquals(
                list[1].descrClass,
                "Classe 2"
            )
            assertEquals(
                list[1].typeEquip,
                2
            )
            server.shutdown()
        }
}

val resultEquipRetrofit = """
    [
      {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","typeEquip":1},
      {"id":2,"nro":1000002,"codClass":2,"descrClass":"Classe 2","typeEquip":2}
    ]
""".trimIndent()
