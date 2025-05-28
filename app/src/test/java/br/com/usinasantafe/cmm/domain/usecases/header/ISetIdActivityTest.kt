package br.com.usinasantafe.cmm.domain.usecases.header

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdActivityTest {

    private val headerMotoMecRepository = mock<HeaderMotoMecRepository>()
    private val usecase = ISetIdActivity(
        headerMotoMecRepository = headerMotoMecRepository
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity`() =
        runTest {
            whenever(
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRepository.setIdActivity",
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
                "ISetIdActivity -> IHeaderMotoMecRepository.setIdActivity"
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
                headerMotoMecRepository.setIdActivity(1)
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