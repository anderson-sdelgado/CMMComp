package br.com.usinasantafe.cmm.domain.usecases.performance

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckClosePerformanceTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val performanceRepository = mock<PerformanceRepository>()
    private val usecase = ICheckClosePerformance(
        motoMecRepository = motoMecRepository,
        performanceRepository = performanceRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckClosePerformance -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository hasPerformanceByIdHeaderAndValueNull`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                performanceRepository.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.hasPerformanceByIdHeaderAndValueNull",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckClosePerformance -> IMotoMecRepository.hasPerformanceByIdHeaderAndValueNull"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false and execute finishHeader if PerformanceRepository hasByIdHeaderAndValueNull return true`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                performanceRepository.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            verify(motoMecRepository, never()).finishHeader()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return true and not execute finishHeader if PerformanceRepository hasByIdHeaderAndValueNull return false`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                performanceRepository.hasByIdHeaderAndValueNull(1)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            verify(motoMecRepository, atLeastOnce()).finishHeader()
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