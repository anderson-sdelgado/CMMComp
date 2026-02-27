package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.ItemOSMechanicApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ItemOSMechanicRetrofitModelInput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import kotlin.test.assertEquals

class IItemOSMechanicRetrofitDatasourceTest {

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
            val service = retrofit.create(ItemOSMechanicApi::class.java)
            val datasource = IItemOSMechanicRetrofitDatasource(service)
            val result = datasource.listByIdEquipAndNroOS("TOKEN", 1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
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
            val service = retrofit.create(ItemOSMechanicApi::class.java)
            val datasource = IItemOSMechanicRetrofitDatasource(service)
            val result = datasource.listByIdEquipAndNroOS("TOKEN", 1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultItemOSMechanicRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ItemOSMechanicApi::class.java)
            val datasource = IItemOSMechanicRetrofitDatasource(service)
            val result = datasource.listByIdEquipAndNroOS("TOKEN", 1, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        ItemOSMechanicRetrofitModelInput(
                            id = 1,
                            nroOS = 10,
                            seqItem = 1,
                            idServ = 10,
                            idComp = 1
                        )
                    )
                )
            )
        }

    private val resultItemOSMechanicRetrofit = """
        [{"id":1,"nroOS":10,"seqItem":1,"idServ":10,"idComp":1}]
    """.trimIndent()
    
}