package br.com.usinasantafe.cmm.external.retrofit.datasource.variable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.variable.MotoMecApi
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.HeaderMotoMecRetrofitModelOutput
import br.com.usinasantafe.cmm.infra.models.retrofit.variable.NoteMotoMecRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IMotoMecRetrofitDatasourceTest {

    @Test
    fun `send - Check return failure if have error 404`() =
        runTest {
            val noteMotoMecRetrofitModelOutput = NoteMotoMecRetrofitModelOutput(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                dateHour = "20/06/2025 14:34",
                statusCon = 1,
            )
            val headerMotoMecRetrofitModelOutput = HeaderMotoMecRetrofitModelOutput(
                id = 1,
                regOperator = 19759,
                idEquip = 1,
                typeEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 200000.0,
                dateHourInitial = "20/06/2025 14:34",
                number = 16997417840,
                status = 1,
                statusCon = 1,
                noteMotoMecList = listOf(noteMotoMecRetrofitModelOutput)
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(MotoMecApi::class.java)
            val dataSource = IMotoMecRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerMotoMecRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRetrofitDatasource.send"
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
            val noteMotoMecRetrofitModelOutput = NoteMotoMecRetrofitModelOutput(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                dateHour = "20/06/2025 14:34",
                statusCon = 1,
            )
            val headerMotoMecRetrofitModelOutput = HeaderMotoMecRetrofitModelOutput(
                id = 1,
                regOperator = 19759,
                idEquip = 1,
                typeEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 200000.0,
                dateHourInitial = "20/06/2025 14:34",
                number = 16997417840,
                status = 1,
                statusCon = 1,
                noteMotoMecList = listOf(noteMotoMecRetrofitModelOutput)
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(MotoMecApi::class.java)
            val dataSource = IMotoMecRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerMotoMecRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRetrofitDatasource.send"
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
            val noteMotoMecRetrofitModelOutput = NoteMotoMecRetrofitModelOutput(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                dateHour = "20/06/2025 14:34",
                statusCon = 1,
            )
            val headerMotoMecRetrofitModelOutput = HeaderMotoMecRetrofitModelOutput(
                id = 1,
                regOperator = 19759,
                idEquip = 1,
                typeEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 200000.0,
                dateHourInitial = "20/06/2025 14:34",
                number = 16997417840,
                status = 1,
                statusCon = 1,
                noteMotoMecList = listOf(noteMotoMecRetrofitModelOutput)
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"idServ":1,"id":"1a","noteMotoMecList":[{"idServ":1,"id":1}]}"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(MotoMecApi::class.java)
            val dataSource = IMotoMecRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerMotoMecRetrofitModelOutput)
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRetrofitDatasource.send"
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
            val noteMotoMecRetrofitModelOutput = NoteMotoMecRetrofitModelOutput(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                dateHour = "20/06/2025 14:34",
                statusCon = 1,
            )
            val headerMotoMecRetrofitModelOutput = HeaderMotoMecRetrofitModelOutput(
                id = 1,
                regOperator = 19759,
                idEquip = 1,
                typeEquip = 1,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 200000.0,
                dateHourInitial = "20/06/2025 14:34",
                number = 16997417840,
                status = 1,
                statusCon = 1,
                noteMotoMecList = listOf(noteMotoMecRetrofitModelOutput)
            )
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""[{"idServ":1,"id":1,"noteMotoMecList":[{"idServ":1,"id":1}]}]"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(MotoMecApi::class.java)
            val dataSource = IMotoMecRetrofitDatasource(service)
            val result = dataSource.send(
                token = "TOKEN",
                modelList = listOf(headerMotoMecRetrofitModelOutput)
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val headerModelInputList = result.getOrNull()!!
            assertEquals(
                headerModelInputList.size,
                1
            )
            val headModelInput = headerModelInputList[0]
            assertEquals(
                headModelInput.id,
                1
            )
            assertEquals(
                headModelInput.idServ,
                1
            )
            val noteModelInputList = headModelInput.noteMotoMecList[0]
            assertEquals(
                noteModelInputList.id,
                1
            )
            assertEquals(
                noteModelInputList.idServ,
                1
            )
            server.shutdown()
        }

}