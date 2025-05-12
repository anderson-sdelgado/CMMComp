package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSAtivRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IROSActivityRepositoryTest {

    private val rosActivityRoomDatasource = mock<ROSActivityRoomDatasource>()
    private val rosActivityRetrofitDatasource = mock<ROSActivityRetrofitDatasource>()
    private val repository = IROSActivityRepository(
        rosActivityRetrofitDatasource = rosActivityRetrofitDatasource,
        rosActivityRoomDatasource = rosActivityRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ROSActivityRoomModel(
                    idROSActivity = 1,
                    idOS = 10,
                    idActivity = 20
                )
            )
            val entityList = listOf(
                ROSActivity(
                    idROSActivity = 1,
                    idOS = 10,
                    idActivity = 20
                )
            )
            whenever(
                rosActivityRoomDatasource.addAll(roomModelList)
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
                "IROSAtivRepository.addAll -> Unknown Error"
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
                ROSActivityRoomModel(
                    idROSActivity = 1,
                    idOS = 10,
                    idActivity = 20
                )
            )
            val entityList = listOf(
                ROSActivity(
                    idROSActivity = 1,
                    idOS = 10,
                    idActivity = 20
                )
            )
            whenever(
                rosActivityRoomDatasource.addAll(roomModelList)
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
                rosActivityRoomDatasource.deleteAll()
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
                "IROSAtivRepository.deleteAll -> Unknown Error"
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
                rosActivityRoomDatasource.deleteAll()
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
                rosActivityRetrofitDatasource.recoverAll("token")
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
                "IROSAtivRepository.recoverAll -> Unknown Error"
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
                ROSAtivRetrofitModel(
                    idROSAtiv = 1,
                    idOS = 10,
                    idAtiv = 20
                ),
                ROSAtivRetrofitModel(
                    idROSAtiv = 2,
                    idOS = 11,
                    idAtiv = 21
                )
            )
            val entityList = listOf(
                ROSActivity(
                    idROSActivity = 1,
                    idOS = 10,
                    idActivity = 20
                ),
                ROSActivity(
                    idROSActivity = 2,
                    idOS = 11,
                    idActivity = 21
                )
            )
            whenever(
                rosActivityRetrofitDatasource.recoverAll("token")
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
    fun `getListByNroOS - Check return failure if have error in ROSActivityRetrofitDatasource getListByNroOS`() =
        runTest {
            whenever(
                rosActivityRetrofitDatasource.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IROSAtivRetrofitDatasource.getListByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getListByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSAtivRepository.getListByNroOS -> IROSAtivRetrofitDatasource.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    @Test
    fun `getListByNroOS - Check return list empty if OSRetrofitDatasource getListByNroOS return empty list`() =
        runTest {
            whenever(
                rosActivityRetrofitDatasource.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = repository.getListByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<OS>()
            )
        }
    @Test
    fun `getListByNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rosActivityRetrofitDatasource.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSAtivRetrofitModel(
                            idROSAtiv = 1,
                            idOS = 1,
                            idAtiv = 10
                        ),
                        ROSAtivRetrofitModel(
                            idROSAtiv = 2,
                            idOS = 2,
                            idAtiv = 11
                        )
                    )
                )
            )
            val result = repository.getListByNroOS(
                token = "token",
                nroOS = 123456
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
                entity1.idROSActivity,
                1
            )
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.idActivity,
                10
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idROSActivity,
                2
            )
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.idActivity,
                11
            )
        }

}
