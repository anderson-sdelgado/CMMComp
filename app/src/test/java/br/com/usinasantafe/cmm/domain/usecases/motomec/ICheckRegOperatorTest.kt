package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ICheckRegOperatorTest {

    private val colabRepository = mock<ColabRepository>()
    private val usecase = IHasRegColab(
        colabRepository = colabRepository
    )

    @Test
    fun `Check return failure if value of field is incorrect`() =
        runTest {
            val result = usecase("19759a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRegOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository checkReg`() =
        runTest {
            whenever(
                colabRepository.hasByReg(19759)
            ).thenReturn(
                resultFailure(
                    "ColabRepository.checkReg",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckRegOperator -> ColabRepository.checkReg"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if not have matric in database`() =
        runTest {
            whenever(
                colabRepository.hasByReg(19759)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase("19759")
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
    fun `Check return true if have matric in database`() =
        runTest {
            whenever(
                colabRepository.hasByReg(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("19759")
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