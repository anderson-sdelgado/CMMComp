package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
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
    private val fertigationRepository = mock<FertigationRepository>()
    private val usecase = ISaveNote(
        motoMecRepository = motoMecRepository,
        functionActivityRepository = functionActivityRepository,
        performanceRepository = performanceRepository,
        fertigationRepository = fertigationRepository
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
                "ISaveNote -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNroOSHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNroOSHeader",
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
                "ISaveNote -> IMotoMecRepository.getNroOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdActivityHeader",
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
                "ISaveNote -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveNote`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveNote",
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
                "ISaveNote -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository hasByIdAndType and TypeActivity PERFORMANCE`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
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
            val result = usecase()
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
    fun `Check return failure if have error in FunctionActivityRepository hasByIdAndType and TypeActivity COLLECTION`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.hasByIdAndType",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            verify(performanceRepository, never()).initial(1, 1)
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
    fun `Check return failure if have error in FertigationRepository initialCollection`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                fertigationRepository.initialCollection(1, 1)
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.initialCollection",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            verify(performanceRepository, never()).initial(1, 1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveNote -> IFertigationRepository.initialCollection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check not call FertigationRepository initialCollection if FunctionActivityRepository hasByIdAndType return false and TypeActivity COLLECTION`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            verify(performanceRepository, never()).initial(1, 1)
            verify(fertigationRepository, atLeastOnce()).initialCollection(1, 1)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `Check call FertigationRepository initialCollection if FunctionActivityRepository hasByIdAndType return true and TypeActivity COLLECTION`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            verify(performanceRepository, never()).initial(1, 1)
            verify(fertigationRepository, never()).initialCollection(1, 1)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveImplement`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                motoMecRepository.saveImplement(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveImplement",
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
                "ISaveNote -> IMotoMecRepository.saveImplement"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1,1, 1)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    1, TypeActivity.COLLECTION
                )
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            verify(performanceRepository, never()).initial(1, 1)
            verify(fertigationRepository, never()).initialCollection(1, 1)
            verify(motoMecRepository, atLeastOnce()).saveImplement(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }
}