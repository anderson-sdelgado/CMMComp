package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.transhipment.ISetNroEquipTranshipment
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetNroEquipTranshipmentTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val saveNote = mock<SaveNote>()
    private val usecase = ISetNroEquipTranshipment(
        motoMecRepository = motoMecRepository,
        saveNote = saveNote
    )

    @Test
    fun `Check return failure if value of field is incorrect - FlowApp TRANSHIPMENT`() =
        runTest {
            val result = usecase(
                nroEquip = "200a",
                flowApp = FlowApp.TRANSHIPMENT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> toLong"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }


    @Test
    fun `Check return failure if have error in MotoMecRepository getIdActivityHeader - FlowApp TRANSHIPMENT`() =
        runTest {
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
                nroEquip = "200",
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
    fun `Check return failure if have error in MotoMeRepository getNroOSHeader - FlowApp TRANSHIPMENT`() =
        runTest {
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
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
                nroEquip = "200",
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
    fun `Check return failure if have error in MotoMecRepository setNroOSNote - FlowApp TRANSHIPMENT`() =
        runTest {
            whenever(
                motoMecRepository.getIdActivityHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(10_000)
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
                nroEquip = "200",
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
    fun `Check return failure if have error in MotoMecRepository setIdActivityNote - FlowApp TRANSHIPMENT`() =
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
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(10_000)
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
    fun `Check return failure if have error in MotoMecRepository setNroEquipTranshipmentNote - FlowApp TRANSHIPMENT`() =
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
                Result.success(Unit)
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
                    "IMotoMecRepository.setNroEquipTranshipmentNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(10_000)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> IMotoMecRepository.setNroEquipTranshipmentNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen - FlowApp TRANSHIPMENT`() =
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdByHeaderOpen",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(10_000)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setNroEquipTranshipmentNote(200)
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
    fun `Check return failure if have error in SaveNote - FlowApp TRANSHIPMENT`() =
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                saveNote(1, 1, 10_000)
            ).thenReturn(
                resultFailure(
                    "SaveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                nroEquip = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(10_000)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setNroEquipTranshipmentNote(200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroEquipTranshipment -> SaveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp TRANSHIPMENT`() =
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                saveNote(1, 1, 10_000)
            ).thenReturn(
                Result.success(Unit)
            )
            usecase(
                nroEquip = "200",
                flowApp = FlowApp.TRANSHIPMENT
            )
            verify(motoMecRepository, atLeastOnce()).setNroOSNote(10_000)
            verify(motoMecRepository, atLeastOnce()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setNroEquipTranshipmentNote(200)
            verify(saveNote, atLeastOnce()).invoke(1, 1, 10_000)
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_WORK`() =
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                saveNote(1, 1, 10_000)
            ).thenReturn(
                Result.success(Unit)
            )
            usecase(
                nroEquip = "200",
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, never()).setNroOSNote(10_000)
            verify(motoMecRepository, never()).setIdActivityNote(1)
            verify(motoMecRepository, atLeastOnce()).setNroEquipTranshipmentNote(200)
            verify(saveNote, atLeastOnce()).invoke(1, 1, 10_000)
        }

}