package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Nozzle
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.NozzleRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.NozzleRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.NozzleRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class INozzleRepositoryTest {

    private val nozzleRetrofitDatasource = mock<NozzleRetrofitDatasource>()
    private val nozzleRoomDatasource = mock<NozzleRoomDatasource>()
    private val repository = INozzleRepository(
        nozzleRetrofitDatasource = nozzleRetrofitDatasource,
        nozzleRoomDatasource = nozzleRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                NozzleRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Nozzle(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                nozzleRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "INozzleRoomDatasource.addAll",
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
                "INozzleRepository.addAll -> INozzleRoomDatasource.addAll"
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
                NozzleRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Nozzle(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                nozzleRoomDatasource.addAll(roomModelList)
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
                nozzleRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "INozzleRoomDatasource.deleteAll",
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
                "INozzleRepository.deleteAll -> INozzleRoomDatasource.deleteAll"
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
                nozzleRoomDatasource.deleteAll()
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
                nozzleRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "INozzleRetrofitDatasource.listAll",
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
                "INozzleRepository.listAll -> INozzleRetrofitDatasource.listAll"
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
                NozzleRetrofitModel(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Nozzle(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            whenever(
                nozzleRetrofitDatasource.listAll("token")
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
    fun `listAll - Check return failure if have error in NozzleRoomDatasource listAll`() =
        runTest {
            whenever(
                nozzleRoomDatasource.listAll()
            ).thenReturn(
                resultFailure(
                    "INozzleRoomDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INozzleRepository.listAll -> INozzleRoomDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                nozzleRoomDatasource.listAll()
            ).thenReturn(
                Result.success(
                    listOf(
                        NozzleRoomModel(
                            id = 1,
                            cod = 10,
                            descr = "TEST"
                        )
                    )
                )
            )
            val result = repository.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Nozzle(
                        id = 1,
                        cod = 10,
                        descr = "TEST"
                    )
                )
            )
        }

}