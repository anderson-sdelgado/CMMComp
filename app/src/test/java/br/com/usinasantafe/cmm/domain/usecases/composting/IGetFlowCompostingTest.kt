package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowComposting
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IGetFlowCompostingTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = IGetFlowComposting(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMecRepository getFlowCompostingHeader`() =
        runTest {
            whenever(
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getFlowCompostingHeader",
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
                "IGetFlowComposting -> IMotoMecRepository.getFlowCompostingHeader"
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
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                Result.success(
                    FlowComposting.COMPOUND
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowComposting.COMPOUND
            )
        }


}