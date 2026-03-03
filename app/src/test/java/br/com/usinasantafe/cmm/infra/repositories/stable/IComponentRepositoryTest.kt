package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Component
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ComponentRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ComponentRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.ComponentRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IComponentRepositoryTest {

    private val componentRetrofitDatasource = mock<ComponentRetrofitDatasource>()
    private val componentRoomDatasource = mock<ComponentRoomDatasource>()
    private val repository = IComponentRepository(
        componentRetrofitDatasource = componentRetrofitDatasource,
        componentRoomDatasource = componentRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ComponentRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Component(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                componentRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.addAll",
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
                "IComponentRepository.addAll -> IComponentRoomDatasource.addAll"
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
                ComponentRoomModel(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            val entityList = listOf(
                Component(
                    id = 1,
                    cod = 10,
                    descr = "TEST"
                )
            )
            whenever(
                componentRoomDatasource.addAll(roomModelList)
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
                componentRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.deleteAll",
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
                "IComponentRepository.deleteAll -> IComponentRoomDatasource.deleteAll"
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
                componentRoomDatasource.deleteAll()
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
                componentRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IComponentRetrofitDatasource.listAll",
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
                "IComponentRepository.listAll -> IComponentRetrofitDatasource.listAll"
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
                ComponentRetrofitModel(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            val entityList = listOf(
                Component(
                    id = 1,
                    cod = 10,
                    descr = "ATIVIDADE"
                )
            )
            whenever(
                componentRetrofitDatasource.listAll("token")
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
    fun `getById - Check return failure if have error in ComponentRoomDatasource getById`() =
        runTest {
            whenever(
                componentRoomDatasource.getById(1)
            ).thenReturn(
                resultFailure(
                    "IComponentRoomDatasource.getById",
                    "-",
                    Exception()
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IComponentRepository.getById -> IComponentRoomDatasource.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getById - Check return correct if function execute successfully with data`() =
        runTest {
            whenever(
                componentRoomDatasource.getById(1)
            ).thenReturn(
                Result.success(
                    ComponentRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "Component 1"
                    )
                )
            )
            val result = repository.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Component(
                    id = 1,
                    cod = 10,
                    descr = "Component 1"
                )
            )
        }

    @Test
    fun `getById - Check return correct if function execute successfully without data`() =
        runTest {
            whenever(
                componentRoomDatasource.getById(1)
            ).thenReturn(
                Result.success(null)
            )
            val result = repository.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }
}