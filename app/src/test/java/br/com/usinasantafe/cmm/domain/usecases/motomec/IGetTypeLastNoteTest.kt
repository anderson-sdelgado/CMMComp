package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.TypeNote
import br.com.usinasantafe.cmm.utils.resultFailure
import org.mockito.Mockito.mock
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class IGetTypeLastNoteTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IGetTypeLastNote(
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
                "IGetTypeLastNote -> IMotoMecRepository.getIdByHeaderOpen"
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
                "IGetTypeLastNote -> IMotoMecRepository.hasNoteByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return null if MotoMecRepository hasNoteByIdHeader return false`() =
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
    fun `Check return failure if have error in MotoMecRepository getNoteLastByIdHeader`() =
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
                "IGetTypeLastNote -> IMotoMecRepository.getNoteLastByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return TypeNote WORK if noteLast idStop is null`() =
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
                    ItemMotoMec()
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
    fun `Check return TypeNote STOP if noteLast idStop is not null`() =
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
                    ItemMotoMec(idStop = 1)
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