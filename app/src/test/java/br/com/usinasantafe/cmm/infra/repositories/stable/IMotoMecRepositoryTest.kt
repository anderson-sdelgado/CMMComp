package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.MotoMecRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.MotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.MotoMecRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.MotoMecRoomModel
import br.com.usinasantafe.cmm.utils.TypeOper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IMotoMecRepositoryTest {

    private val motoMecRoomDatasource = mock<MotoMecRoomDatasource>()
    private val motoMecRetrofitDatasource = mock<MotoMecRetrofitDatasource>()
    private val repository = IMotoMecRepository(
        motoMecRetrofitDatasource = motoMecRetrofitDatasource,
        motoMecRoomDatasource = motoMecRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                MotoMecRoomModel(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE.ordinal
                )
            )
            val entityList = listOf(
                MotoMec(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE
                )
            )
            whenever(
                motoMecRoomDatasource.addAll(roomModelList)
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
                "IMotoMecRepository.addAll -> Unknown Error"
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
                MotoMecRoomModel(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE.ordinal
                )
            )
            val entityList = listOf(
                MotoMec(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE
                )
            )
            whenever(
                motoMecRoomDatasource.addAll(roomModelList)
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
                motoMecRoomDatasource.deleteAll()
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
                "IMotoMecRepository.deleteAll -> Unknown Error"
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
                motoMecRoomDatasource.deleteAll()
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
                motoMecRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMotoMecRepository.recoverAll -> Unknown Error"
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
                MotoMecRetrofitModel(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE.ordinal
                ),
                MotoMecRetrofitModel(
                    idMotoMec = 2,
                    idOperMotoMec = 20,
                    descrOperMotoMec = "PARADA MANUTENCAO",
                    codFuncaoOperMotoMec = 201,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 2,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.PARADA.ordinal
                )
            )
            val entityList = listOf(
                MotoMec(
                    idMotoMec = 1,
                    idOperMotoMec = 10,
                    descrOperMotoMec = "ATIVIDADE AGRICOLA",
                    codFuncaoOperMotoMec = 101,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 1,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.ATIVIDADE
                ),
                MotoMec(
                    idMotoMec = 2,
                    idOperMotoMec = 20,
                    descrOperMotoMec = "PARADA MANUTENCAO",
                    codFuncaoOperMotoMec = 201,
                    posOperMotoMec = 1,
                    tipoOperMotoMec = 2,
                    aplicOperMotoMec = 1,
                    funcaoOperMotoMec = TypeOper.PARADA
                )
            )
            whenever(
                motoMecRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
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
