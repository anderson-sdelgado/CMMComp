package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.PerformanceRepository
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISaveNoteTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val performanceRepository = mock<PerformanceRepository>()
    private val usecase = ISaveNote(
        motoMecRepository,
        functionActivityRepository,
        performanceRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository saveNote`() =
        runTest {
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, 1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository hasByIdAndType`() =
        runTest {
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.hasByIdAndType",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, 1, 1)
            verify(motoMecRepository, atLeastOnce()).saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IFunctionActivityRepository.hasByIdAndType"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PerformanceRepository insertInitialPerformance`() =
        runTest {
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                performanceRepository.initial(1, 1)
            ).thenReturn(
                resultFailure(
                    "IPerformanceRepository.insertInitialPerformance",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1, 1, 1)
            verify(motoMecRepository, atLeastOnce()).saveNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IPerformanceRepository.insertInitialPerformance"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check not call insertInitialPerformance if FunctionActivityRepository hasByIdAndType return false`() =
        runTest {
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            usecase(1, 1, 1)
            verify(motoMecRepository, atLeastOnce()).saveNote(1)
            verify(performanceRepository, never()).initial(1, 1)
        }

    @Test
    fun `Check call insertInitialPerformance if FunctionActivityRepository hasByIdAndType return true`() =
        runTest {
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            usecase(1, 1, 1)
            verify(motoMecRepository, atLeastOnce()).saveNote(1)
            verify(performanceRepository, atLeastOnce()).initial(1, 1)
        }

}