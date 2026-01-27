package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ROSActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IROSActivityRepositoryTest {

    private val rOSActivityRoomDatasource = mock<ROSActivityRoomDatasource>()
    private val rOSActivityRetrofitDatasource = mock<ROSActivityRetrofitDatasource>()
    private val repository = IROSActivityRepository(
        rOSActivityRetrofitDatasource = rOSActivityRetrofitDatasource,
        rOSActivityRoomDatasource = rOSActivityRoomDatasource
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
                rOSActivityRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IROSActivityRoomDatasource.addAll",
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
                "IROSActivityRepository.addAll -> IROSActivityRoomDatasource.addAll"
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
                rOSActivityRoomDatasource.addAll(roomModelList)
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
                rOSActivityRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IROSActivityRoomDatasource.deleteAll",
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
                "IROSActivityRepository.deleteAll -> IROSActivityRoomDatasource.deleteAll"
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
                rOSActivityRoomDatasource.deleteAll()
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
    fun `recoverAll - Check return failure if have error`() =
        runTest {
            whenever(
                rOSActivityRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IROSActivityRetrofitDatasource.listAll",
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
                "IROSActivityRepository.listAll -> IROSActivityRetrofitDatasource.listAll"
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
                ROSActivityRetrofitModel(
                    idOS = 10,
                    idActivity = 20
                ),
                ROSActivityRetrofitModel(
                    idOS = 11,
                    idActivity = 21
                )
            )
            val entityList = listOf(
                ROSActivity(
                    idOS = 10,
                    idActivity = 20
                ),
                ROSActivity(
                    idOS = 11,
                    idActivity = 21
                )
            )
            whenever(
                rOSActivityRetrofitDatasource.listAll("token")
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
    fun `getListByNroOS - Check return failure if have error in ROSActivityRetrofitDatasource getListByNroOS`() =
        runTest {
            whenever(
                rOSActivityRetrofitDatasource.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IROSActivityRetrofitDatasource.listByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSActivityRepository.listByNroOS -> IROSActivityRetrofitDatasource.listByNroOS"
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
                rOSActivityRetrofitDatasource.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(emptyList())
            )
            val result = repository.listByNroOS(
                token = "token",
                nroOS = 123456
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }
    @Test
    fun `getListByNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rOSActivityRetrofitDatasource.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSActivityRetrofitModel(
                            idOS = 1,
                            idActivity = 10
                        ),
                        ROSActivityRetrofitModel(
                            idOS = 2,
                            idActivity = 11
                        )
                    )
                )
            )
            val result = repository.listByNroOS(
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
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.idActivity,
                10
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.idActivity,
                11
            )
        }

    @Test
    fun `getListByIdOS - Check return failure if have error in rOSActivityRoomDatasource getListByIdOS`() =
        runTest {
            whenever(
                rOSActivityRoomDatasource.listByIdOS(1)
            ).thenReturn(
                resultFailure(
                    "IROSActivityRoomDatasource.listByIdOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdOS(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSActivityRepository.listByIdOS -> IROSActivityRoomDatasource.listByIdOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getListByIdOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rOSActivityRoomDatasource.listByIdOS(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        ROSActivityRoomModel(
                            idROSActivity = 1,
                            idOS = 1,
                            idActivity = 10
                        )
                    )
                )
            )
            val result = repository.listByIdOS(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
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

        }

}
