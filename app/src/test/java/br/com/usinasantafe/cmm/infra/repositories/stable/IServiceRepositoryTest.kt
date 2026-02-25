package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Service
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ServiceRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ServiceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ServiceRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IServiceRepositoryTest {

    private val serviceRetrofitDatasource = mock<ServiceRetrofitDatasource>()
    private val serviceRoomDatasource = mock<ServiceRoomDatasource>()
    private val repository = IServiceRepository(
        serviceRetrofitDatasource = serviceRetrofitDatasource,
        serviceRoomDatasource = serviceRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ServiceRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Service(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                serviceRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.addAll",
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
                "IServiceRepository.addAll -> IServiceRoomDatasource.addAll"
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
                ServiceRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Service(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                serviceRoomDatasource.addAll(roomModelList)
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
                serviceRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IServiceRoomDatasource.deleteAll",
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
                "IServiceRepository.deleteAll -> IServiceRoomDatasource.deleteAll"
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
                serviceRoomDatasource.deleteAll()
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
                serviceRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IServiceRetrofitDatasource.listAll",
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
                "IServiceRepository.listAll -> IServiceRetrofitDatasource.listAll"
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
                ServiceRetrofitModel(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Service(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            whenever(
                serviceRetrofitDatasource.listAll("token")
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

}