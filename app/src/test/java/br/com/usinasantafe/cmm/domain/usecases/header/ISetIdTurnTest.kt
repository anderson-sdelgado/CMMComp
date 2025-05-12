package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdTurnTest  {

    private val headerMotoMecRepository = mock<HeaderMotoMecRepository>()
    private val usecase = ISetIdTurn(
        headerMotoMecRepository = headerMotoMecRepository
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdTurn`() =
        runTest {
            whenever(
                headerMotoMecRepository.setIdTurn(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRepository.setIdTurn",
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
                "ISetIdTurn -> IHeaderMotoMecRepository.setIdTurn"
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
                headerMotoMecRepository.setIdTurn(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
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