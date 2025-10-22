package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IActivityRepositoryTest {

    private val activityRoomDatasource = mock<ActivityRoomDatasource>()
    private val activityRetrofitDatasource = mock<ActivityRetrofitDatasource>()
    private val repository = IActivityRepository(
        activityRetrofitDatasource = activityRetrofitDatasource,
        activityRoomDatasource = activityRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ActivityRoomModel(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "TEST"
                )
            )
            val entityList = listOf(
                Activity(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "TEST"
                )
            )
            whenever(
                activityRoomDatasource.addAll(roomModelList)
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
                "IActivityRepository.addAll -> Unknown Error"
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
                ActivityRoomModel(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "TEST"
                )
            )
            val entityList = listOf(
                Activity(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "TEST"
                )
            )
            whenever(
                activityRoomDatasource.addAll(roomModelList)
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
                activityRoomDatasource.deleteAll()
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
                "IActivityRepository.deleteAll -> Unknown Error"
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
                activityRoomDatasource.deleteAll()
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
                activityRetrofitDatasource.listAll("token")
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
                "IActivityRepository.recoverAll -> Unknown Error"
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
                ActivityRetrofitModel(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Activity(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "ATIVIDADE"
                )
            )
            whenever(
                activityRetrofitDatasource.listAll("token")
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
    fun `listByIdList - Check return failure if have error in ActivityRoomDatasource listByIdList`() =
        runTest {
            whenever(
                activityRoomDatasource.listByIdList(
                    listOf(1)
                )
            ).thenReturn(
                resultFailure(
                    context = "IActivityRoomDatasource.listByIdList",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.listByIdList(listOf(1))
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IActivityRepository.listByIdList -> IActivityRoomDatasource.listByIdList"
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
                activityRoomDatasource.listByIdList(
                    listOf(1, 2)
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ActivityRoomModel(
                            idActivity = 1,
                            codActivity = 10,
                            descrActivity = "ATIVIDADE"
                        ),
                        ActivityRoomModel(
                            idActivity = 2,
                            codActivity = 20,
                            descrActivity = "ATIVIDADE 2"
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
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                10
            )
            assertEquals(
                entity1.descrActivity,
                "ATIVIDADE"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                2
            )
            assertEquals(
                entity2.codActivity,
                20
            )
            assertEquals(
                entity2.descrActivity,
                "ATIVIDADE 2"
            )
        }

    @Test
    fun `getById - Check return failure if have error in ActivityRoomDatasource getById`() =
        runTest {
            whenever(
                activityRoomDatasource.getById(1)
            ).thenReturn(
                resultFailure(
                    "IActivityRoomDatasource.getById",
                    "-",
                    Exception()
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IActivityRepository.getById -> IActivityRoomDatasource.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                activityRoomDatasource.getById(1)
            ).thenReturn(
                Result.success(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE"
                    )
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Activity(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "ATIVIDADE"
                )
            )
        }

}