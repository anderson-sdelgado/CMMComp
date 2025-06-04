package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IGetNroOSHeaderTest {

    private val headerMotoMecRepository = mock<HeaderMotoMecRepository>()
    private val usecase = IGetNroOSHeader(
        headerMotoMecRepository = headerMotoMecRepository
    )

    @Test
    fun `Check return failure if have error in headerMotoMecRepository getNroOS`() =
        runTest {
            whenever(
                headerMotoMecRepository.getNroOS()
            ).thenReturn(
                resultFailure(
                    context = "HeaderMotoMecRepository.getNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetNroOSHeader -> HeaderMotoMecRepository.getNroOS"
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
                headerMotoMecRepository.getNroOS()
            ).thenReturn(
                Result.success(10000)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "10000"
            )
        }

}