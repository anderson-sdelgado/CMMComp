package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.utils.TypeStop
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IFunctionStopRepositoryTest {

    private val functionStopRoomDatasource = mock<FunctionStopRoomDatasource>()
    private val functionStopRetrofitDatasource = mock<FunctionStopRetrofitDatasource>()
    private val repository = IFunctionStopRepository(
        functionStopRetrofitDatasource = functionStopRetrofitDatasource,
        functionStopRoomDatasource = functionStopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                FunctionStopRoomModel(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.CHECKLIST,
                )
            )
            val entityList = listOf(
                FunctionStop(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.CHECKLIST,
                )
            )
            whenever(
                functionStopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IFunctionStopRoomDatasource.addAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionStopRepository.addAll -> IFunctionStopRoomDatasource.addAll"
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
                FunctionStopRoomModel(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.CHECKLIST,
                )
            )
            val entityList = listOf(
                FunctionStop(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.CHECKLIST,
                )
            )
            whenever(
                functionStopRoomDatasource.addAll(roomModelList)
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
                functionStopRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IFunctionStopRoomDatasource.deleteAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionStopRepository.deleteAll -> IFunctionStopRoomDatasource.deleteAll"
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
                functionStopRoomDatasource.deleteAll()
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
                functionStopRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IFunctionStopRetrofitDatasource.listAll",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionStopRepository.listAll -> IFunctionStopRetrofitDatasource.listAll"
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
                FunctionStopRetrofitModel(
                    idRFunctionStop = 1,
                    idStop = 1,
                    typeStop = 1,
                ),
                FunctionStopRetrofitModel(
                    idRFunctionStop = 2,
                    idStop = 2,
                    typeStop = 2,
                )
            )
            val entityList = listOf(
                FunctionStop(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.CHECKLIST,
                ),
                FunctionStop(
                    idFunctionStop = 2,
                    idStop = 2,
                    typeStop = TypeStop.IMPLEMENT,
                )
            )
            whenever(
                functionStopRetrofitDatasource.listAll("token")
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