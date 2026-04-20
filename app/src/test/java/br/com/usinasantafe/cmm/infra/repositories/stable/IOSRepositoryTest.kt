package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                )
            )
            whenever(
                osRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.addAll",
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
                "IOSRepository.addAll -> IOSRoomDatasource.addAll"
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
                OSRoomModel(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                )
            )
            whenever(
                osRoomDatasource.addAll(roomModelList)
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
                osRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.deleteAll",
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
                "IOSRepository.deleteAll -> IOSRoomDatasource.deleteAll"
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
                osRoomDatasource.deleteAll()
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
                osRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IOSRetrofitDatasource.listAll",
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
                "IOSRepository.listAll -> IOSRetrofitDatasource.listAll"
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
                OSRetrofitModel(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                ),
                OSRetrofitModel(
                    idOS = 2,
                    nroOS = 67890,
                    idReleaseOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0
                )
            )
            val entityList = listOf(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                ),
                OS(
                    idOS = 2,
                    nroOS = 67890,
                    idReleaseOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0
                )
            )
            whenever(
                osRetrofitDatasource.listAll("token")
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
    fun `hasByNroOS - Check return failure if have error in OSRoomDatasource checkNroOS`() =
        runTest {
            whenever(
                osRoomDatasource.hasByNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.hasByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasByNroOS(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.hasByNroOS -> IOSRoomDatasource.hasByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasByNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osRoomDatasource.hasByNroOS(123456)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasByNroOS(123456)
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
    fun `listByNroOS - Check return failure if have error in OSRetrofitDatasource getListByNroOS`() =
        runTest {
            whenever(
                osRetrofitDatasource.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IOSRetrofitDatasource.listByNroOS",
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
                "IOSRepository.listByNroOS -> IOSRetrofitDatasource.listByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByNroOS - Check return list empty if OSRetrofitDatasource getListByNroOS return empty list`() =
        runTest {
            whenever(
                osRetrofitDatasource.listByNroOS(
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
                emptyList<OS>()
            )
        }

    @Test
    fun `listByNroOS - Check return list if OSRetrofitDatasource getListByNroOS return list`() =
        runTest {
            whenever(
                osRetrofitDatasource.listByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        OSRetrofitModel(
                            idOS = 1,
                            nroOS = 12345,
                            idReleaseOS = 10,
                            idPropAgr = 20,
                            areaOS = 50.5
                        ),
                        OSRetrofitModel(
                            idOS = 2,
                            nroOS = 67890,
                            idReleaseOS = 11,
                            idPropAgr = 21,
                            areaOS = 100.0
                        ),
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
                entity1.nroOS,
                12345
            )
            assertEquals(
                entity1.idReleaseOS,
                10
            )
            assertEquals(
                entity1.idPropAgr,
                20
                )
            assertEquals(
                entity1.areaOS!!,
                50.5,
                0.0
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
                entity2.idReleaseOS,
                11
            )
            assertEquals(
                entity2.idPropAgr,
                21
            )
            assertEquals(
                entity2.areaOS!!,
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
                        idReleaseOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5
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
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
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
                        idReleaseOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5
                    )
                )
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.add(
                OS(
                    idOS = 1,
                    nroOS = 12345,
                    idReleaseOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5
                )
            )
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
    fun `getByNroOS - Check return failure if have error in OSRoomDatasource getByNroOS`() =
        runTest {
            whenever(
                osRoomDatasource.getByNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IOSRoomDatasource.getByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getByNroOS(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.getByNroOS -> IOSRoomDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getByNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osRoomDatasource.getByNroOS(123456)
            ).thenReturn(
                Result.success(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idReleaseOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5
                    )
                )
            )
            val result = repository.getByNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.idOS,
                1
            )
            assertEquals(
                entity.nroOS,
                123456
            )
            assertEquals(
                entity.idReleaseOS,
                10
            )
            assertEquals(
                entity.idPropAgr,
                20
            )
            assertEquals(
                entity.areaOS!!,
                50.5,
                0.0
            )
        }

}
