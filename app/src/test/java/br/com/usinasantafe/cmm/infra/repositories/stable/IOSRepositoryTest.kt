package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IOSRepositoryTest {

    private val osRoomDatasource = mock<OSRoomDatasource>()
    private val osRetrofitDatasource = mock<OSRetrofitDatasource>()
    private val repository = IOSRepository(
        osRetrofitDatasource = osRetrofitDatasource,
        osRoomDatasource = osRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                OSRoomModel(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                )
            )
            whenever(
                osRoomDatasource.addAll(roomModelList)
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
                "IOSRepository.addAll -> Unknown Error"
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
                OSRoomModel(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                )
            )
            whenever(
                osRoomDatasource.addAll(roomModelList)
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
                osRoomDatasource.deleteAll()
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
                "IOSRepository.deleteAll -> Unknown Error"
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
                osRoomDatasource.deleteAll()
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
                osRetrofitDatasource.recoverAll("token")
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
                "IOSRepository.recoverAll -> Unknown Error"
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
                OSRetrofitModel(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                ),
                OSRetrofitModel(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idProprAgr = 21,
                    areaProgrOS = 100.0,
                    tipoOS = 2,
                    idEquip = 31
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idProprAgr = 20,
                    areaProgrOS = 50.5,
                    tipoOS = 1,
                    idEquip = 30
                ),
                OS(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idProprAgr = 21,
                    areaProgrOS = 100.0,
                    tipoOS = 2,
                    idEquip = 31
                )
            )
            whenever(
                osRetrofitDatasource.recoverAll("token")
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
