package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipPneuApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipPneuRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IREquipPneuRetrofitDatasourceTest {

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
            val service = retrofit.create(REquipPneuApi::class.java)
            val datasource = IREquipPneuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IREquipPneuRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(REquipPneuApi::class.java)
            val datasource = IREquipPneuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IREquipPneuRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultREquipPneuRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(REquipPneuApi::class.java)
            val datasource = IREquipPneuRetrofitDatasource(service)
            val result = datasource.listAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        REquipPneuRetrofitModel(
                            idREquipPneu = 1,
                            idEquip = 30,
                            idPosConfPneu = 1,
                            posPneu = 1,
                            statusPneu = 1
                        ),
                        REquipPneuRetrofitModel(
                            idREquipPneu = 2,
                            idEquip = 30,
                            idPosConfPneu = 2,
                            posPneu = 2,
                            statusPneu = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultREquipPneuRetrofit = """
    [
      {"idREquipPneu":1,"idEquip":30,"idPosConfPneu":1,"posPneu":1,"statusPneu":1},
      {"idREquipPneu":2,"idEquip":30,"idPosConfPneu":2,"posPneu":2,"statusPneu":1}
    ]
""".trimIndent()
