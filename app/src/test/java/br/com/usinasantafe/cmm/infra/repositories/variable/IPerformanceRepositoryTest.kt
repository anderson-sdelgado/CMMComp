package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class IPerformanceRepositoryTest {

    private val performanceRoomDatasource = mock<PerformanceRoomDatasource>()
    private val repository = IPerformanceRepository(
        performanceRoomDatasource = performanceRoomDatasource
    )

    @Test
    fun `initial - Check return failure if have error in PerformanceRoomDatasource insert`() =
        runTest {
            val modelCaptor = argumentCaptor<PerformanceRoomModel>().apply {
                whenever(
                    performanceRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "IPerformanceRoomDatasource.insert",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.initial(10, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.initial -> IPerformanceRoomDatasource.insert"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                10
            )
        }

    @Test
    fun `initial - Check return correct if function execute successfully`() =
        runTest {
            val modelCaptor = argumentCaptor<PerformanceRoomModel>().apply {
                whenever(
                    performanceRoomDatasource.insert(
                        capture()
                    )
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            val result = repository.initial(10, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                10
            )
        }

    @Test
    fun `listByIdHeader - Check return failure if have error in PerformanceRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                performanceRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.listByIdHeader -> IPerformanceRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                performanceRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        PerformanceRoomModel(
                            id = 1,
                            idHeader = 1,
                            nroOS = 1,
                            value = 10.0,
                            dateHour = Date(1750422691)
                        )
                    )
                )
            )
            val result = repository.listByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    Performance(
                        id = 1,
                        idHeader = 1,
                        nroOS = 1,
                        value = 10.0,
                        dateHour = Date(1750422691)
                    )
                )
            )
        }

    @Test
    fun `update - Check return failure if have error in PerformanceRoomDatasource update`() =
        runTest {
            whenever(
                performanceRoomDatasource.update(1, 50.0)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.update",
                    "-",
                    Exception()
                )
            )
            val result = repository.update(1, 50.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.update -> IPerformanceRoomDatasource.update"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `update - Check return correct if function execute successfully`() =
        runTest {
            repository.update(1, 50.0)
            verify(performanceRoomDatasource, atLeastOnce()).update(1, 50.0)
        }

    @Test
    fun `getNroOSById - Check return failure if have error in PerformanceRoomDatasource getNroOSById`() =
        runTest {
            whenever(
                performanceRoomDatasource.getNroOSById(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.getNroOSById",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNroOSById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.getNroOSById -> IPerformanceRoomDatasource.getNroOSById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroOSById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                performanceRoomDatasource.getNroOSById(1)
            ).thenReturn(
                Result.success(100)
            )
            val result = repository.getNroOSById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                100
            )
        }

    @Test
    fun `hasByIdHeaderAndValueNull - Check return failure if have error in PerformanceRoomDatasource hasByIdHeaderAndValueNull`() =
        runTest {
            whenever(
                performanceRoomDatasource.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.hasByIdHeaderAndValueNull",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasByIdHeaderAndValueNull(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.hasByIdHeaderAndValueNull -> IPerformanceRoomDatasource.hasByIdHeaderAndValueNull"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasPerformanceByIdHeaderAndValueNull - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                performanceRoomDatasource.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasByIdHeaderAndValueNull(1)
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
    fun `hasByIdHeader - Check return failure if have error in PerformanceRoomDatasource hasByIdHeader`() =
        runTest {
            whenever(
                performanceRoomDatasource.hasByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRoomDatasource.hasByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPerformanceRepository.hasByIdHeader -> IPerformanceRoomDatasource.hasByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                performanceRoomDatasource.hasByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.hasByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }
}