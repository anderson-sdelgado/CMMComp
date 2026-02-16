package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IUncouplingTrailerTest {

    private val cecRepository = Mockito.mock<CECRepository>()
    private val usecase = IUncouplingTrailer(
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository uncouplingTrailer`() =
        runTest {
            whenever(
                cecRepository.uncouplingTrailer()
            ).thenReturn(
                resultFailure(
                    "ICECRepository.uncouplingTrailer",
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
                "IUncouplingTrailer -> ICECRepository.uncouplingTrailer"
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
                cecRepository.uncouplingTrailer()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase()
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