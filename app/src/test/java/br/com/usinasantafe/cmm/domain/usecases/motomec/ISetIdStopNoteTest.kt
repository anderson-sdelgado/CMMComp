package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdStopNoteTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()

    private val usecase = ISetIdStopNote(
        motoMecRepository = motoMecRepository,
        functionActivityRepository = functionActivityRepository,
        startWorkManager = startWorkManager
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
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.getIdByHeaderOpen"
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
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdActivityHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository setIdStop`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRepository.setIdStop",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> INoteMotoMecRepository.setIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository save`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRepository.save",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> INoteMotoMecRepository.save"
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.hasByIdAndType",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IFunctionActivityRepository.hasByIdAndType"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and checkPerformance is false`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase(1)
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
    fun `Check return failure if have error in MotoMecRepository insertInitialPerformance`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.insertInitialPerformance",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.insertInitialPerformance"
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
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.hasByIdAndType(
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
        }

}