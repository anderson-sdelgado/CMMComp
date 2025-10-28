package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IREquipActivityRepositoryTest {

    private val rEquipActivityRoomDatasource = mock<REquipActivityRoomDatasource>()
    private val rEquipActivityRetrofitDatasource = mock<REquipActivityRetrofitDatasource>()
    private val repository = IREquipActivityRepository(
        rEquipActivityRetrofitDatasource = rEquipActivityRetrofitDatasource,
        rEquipActivityRoomDatasource = rEquipActivityRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                REquipActivityRoomModel(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                )
            )
            val entityList = listOf(
                REquipActivity(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                )
            )
            whenever(
                rEquipActivityRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    context = "IREquipActivityRoomDatasource.addAll",
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
                "IREquipActivityRepository.addAll -> IREquipActivityRoomDatasource.addAll"
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
                REquipActivityRoomModel(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                )
            )
            val entityList = listOf(
                REquipActivity(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                )
            )
            whenever(
                rEquipActivityRoomDatasource.addAll(roomModelList)
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
                rEquipActivityRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    context = "IREquipActivityRoomDatasource.deleteAll",
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
                "IREquipActivityRepository.deleteAll -> IREquipActivityRoomDatasource.deleteAll"
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
                rEquipActivityRoomDatasource.deleteAll()
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
    fun `listByIdEquip - Check return failure if have error`() =
        runTest {
            whenever(
                rEquipActivityRetrofitDatasource.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                resultFailure(
                    context = "IREquipActivityRetrofitDatasource.listByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.listByIdEquip(
                token = "token",
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IREquipActivityRepository.listByIdEquip -> IREquipActivityRetrofitDatasource.listByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdEquip - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                REquipActivityRetrofitModel(
                    idEquip = 10,
                    idActivity = 20
                ),
                REquipActivityRetrofitModel(
                    idEquip = 11,
                    idActivity = 21
                )
            )
            val entityList = listOf(
                REquipActivity(
                    idEquip = 10,
                    idActivity = 20
                ),
                REquipActivity(
                    idEquip = 11,
                    idActivity = 21
                )
            )
            whenever(
                rEquipActivityRetrofitDatasource.listByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listByIdEquip(
                token = "token",
                idEquip = 10
            )
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
    fun `listByIdEquip - Check return failure if have error in REquipActivityRoomDatasource getListByIdEquip`() =
        runTest {
            whenever(
                rEquipActivityRoomDatasource.listByIdEquip(
                    10
                )
            ).thenReturn(
                resultFailure(
                    context = "IREquipActivityRoomDatasource.listByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.listByIdEquip(
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IREquipActivityRepository.listByIdEquip -> IREquipActivityRoomDatasource.listByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rEquipActivityRoomDatasource.listByIdEquip(
                    10
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        REquipActivityRoomModel(
                            idREquipActivity = 1,
                            idEquip = 10,
                            idActivity = 20
                        )
                    )
                )
            )
            val result = repository.listByIdEquip(
                idEquip = 10
            )
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
                entity.idREquipActivity,
                1
            )
            assertEquals(
                entity.idEquip,
                10
            )
            assertEquals(
                entity.idActivity,
                20
            )
        }

}
