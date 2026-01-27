package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IHasCouplingTrailerTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IHasCouplingTrailer(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository hasCouplingTrailerImplement`() =
        runTest {
            whenever(
                motoMecRepository.hasCouplingTrailerImplement()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.hasCouplingTrailerImplement",
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
                "IHasCouplingTrailer -> IMotoMecRepository.hasCouplingTrailerImplement"
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
                motoMecRepository.hasCouplingTrailerImplement()
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