package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipAtiv
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipAtivRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipAtivRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.REquipAtivRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipAtivRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IREquipAtivRepositoryTest {

    private val rEquipAtivRoomDatasource = mock<REquipAtivRoomDatasource>()
    private val rEquipAtivRetrofitDatasource = mock<REquipAtivRetrofitDatasource>()
    private val repository = IREquipAtivRepository(
        rEquipAtivRetrofitDatasource = rEquipAtivRetrofitDatasource,
        rEquipAtivRoomDatasource = rEquipAtivRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                REquipAtivRoomModel(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                )
            )
            val entityList = listOf(
                REquipAtiv(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                )
            )
            whenever(
                rEquipAtivRoomDatasource.addAll(roomModelList)
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
                REquipAtivRoomModel(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                )
            )
            val entityList = listOf(
                REquipAtiv(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                )
            )
            whenever(
                rEquipAtivRoomDatasource.addAll(roomModelList)
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
                rEquipAtivRoomDatasource.deleteAll()
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
                rEquipAtivRoomDatasource.deleteAll()
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
                rEquipAtivRetrofitDatasource.recoverAll("token")
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
                "IREquipAtivRepository.recoverAll -> Unknown Error"
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
                REquipAtivRetrofitModel(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                ),
                REquipAtivRetrofitModel(
                    idREquipAtiv = 2,
                    idEquip = 11,
                    idAtiv = 21
                )
            )
            val entityList = listOf(
                REquipAtiv(
                    idREquipAtiv = 1,
                    idEquip = 10,
                    idAtiv = 20
                ),
                REquipAtiv(
                    idREquipAtiv = 2,
                    idEquip = 11,
                    idAtiv = 21
                )
            )
            whenever(
                rEquipAtivRetrofitDatasource.recoverAll("token")
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

}
