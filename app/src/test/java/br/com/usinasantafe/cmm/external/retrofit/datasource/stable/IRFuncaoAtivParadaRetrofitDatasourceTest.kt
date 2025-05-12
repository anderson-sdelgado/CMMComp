package br.com.usinasantafe.cmm.external.retrofit.datasource.stable

import br.com.usinasantafe.cmm.di.external.ApiModuleTest.provideRetrofitTest
import br.com.usinasantafe.cmm.external.retrofit.api.stable.RFuncaoAtivParadaApi
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RFuncaoAtivParadaRetrofitModel
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.TypeOper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IRFuncaoAtivParadaRetrofitDatasourceTest {

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
            val service = retrofit.create(RFuncaoAtivParadaApi::class.java)
            val datasource = IRFuncaoAtivParadaRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IRFuncaoAtivParadaRetrofitDatasource.recoverAll",
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
            val service = retrofit.create(RFuncaoAtivParadaApi::class.java)
            val datasource = IRFuncaoAtivParadaRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                "IRFuncaoAtivParadaRetrofitDatasource.recoverAll",
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
                MockResponse().setBody(resultRFuncaoAtivParadaRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(RFuncaoAtivParadaApi::class.java)
            val datasource = IRFuncaoAtivParadaRetrofitDatasource(service)
            val result = datasource.recoverAll("TOKEN")

            assertEquals(
                true,
                result.isSuccess
            )
            assertEquals(
                Result.success(
                    listOf(
                        RFuncaoAtivParadaRetrofitModel(
                            idRFuncaoAtivPar = 1,
                            idAtivPar = 10,
                            funcAtiv = 1,
                            funcParada = 1,
                            tipoFuncao = 1
                        ),
                        RFuncaoAtivParadaRetrofitModel(
                            idRFuncaoAtivPar = 2,
                            idAtivPar = 401,
                            funcAtiv = 1,
                            funcParada = 1,
                            tipoFuncao = 1
                        )
                    )
                ),
                result
            )
            server.shutdown()
        }
}

val resultRFuncaoAtivParadaRetrofit = """
    [
      {"idRFuncaoAtivPar":1,"idAtivPar":10,"funcAtiv":1,"funcParada":1,"tipoFuncao":1},
      {"idRFuncaoAtivPar":2,"idAtivPar":401,"funcAtiv":1,"funcParada":1,"tipoFuncao":1}
    ]
""".trimIndent()
