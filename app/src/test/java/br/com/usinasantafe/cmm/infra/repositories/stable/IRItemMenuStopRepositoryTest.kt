package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RItemMenuStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RItemMenuStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.RItemMenuStopRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IRItemMenuStopRepositoryTest {

    private val rItemMenuStopRetrofitDatasource = mock<RItemMenuStopRetrofitDatasource>()
    private val rItemMenuStopRoomDatasource = mock<RItemMenuStopRoomDatasource>()
    private val repository = IRItemMenuStopRepository(
        rItemMenuStopRetrofitDatasource = rItemMenuStopRetrofitDatasource,
        rItemMenuStopRoomDatasource = rItemMenuStopRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error in RItemMenuStopDatasource addAll`() =
        runTest {
            val roomModelList = listOf(
                RItemMenuStopRoomModel(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            val entityList = listOf(
                RItemMenuStop(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            whenever(
                rItemMenuStopRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IRItemMenuStopDatasource.addAll",
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
                "IRItemMenuStopRepository.addAll -> IRItemMenuStopDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return correct if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                RItemMenuStopRoomModel(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            val entityList = listOf(
                RItemMenuStop(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            whenever(
                rItemMenuStopRoomDatasource.addAll(roomModelList)
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
    fun `deleteAll - Check return failure if have error in RItemMenuStopDatasource deleteAll`() =
        runTest {
            whenever(
                rItemMenuStopRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IRItemMenuStopDatasource.deleteAll",
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
                "IRItemMenuStopRepository.deleteAll -> IRItemMenuStopDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                rItemMenuStopRoomDatasource.deleteAll()
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
    fun `listAll - Check return failure if have error in RItemMenuStopDatasource listAll`() =
        runTest {
            whenever(
                rItemMenuStopRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IRItemMenuStopDatasource.listAll",
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
                "IRItemMenuStopRepository.listAll -> IRItemMenuStopDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return correct if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                RItemMenuStopRetrofitModel(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            val entityList = listOf(
                RItemMenuStop(
                    id = 1,
                    idFunction = 1,
                    idApp = 1,
                    idStop = 1
                )
            )
            whenever(
                rItemMenuStopRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(retrofitModelList)
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