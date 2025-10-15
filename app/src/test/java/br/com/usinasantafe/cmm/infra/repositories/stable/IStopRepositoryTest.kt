package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.StopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.StopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.StopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IStopRepositoryTest {

    private val stopRoomDatasource = mock<StopRoomDatasource>()
    private val stopRetrofitDatasource = mock<StopRetrofitDatasource>()
    private val repository = IStopRepository(
        stopRetrofitDatasource = stopRetrofitDatasource,
        stopRoomDatasource = stopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                stopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IStopRoomDatasource.addAll",
                    "-",
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
                "IStopRepository.addAll -> IStopRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                )
            )
            whenever(
                stopRoomDatasource.addAll(roomModelList)
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
                stopRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IStopRoomDatasource.addAll",
                    "-",
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
                "IStopRepository.deleteAll -> IStopRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                stopRoomDatasource.deleteAll()
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
                stopRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IStopRetrofitDatasource.recoverAll",
                    "-",
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
                "IStopRepository.recoverAll -> IStopRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `recoverAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                StopRetrofitModel(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                ),
                StopRetrofitModel(
                    idStop = 2,
                    codStop = 102,
                    descrStop = "ABASTECIMENTO"
                )
            )
            val entityList = listOf(
                Stop(
                    idStop = 1,
                    codStop = 101,
                    descrStop = "MANUTENCAO MECANICA"
                ),
                Stop(
                    idStop = 2,
                    codStop = 102,
                    descrStop = "ABASTECIMENTO"
                )
            )
            whenever(
                stopRetrofitDatasource.listAll("token")
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

    @Test
    fun `listByIdList - Check return failure if have error in StopRoomDatasource listByIdList`() =
        runTest {
            whenever(
                stopRoomDatasource.listByIdList(
                    listOf(1)
                )
            ).thenReturn(
                resultFailure(
                    context = "IStopRoomDatasource.listByIdList",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdList(listOf(1))
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IStopRepository.listByIdList -> IStopRoomDatasource.listByIdList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                stopRoomDatasource.listByIdList(
                    listOf(1, 2)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        StopRoomModel(
                            idStop = 1,
                            codStop = 101,
                            descrStop = "MANUTENCAO MECANICA"
                        ),
                        StopRoomModel(
                            idStop = 2,
                            codStop = 102,
                            descrStop = "ABASTECIMENTO"
                        )
                    )
                )
            )
            val result = repository.listByIdList(
                listOf(1, 2)
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idStop,
                1
            )
            assertEquals(
                entity1.codStop,
                101
            )
            assertEquals(
                entity1.descrStop,
                "MANUTENCAO MECANICA"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idStop,
                2
            )
            assertEquals(
                entity2.codStop,
                102
            )
            assertEquals(
                entity2.descrStop,
                "ABASTECIMENTO"
            )
        }

}
