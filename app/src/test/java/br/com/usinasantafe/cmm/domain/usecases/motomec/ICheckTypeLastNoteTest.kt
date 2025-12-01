package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.variable.NoteMotoMec
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.TypeNote
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckTypeLastNoteTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ICheckTypeLastNote(
        motoMecRepository = motoMecRepository
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
                "ICheckTypeLastNote -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository hasNoteByIdHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.hasNoteByIdHeader",
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
                "ICheckTypeLastNote -> IMotoMecRepository.hasNoteByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return null if not have data in table`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNotLastByIdHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNoteLastByIdHeader",
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
                "ICheckTypeLastNote -> IMotoMecRepository.getNoteLastByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return WORK if function execute successfully and the last note is WORK`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    NoteMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeNote.WORK
            )
        }

    @Test
    fun `Check return STOP if function execute successfully and the last note is STOP`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.hasNoteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getNoteLastByIdHeader(1)
            ).thenReturn(
                Result.success(
                    NoteMotoMec(
                        id = 1,
                        nroOS = 123456,
                        idActivity = 1,
                        idStop = 1
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                TypeNote.STOP
            )
        }
}