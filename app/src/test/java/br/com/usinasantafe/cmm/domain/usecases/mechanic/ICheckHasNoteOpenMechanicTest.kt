package br.com.usinasantafe.cmm.domain.usecases.mechanic

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MechanicRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckHasNoteOpenMechanicTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val mechanicRepository = mock<MechanicRepository>()
    private val usecase = ICheckHasNoteOpenMechanic(
        motoMecRepository = motoMecRepository,
        mechanicRepository = mechanicRepository
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
                "ICheckHasNoteOpenMechanic -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MechanicRepository checkNoteOpenByIdHeader`() =
        runTest {
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                mechanicRepository.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMechanicRepository.checkNoteOpenByIdHeader",
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
                "ICheckHasNoteOpenMechanic -> IMechanicRepository.checkNoteOpenByIdHeader"
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
                mechanicRepository.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
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