package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.REquipActivityApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test

class IREquipActivityRetrofitDatasourceTest {

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
            val service = retrofit.create(REquipActivityApi::class.java)
            val datasource = IREquipActivityRetrofitDatasource(service)
            val result = datasource.getListByIdEquip(
                token = "token",
                idEquip = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IREquipActivityRetrofitDatasource.getListByIdEquip",
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
            val service = retrofit.create(REquipActivityApi::class.java)
            val datasource = IREquipActivityRetrofitDatasource(service)
            val result = datasource.getListByIdEquip(
                token = "token",
                idEquip = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IREquipActivityRetrofitDatasource.getListByIdEquip",
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
                MockResponse().setBody(resultREquipActivityRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(REquipActivityApi::class.java)
            val datasource = IREquipActivityRetrofitDatasource(service)
            val result = datasource.getListByIdEquip(
                token = "token",
                idEquip = 1
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        REquipActivityRetrofitModel(
                            idREquipActivity = 1,
                            idEquip = 30,
                            idActivity = 10
                        ),
                        REquipActivityRetrofitModel(
                            idREquipActivity = 2,
                            idEquip = 31,
                            idActivity = 11
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

    private val resultREquipActivityRetrofit = """
        [
          {"idREquipActivity":1,"idEquip":30,"idActivity":10},
          {"idREquipActivity":2,"idEquip":31,"idActivity":11}
        ]
    """.trimIndent()

}


