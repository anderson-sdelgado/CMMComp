package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.FunctionActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IFunctionActivityRepositoryTest {

    private val functionActivityRoomDatasource = mock<FunctionActivityRoomDatasource>()
    private val functionActivityRetrofitDatasource = mock<FunctionActivityRetrofitDatasource>()
    private val repository = IFunctionActivityRepository(
        functionActivityRetrofitDatasource = functionActivityRetrofitDatasource,
        functionActivityRoomDatasource = functionActivityRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            val entityList = listOf(
                FunctionActivity(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            whenever(
                functionActivityRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IFunctionActivityRoomDatasource.addAll",
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
                "IFunctionActivityRepository.addAll -> IFunctionActivityRoomDatasource.addAll"
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
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            val entityList = listOf(
                FunctionActivity(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            whenever(
                functionActivityRoomDatasource.addAll(roomModelList)
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
                functionActivityRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IFunctionActivityRoomDatasource.deleteAll",
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
                "IFunctionActivityRepository.deleteAll -> IFunctionActivityRoomDatasource.deleteAll"
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
                functionActivityRoomDatasource.deleteAll()
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
                functionActivityRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    context = "IFunctionActivityRetrofitDatasource.listAll",
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
                "IFunctionActivityRepository.listAll -> IFunctionActivityRetrofitDatasource.listAll"
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
                FunctionActivityRetrofitModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = 1,
                ),
                FunctionActivityRetrofitModel(
                    idFunctionActivity = 2,
                    idActivity = 2,
                    typeActivity = 2,
                )
            )
            val entityList = listOf(
                FunctionActivity(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                ),
                FunctionActivity(
                    idFunctionActivity = 2,
                    idActivity = 2,
                    typeActivity = TypeActivity.TRANSHIPMENT,
                )
            )
            whenever(
                functionActivityRetrofitDatasource.listAll("token")
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
    fun `listByIdActivity - Check return failure if have error in FunctionActivityRoomDatasource listByIdActivity`() =
        runTest {
            whenever(
                functionActivityRoomDatasource.listByIdActivity(1)
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRoomDatasource.listByIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdActivity(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionActivityRepository.listByIdActivity -> IFunctionActivityRoomDatasource.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdActivity - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            val entityList = listOf(
                FunctionActivity(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE,
                )
            )
            whenever(
                functionActivityRoomDatasource.listByIdActivity(1)
            ).thenReturn(
                Result.success(roomModelList)
            )
            val result = repository.listByIdActivity(1)
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