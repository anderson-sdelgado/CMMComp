package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IOSRetrofitDatasourceTest {

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
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.recoverAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.recoverAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultOSRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.recoverAll("TOKEN")
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            idOS = 1,
                            nroOS = 12345,
                            idLibOS = 10,
                            idPropAgr = 20,
                            areaOS = 150.75,
                            idEquip = 30
                        ),
                        OSRetrofitModel(
                            idOS = 2,
                            nroOS = 67890,
                            idLibOS = 11,
                            idPropAgr = 21,
                            areaOS = 200.0,
                            idEquip = 31
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
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.getListByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.getListByNroOS",
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
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.getListByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.getListByNroOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
            server.shutdown()
        }

    @Test
    fun `getListByNroOS - Check return list empty`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitEmptyList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.getListByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    emptyList<OSRetrofitModel>()
                ),
                result
            )
            server.shutdown()
        }

    @Test
    fun `getListByNroOS - Check return list`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitOne)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.getListByNroOS(
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
                        OSRetrofitModel(
                            idOS = 1,
                            nroOS = 12345,
                            idLibOS = 10,
                            idPropAgr = 20,
                            areaOS = 150.75,
                            idEquip = 30
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }

    private val resultOSRetrofit = """
        [
          {"idOS":1,"nroOS":12345,"idLibOS":10,"idProprAgr":20,"areaProgrOS":150.75,"tipoOS":1,"idEquip":30},
          {"idOS":2,"nroOS":67890,"idLibOS":11,"idProprAgr":21,"areaProgrOS":200.0,"tipoOS":2,"idEquip":31}
        ]
    """.trimIndent()

    private val resultOSRetrofitEmptyList = """
        []
    """.trimIndent()

    private val resultOSRetrofitOne = """
        [
          {"idOS":1,"nroOS":12345,"idLibOS":10,"idProprAgr":20,"areaProgrOS":150.75,"tipoOS":1,"idEquip":30}
        ]
    """.trimIndent()

}


