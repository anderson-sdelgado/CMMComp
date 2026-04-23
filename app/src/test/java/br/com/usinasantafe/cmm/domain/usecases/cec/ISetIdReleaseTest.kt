package br.com.usinasantafe.cmm.domain.usecases.cec

import br.com.usinasantafe.cmm.domain.repositories.variable.CECRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetIdReleaseTest {

    private val cecRepository = mock<CECRepository>()
    private val usecase = ISetIdRelease(
        cecRepository = cecRepository
    )

    @Test
    fun `Check return failure if value input is incorrect`() =
        runTest {
            val result = usecase("dsfsda452")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdRelease -> toInt"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"dsfsda452\""
            )
        }

    @Test
    fun `Check return failure if have error in CECRepository setNroOSAndIdRelease`() =
        runTest {
            whenever(
                cecRepository.setIdReleasePreCEC(20_000)
            ).thenReturn(
                resultFailure(
                    "ICECRepository.setNroOSAndIdRelease",
                    "-",
                    Exception()
                )
            )
            val result = usecase("20000")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdRelease -> ICECRepository.setNroOSAndIdRelease"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase("20000")
            verify(cecRepository, atLeastOnce()).setIdReleasePreCEC(20_000)
            assertEquals(
                result.isSuccess,
                true
            )
        }

}