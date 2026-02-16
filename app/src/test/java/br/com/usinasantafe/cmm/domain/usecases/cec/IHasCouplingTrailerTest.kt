package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IHasCouplingTrailerTest {

    private val cecRepository = Mockito.mock<CECRepository>()
    private val usecase = IHasCouplingTrailer(
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository hasCouplingTrailer`() =
        runTest {
            whenever(
                cecRepository.hasCouplingTrailer()
            ).thenReturn(
                resultFailure(
                    "ICECRepository.hasCouplingTrailer",
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
                "IHasCouplingTrailer -> ICECRepository.hasCouplingTrailer"
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
                cecRepository.hasCouplingTrailer()
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