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
                "IEquipRetrofitDatasource.recoverAll"
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
                "IEquipRetrofitDatasource.recoverAll"
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
                list[0].codTurnEquip,
                1
            )
            assertEquals(
                list[0].idCheckList,
                1
            )
            assertEquals(
                list[0].typeEquip,
                1
            )
            assertEquals(
                list[0].hourMeter,
                100.0,
                0.0
            )
            assertEquals(
                list[0].classify,
                1
            )
            assertEquals(
                list[0].flagMechanic,
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
                list[1].codTurnEquip,
                2
            )
            assertEquals(
                list[1].idCheckList,
                2
            )
            assertEquals(
                list[1].typeEquip,
                2
            )
            assertEquals(
                list[1].hourMeter,
                200.0,
                0.0
            )
            assertEquals(
                list[1].classify,
                2
            )
            assertEquals(
                list[1].flagMechanic,
                0
            )
            server.shutdown()
        }
}

val resultEquipRetrofit = """
    [
      {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1},
      {"id":2,"nro":1000002,"codClass":2,"descrClass":"Classe 2","codTurnEquip":2,"idCheckList":2,"typeEquip":2,"hourmeter":200.0,"measurement":300.0,"type":2,"classify":2,"flagApontMecan":0,"flagApontPneu":0}
    ]
""".trimIndent()
