package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetIdTurnTest  {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetIdTurn(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdTurn`() =
        runTest {
            whenever(
                motoMecRepository.setIdTurnHeader(1)
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
                motoMecRepository.setIdTurnHeader(1)
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