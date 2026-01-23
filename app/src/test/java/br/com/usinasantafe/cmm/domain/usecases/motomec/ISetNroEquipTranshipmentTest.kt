package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.FlowApp
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNroEquipTranshipmentTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val usecase = ISetNroEquipTranshipment(
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
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMeRepository.getNroOSHeader"
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
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getIdActivityHeader"
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
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.setNroOSNote"
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
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.setIdActivityNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if nroEquip is incorrect`() =
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
                Result.success(Unit)
            )
            val result = usecase(
                nroEquipTranshipment = "20df0",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"20df0\""
            )
        }


    @Test
    fun `Check return failure if have error in MotoMecRepository setnroEquipTranshipmentNote`() =
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
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setNroEquipTranshipmentNote(200)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setnroEquipTranshipmentNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.setnroEquipTranshipmentNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen`() =
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
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setNroEquipTranshipmentNote(200)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveNote`() =
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
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setNroEquipTranshipmentNote(200)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.saveNote"
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
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setNroEquipTranshipmentNote(200)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                nroEquipTranshipment = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
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