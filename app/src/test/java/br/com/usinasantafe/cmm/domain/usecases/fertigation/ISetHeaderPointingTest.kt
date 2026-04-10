package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetHeaderPointingTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetHeaderPointing(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository openHeaderById`() =
        runTest {
            whenever(
                motoMecRepository.openHeaderById(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.openHeaderById",
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
                "ISetHeaderPointing -> IMotoMecRepository.openHeaderById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase(1)
            verify(motoMecRepository, atLeastOnce()).openHeaderById(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }

}