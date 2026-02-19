package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Pressure
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.PressureRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PressureRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.PressureRetrofitModel
import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IPressureRepositoryTest {

    private val pressureRoomDatasource = mock<PressureRoomDatasource>()
    private val pressureRetrofitDatasource = mock<PressureRetrofitDatasource>()
    private val repository = IPressureRepository(
        pressureRetrofitDatasource = pressureRetrofitDatasource,
        pressureRoomDatasource = pressureRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                PressureRoomModel(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            val entityList = listOf(
                Pressure(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            whenever(
                pressureRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IPressureRoomDatasource.addAll",
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
                "IPressureRepository.addAll -> IPressureRoomDatasource.addAll"
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
                PressureRoomModel(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            val entityList = listOf(
                Pressure(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            whenever(
                pressureRoomDatasource.addAll(roomModelList)
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
                pressureRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IPressureRoomDatasource.deleteAll",
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
                "IPressureRepository.deleteAll -> IPressureRoomDatasource.deleteAll"
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
                pressureRoomDatasource.deleteAll()
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
                pressureRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IPressureRetrofitDatasource.listAll",
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
                "IPressureRepository.listAll -> IPressureRetrofitDatasource.listAll"
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
                PressureRetrofitModel(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            val entityList = listOf(
                Pressure(
                    id = 1,
                    idNozzle = 1,
                    value = 10.0,
                    speed = 1
                )
            )
            whenever(
                pressureRetrofitDatasource.listAll("token")
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
    fun `listByIdNozzle - Check return failure if have error in PressureRoomDatasource listByIdNozzle`() =
        runTest {
            whenever(
                pressureRoomDatasource.listByIdNozzle(1)
            ).thenReturn(
                resultFailure(
                    "IPressureRoomDatasource.listByIdNozzle",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdNozzle(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPressureRepository.listByIdNozzle -> IPressureRoomDatasource.listByIdNozzle"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdNozzle - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                pressureRoomDatasource.listByIdNozzle(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        PressureRoomModel(
                            id = 1,
                            idNozzle = 1,
                            value = 10.0,
                            speed = 1
                        )
                    )
                )
            )
            val result = repository.listByIdNozzle(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    )
                )
            )
        }

    @Test
    fun `listByIdNozzleAndValuePressure - Check return failure if have error in PressureRoomDatasource listByIdNozzleAndValuePressure`() =
        runTest {
            whenever(
                pressureRoomDatasource.listByIdNozzleAndValuePressure(1, 10.0)
            ).thenReturn(
                resultFailure(
                    "IPressureRoomDatasource.listByIdNozzleAndValuePressure",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdNozzleAndValuePressure(1, 10.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPressureRepository.listByIdNozzleAndValuePressure -> IPressureRoomDatasource.listByIdNozzleAndValuePressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdNozzleAndValuePressure - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                pressureRoomDatasource.listByIdNozzleAndValuePressure(1, 10.0)
            ).thenReturn(
                Result.success(
                    listOf(
                        PressureRoomModel(
                            id = 1,
                            idNozzle = 1,
                            value = 10.0,
                            speed = 1
                        )
                    )
                )
            )
            val result = repository.listByIdNozzleAndValuePressure(1, 10.0)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Pressure(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    )
                )
            )
        }

}