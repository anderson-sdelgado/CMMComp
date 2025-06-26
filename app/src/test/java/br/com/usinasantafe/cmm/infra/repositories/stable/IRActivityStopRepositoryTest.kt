package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RActivityStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RActivityStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IRActivityStopRepositoryTest {

    private val rActivityStopRoomDatasource = mock<RActivityStopRoomDatasource>()
    private val rActivityStopRetrofitDatasource = mock<RActivityStopRetrofitDatasource>()
    private val repository = IRActivityStopRepository(
        rActivityStopRetrofitDatasource = rActivityStopRetrofitDatasource,
        rActivityStopRoomDatasource = rActivityStopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                RActivityStopRoomModel(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            whenever(
                rActivityStopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IRActivityStopRoomDatasource.addAll",
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
                "IRActivityStopRepository.addAll -> IRActivityStopRoomDatasource.addAll"
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
                RActivityStopRoomModel(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idRActivityStop = 1,
                    idActivity = 10,
                    idStop = 20
                )
            )
            whenever(
                rActivityStopRoomDatasource.addAll(roomModelList)
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
                rActivityStopRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IRActivityStopRoomDatasource.deleteAll",
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
                "IRActivityStopRepository.deleteAll -> IRActivityStopRoomDatasource.deleteAll"
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
                rActivityStopRoomDatasource.deleteAll()
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
                rActivityStopRetrofitDatasource.recoverAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IRActivityStopRetrofitDatasource.recoverAll",
                    "-",
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
                "IRActivityStopRepository.recoverAll -> IRActivityStopRetrofitDatasource.recoverAll"
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
                RActivityStopRetrofitModel(
                    idActivity = 10,
                    idStop = 20
                ),
                RActivityStopRetrofitModel(
                    idActivity = 11,
                    idStop = 21
                )
            )
            val entityList = listOf(
                RActivityStop(
                    idActivity = 10,
                    idStop = 20
                ),
                RActivityStop(
                    idActivity = 11,
                    idStop = 21
                )
            )
            whenever(
                rActivityStopRetrofitDatasource.recoverAll("token")
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

    @Test
    fun `listByIdActivity - Check return failure if have error in RActivityStopRoomDatasource listByIdActivity`() =
        runTest {
            whenever(
                rActivityStopRoomDatasource.listByIdActivity(10)
            ).thenReturn(
                resultFailure(
                    context = "IRActivityStopRoomDatasource.listByIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdActivity(10)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRActivityStopRepository.listByIdActivity -> IRActivityStopRoomDatasource.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdActivity - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rActivityStopRoomDatasource.listByIdActivity(10)
            ).thenReturn(
                Result.success(
                    listOf(
                        RActivityStopRoomModel(
                            idRActivityStop = 1,
                            idActivity = 10,
                            idStop = 20
                        )
                    )
                )
            )
            val result = repository.listByIdActivity(10)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list.first()
            assertEquals(
                entity.idRActivityStop,
                1
            )
            assertEquals(
                entity.idActivity,
                10
            )
            assertEquals(
                entity.idStop,
                20
            )
        }
    
}
