package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.variable.CheckListApi
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderCheckListRetrofitModelOutput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.RespItemCheckListRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class ICheckListRetrofitDatasourceTest {

    private val respItemCheckListRetrofitModelOutput =
        RespItemCheckListRetrofitModelOutput(
            id = 1,
            idHeader = 1,
            idItem = 1,
            option = 1
        )

    private val headerCheckListRetrofitModelOutput =
        HeaderCheckListRetrofitModelOutput(
            id = 1,
            nroEquip = 2200,
            regOperator = 19759,
            nroTurn = 2,
            dateHour = "20/06/2025 14:34",
            number = 16997417840,
            respItemList = listOf(
                respItemCheckListRetrofitModelOutput
            )
        )

    @Test
    fun `send - Check return failure if have error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerCheckListRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if have failure Connection`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerCheckListRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if sent data incorrect`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"idServ":1,"id":"1a","respItemList":[{"idServ":1,"id":1}]}"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerCheckListRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if sent data incorrect - return only one item`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"idServ":1,"id":1,"respItemList":[{"idServ":1,"id":1}]}"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerCheckListRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckListRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""[{"idServ":1,"id":1,"respItemList":[{"idServ":1,"id":1}]}]"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(CheckListApi::class.java)
            val dataSource = ICheckListRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerCheckListRetrofitModelOutput)
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val headerList = result.getOrNull()!!
            assertEquals(
                headerList.size,
                1
            )
            val headerModel = headerList[0]
            assertEquals(
                headerModel.id,
                1
            )
            assertEquals(
                headerModel.idServ,
                1
            )
            val respItemList = headerModel.respItemList
            assertEquals(
                respItemList.size,
                1
            )
            val respItemModel = respItemList[0]
            assertEquals(
                respItemModel.id,
                1
            )
            assertEquals(
                respItemModel.idServ,
                1
            )
            server.shutdown()
        }
}