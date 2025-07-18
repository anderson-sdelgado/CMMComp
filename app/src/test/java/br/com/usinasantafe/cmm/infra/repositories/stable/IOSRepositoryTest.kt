package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.errors.resultFailure
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
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
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
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
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
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                ),
                OSRetrofitModel(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0,
                    idEquip = 31
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                ),
                OS(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0,
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

    @Test
    fun `checkNroOS - Check return failure if have error in OSRoomDatasource checkNroOS`() =
        runTest {
            whenever(
                osRoomDatasource.checkNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.checkNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkNroOS(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.checkNroOS -> IOSRoomDatasource.checkNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osRoomDatasource.checkNroOS(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkNroOS(123456)
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
    fun `getListByNroOS - Check return failure if have error in OSRetrofitDatasource getListByNroOS`() =
        runTest {
            whenever(
                osRetrofitDatasource.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IOSRetrofitDatasource.getListByNroOS",
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
                "IOSRepository.getListByNroOS -> IOSRetrofitDatasource.getListByNroOS"
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
                osRetrofitDatasource.getListByNroOS(
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
    fun `getListByNroOS - Check return list if OSRetrofitDatasource getListByNroOS return list`() =
        runTest {
            whenever(
                osRetrofitDatasource.getListByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            idOS = 1,
                            nroOS = 12345,
                            idLibOS = 10,
                            idPropAgr = 20,
                            areaOS = 50.5,
                            idEquip = 30
                        ),
                        OSRetrofitModel(
                            idOS = 2,
                            nroOS = 67890,
                            idLibOS = 11,
                            idPropAgr = 21,
                            areaOS = 100.0,
                            idEquip = 31
                        ),
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
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.nroOS,
                12345
            )
            assertEquals(
                entity1.idLibOS,
                10
            )
            assertEquals(
                entity1.idPropAgr,
                20
                )
            assertEquals(
                entity1.areaOS,
                50.5,
                0.0
            )
            assertEquals(
                entity1.idEquip,
                30
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.nroOS,
                67890
            )
            assertEquals(
                entity2.idLibOS,
                11
            )
            assertEquals(
                entity2.idPropAgr,
                21
            )
            assertEquals(
                entity2.areaOS,
                100.0,
                0.0
            )
        }

    @Test
    fun `add - Check return failure if have error in OSRoomDatasource add`() =
        runTest {
            whenever(
                osRoomDatasource.add(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.add",
                    "-",
                    Exception()
                )
            )
            val result = repository.add(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.add -> IOSRoomDatasource.add"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `add - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osRoomDatasource.add(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                )
            )
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
    fun `getListByNroOS - Check return failure if have error in OSRoomDatasource getListByNroOS`() =
        runTest {
            whenever(
                osRoomDatasource.listByNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.getListByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByNroOS(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.listByNroOS -> IOSRoomDatasource.getListByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getListByNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osRoomDatasource.listByNroOS(123456)
            ).thenReturn(
                Result.success(
                    listOf(
                        OSRoomModel(
                            idOS = 1,
                            nroOS = 123456,
                            idLibOS = 10,
                            idPropAgr = 20,
                            areaOS = 50.5,
                            idEquip = 3
                        )
                    )
                )
            )
            val result = repository.listByNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idOS,
                1
            )
            assertEquals(
                entity.nroOS,
                123456
            )
            assertEquals(
                entity.idLibOS,
                10
            )
            assertEquals(
                entity.idPropAgr,
                20
            )
            assertEquals(
                entity.areaOS,
                50.5,
                0.0
            )
            assertEquals(
                entity.idEquip,
                3
            )
        }

}
