package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipActivityRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

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
                "IREquipAtivRepository.addAll -> Unknown Error"
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
                "IREquipAtivRepository.deleteAll -> Unknown Error"
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
    fun `getListByIdEquip - Check return failure if have error`() =
        runTest {
            whenever(
                rEquipActivityRetrofitDatasource.getListByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.failure(
                    Exception()
                )
            )
            val result = repository.getListByIdEquip(
                token = "token",
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IREquipActivityRepository.getListByIdEquip -> Unknown Error"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "null"
            )
        }

    @Test
    fun `getListByIdEquip - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                REquipActivityRetrofitModel(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                ),
                REquipActivityRetrofitModel(
                    idREquipActivity = 2,
                    idEquip = 11,
                    idActivity = 21
                )
            )
            val entityList = listOf(
                REquipActivity(
                    idREquipActivity = 1,
                    idEquip = 10,
                    idActivity = 20
                ),
                REquipActivity(
                    idREquipActivity = 2,
                    idEquip = 11,
                    idActivity = 21
                )
            )
            whenever(
                rEquipActivityRetrofitDatasource.getListByIdEquip(
                    token = "token",
                    idEquip = 10
                )
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.getListByIdEquip(
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
    fun `getListByIdEquip - Check return failure if have error in REquipActivityRoomDatasource getListByIdEquip`() =
        runTest {
            whenever(
                rEquipActivityRoomDatasource.getListByIdEquip(
                    10
                )
            ).thenReturn(
                resultFailure(
                    context = "IREquipActivityRoomDatasource.getListByIdEquip",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.getListByIdEquip(
                idEquip = 10
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IREquipActivityRepository.getListByIdEquip -> IREquipActivityRoomDatasource.getListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getListByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rEquipActivityRoomDatasource.getListByIdEquip(
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
            val result = repository.getListByIdEquip(
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
