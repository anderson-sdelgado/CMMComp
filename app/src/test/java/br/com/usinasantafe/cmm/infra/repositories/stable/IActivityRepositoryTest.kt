package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Activity(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                activityRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IActivityRoomDatasource.addAll",
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
                "IActivityRepository.addAll -> IActivityRoomDatasource.addAll"
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
                ActivityRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Activity(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                activityRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                activityRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IActivityRoomDatasource.deleteAll",
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
                "IActivityRepository.deleteAll -> IActivityRoomDatasource.deleteAll"
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
                activityRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

    @Test
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                activityRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IActivityRetrofitDatasource.listAll",
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
                "IActivityRepository.listAll -> IActivityRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                ActivityRetrofitModel(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Activity(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
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
                            id = 1,
                            cod = 10,
                            descr = "ATIVIDADE"
                        ),
                        ActivityRoomModel(
                            id = 2,
                            cod = 20,
                            descr = "ATIVIDADE 2"
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
                entity1.id,
                1
            )
            assertEquals(
                entity1.cod,
                10
            )
            assertEquals(
                entity1.descr,
                "ATIVIDADE"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.cod,
                20
            )
            assertEquals(
                entity2.descr,
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
                        id = 1,
                        cod = 10,
                        descr = "ATIVIDADE"
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
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
        }

}