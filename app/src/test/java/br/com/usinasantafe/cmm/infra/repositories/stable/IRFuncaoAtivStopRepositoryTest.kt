package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RFuncaoAtivParadaRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RFuncaoAtivParadaRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RFuncaoAtivParadaRoomModel
import br.com.usinasantafe.cmm.utils.FuncAtividade
import br.com.usinasantafe.cmm.utils.FuncParada
import br.com.usinasantafe.cmm.utils.TypeOper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IRFuncaoAtivStopRepositoryTest {

    private val functionActivityStopRoomDatasource = mock<FunctionActivityStopRoomDatasource>()
    private val rFuncaoAtivParadaRetrofitDatasource = mock<RFuncaoAtivParadaRetrofitDatasource>()
    private val repository = IRFuncaoAtivParadaRepository(
        rFuncaoAtivParadaRetrofitDatasource = rFuncaoAtivParadaRetrofitDatasource,
        functionActivityStopRoomDatasource = functionActivityStopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RFuncaoAtivParadaRoomModel(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO.ordinal,
                    funcParada = FuncParada.CHECKLIST.ordinal,
                    tipoFuncao = TypeOper.ATIVIDADE.ordinal
                )
            )
            val entityList = listOf(
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO,
                    funcParada = FuncParada.CHECKLIST,
                    tipoFuncao = TypeOper.ATIVIDADE
                )
            )
            whenever(
                functionActivityStopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRFuncaoAtivParadaRepository.addAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                RFuncaoAtivParadaRoomModel(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO.ordinal,
                    funcParada = FuncParada.CHECKLIST.ordinal,
                    tipoFuncao = TypeOper.ATIVIDADE.ordinal
                )
            )
            val entityList = listOf(
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO,
                    funcParada = FuncParada.CHECKLIST,
                    tipoFuncao = TypeOper.ATIVIDADE
                )
            )
            whenever(
                functionActivityStopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                functionActivityStopRoomDatasource.deleteAll()
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRFuncaoAtivParadaRepository.deleteAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                functionActivityStopRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `recoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                rFuncaoAtivParadaRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRFuncaoAtivParadaRepository.recoverAll -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                RFuncaoAtivParadaRetrofitModel(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO.ordinal,
                    funcParada = FuncParada.CHECKLIST.ordinal,
                    tipoFuncao = TypeOper.ATIVIDADE.ordinal
                ),
                RFuncaoAtivParadaRetrofitModel(
                    idRFuncaoAtivPar = 2,
                    idAtivPar = 20,
                    funcAtiv = FuncAtividade.TRANSBORDO.ordinal,
                    funcParada = FuncParada.IMPLEMENTO.ordinal,
                    tipoFuncao = TypeOper.PARADA.ordinal
                )
            )
            val entityList = listOf(
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 1,
                    idAtivPar = 10,
                    funcAtiv = FuncAtividade.RENDIMENTO,
                    funcParada = FuncParada.CHECKLIST,
                    tipoFuncao = TypeOper.ATIVIDADE
                ),
                RFuncaoAtivParada(
                    idRFuncaoAtivPar = 2,
                    idAtivPar = 20,
                    funcAtiv = FuncAtividade.TRANSBORDO,
                    funcParada = FuncParada.IMPLEMENTO,
                    tipoFuncao = TypeOper.PARADA
                )
            )
            whenever(
                rFuncaoAtivParadaRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.recoverAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

}
