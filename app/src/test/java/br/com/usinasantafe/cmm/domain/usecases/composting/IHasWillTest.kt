package br.com.usinasantafe.cmm.domain.usecases.composting

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.CompostingRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowComposting
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IHasWillTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val compostingRepository = mock<CompostingRepository>()
    private val usecase = IHasWill(
        motoMecRepository = motoMecRepository,
        compostingRepository = compostingRepository
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
                "IHasWill -> IMotoMecRepository.getFlowCompostingHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in CompostingRepository hasWill`() =
        runTest {
            whenever(
                motoMecRepository.getFlowCompostingHeader()
            ).thenReturn(
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                compostingRepository.hasWill(FlowComposting.INPUT)
            ).thenReturn(
                resultFailure(
                    "ICompostingRepository.hasWill",
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
                "IHasWill -> ICompostingRepository.hasWill"
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
                Result.success(FlowComposting.INPUT)
            )
            whenever(
                compostingRepository.hasWill(FlowComposting.INPUT)
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