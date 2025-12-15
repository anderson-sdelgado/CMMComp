package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNroTranshipmentTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetNroTranshipment(
        motoMecRepository = motoMecRepository
    )

    @Test
    fun `Check return failure if have error in MotoMeRepository getNroOSHeader and FlowApp TRANSHIPMENT`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMeRepository.getNroOSHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroTranshipment -> IMotoMeRepository.getNroOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10_000)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdActivityHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroTranshipment -> IMotoMecRepository.getIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setNroOSNote`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10_000)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(10_000)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setNroOSNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroTranshipment -> IMotoMecRepository.setNroOSNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setIdActivityNote`() =
        runTest {
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10_000)
            )
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.setNroOSNote(10_000)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroTranshipment -> IMotoMecRepository.setIdActivityNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

}