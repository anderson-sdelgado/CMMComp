package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class IOSRetrofitDatasourceTest {

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer();
        server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `listAll - Check return failure if token is invalid`() =
        runTest {
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
        }

    @Test
    fun `listAll - Check return failure if have Error 404`() =
        runTest {
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                        NullPointerException().toString()
            )
        }

    @Test
    fun `listAll - Check return correct - PMM`() =
        runTest {
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitPMM)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            id = 1,
                            nro = 12345,
                            idPropAgr = 20,
                            area = 150.75
                        )
                    )
                ),
                result
            )
        }

    @Test
    fun `listAll - Check return correct - ECM`() =
        runTest {
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitECM)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listAll("TOKEN")
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            id = 1,
                            nro = 12345,
                            idRelease = 10
                        )
                    )
                ),
                result
            )
        }

    @Test
    fun `listByNroOS - Check return failure if token is invalid`() =
        runTest {
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listByNro(
                token = "token",
                nro = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.listByNroOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                result.exceptionOrNull()!!.cause.toString()
            )
        }

    @Test
    fun `listByNroOS - Check return failure if have Error 404`() =
        runTest {
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listByNro(
                token = "token",
                nro = 123456
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IOSRetrofitDatasource.listByNroOS",
                result.exceptionOrNull()!!.message
            )
            assertEquals(
                NullPointerException().toString(),
                result.exceptionOrNull()!!.cause.toString()
            )
        }

    @Test
    fun `listByNroOS - Check return list empty`() =
        runTest {
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitEmptyList)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listByNro(
                token = "token",
                nro = 123456
            )
            assertEquals(
                result.isSuccess,
                true,
            )
            assertEquals(
                Result.success(
                    emptyList()
                ),
                result
            )
        }

    @Test
    fun `listByNroOS - Check return list - PMM`() =
        runTest {
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitPMM)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listByNro(
                token = "token",
                nro = 123456
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            id = 1,
                            nro = 12345,
                            idPropAgr = 20,
                            area = 150.75
                        )
                    )
                ),
                result
            )
        }

    @Test
    fun `listByNroOS - Check return list - ECM`() =
        runTest {
            server.enqueue(
                MockResponse().setBody(resultOSRetrofitECM)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service, service)
            val result = datasource.listByNro(
                token = "token",
                nro = 123456
            )
            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            id = 1,
                            nro = 12345,
                            idRelease = 10
                        )
                    )
                ),
                result
            )
        }

    private val resultOSRetrofitEmptyList = """
        []
    """.trimIndent()

    private val resultOSRetrofitPMM = """
        [
          {"idOS":1,"nroOS":12345,"idPropAgr":20,"areaOS":150.75}
        ]
    """.trimIndent()

    private val resultOSRetrofitECM = """
        [
          {"idOS":1,"nroOS":12345,"idReleaseOS":10}
        ]
    """.trimIndent()

}


