package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetIdActivityCommonTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val saveNote = mock<SaveNote>()
    private val equipRepository = mock<EquipRepository>()
    private val usecase = ISetIdActivityCommon(
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager,
        functionActivityRepository = functionActivityRepository,
        saveNote = saveNote,
        equipRepository = equipRepository
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.setIdActivityNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp HEADER_INITIAL if function execute successfully - FlowApp HEADER_INITIAL`() =
        runTest {
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_INITIAL
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setIdActivityNote - FlowApp NOTE_STOP`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_STOP
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.setIdActivityNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp NOTE_STOP if function execute successfully - FlowApp NOTE_STOP`() =
        runTest {
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_STOP
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.NOTE_STOP
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository setIdActivityHeader - FlowApp HEADER_INITIAL_REEL_FERT`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.setIdActivityHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL_REEL_FERT
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.setIdActivityHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in EquipRepository getIdEquipMain - FlowApp HEADER_INITIAL_REEL_FERT`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                resultFailure(
                    "IEquipRepository.getIdEquipMain",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL_REEL_FERT
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IEquipRepository.getIdEquipMain"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdHeaderByIdEquipAndNotFinish- FlowApp HEADER_INITIAL_REEL_FERT`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndNotFinish(10)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getIdHeaderByIdEquipAndNotFinish",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL_REEL_FERT
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.getIdHeaderByIdEquipAndNotFinish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository saveHeader - FlowApp HEADER_INITIAL_REEL_FERT`() =
        runTest {
            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndNotFinish(10)
            ).thenReturn(
                Result.success(3)
            )
            whenever(
                motoMecRepository.saveHeader(0.0, 3)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL_REEL_FERT
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.saveHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp HEADER_INITIAL_REEL_FERT`() =
        runTest {

            whenever(
                equipRepository.getIdEquipMain()
            ).thenReturn(
                Result.success(10)
            )
            whenever(
                motoMecRepository.getIdHeaderByIdEquipAndNotFinish(10)
            ).thenReturn(
                Result.success(3)
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL_REEL_FERT
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            verify(motoMecRepository, atLeastOnce()).saveHeader(0.0, 3)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_INITIAL_REEL_FERT
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository listByIdActivity - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                resultFailure(
                    "IFunctionActivityRepository.listByIdActivity",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IFunctionActivityRepository.listByIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp TRANSHIPMENT if function execute successfully and Function Activity list contains TRANSHIPMENT - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.TRANSHIPMENT
                        )
                    )
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            verify(motoMecRepository, never()).getIdHeaderPointing()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.TRANSHIPMENT
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getTypeEquipHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getTypeEquipHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.getTypeEquipHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return FlowApp NOTE_REEL_FERT if function execute successfully and type equip is NOTE_REEL_FERT - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.REEL_FERT)
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.NOTE_REEL_FERT
            )
        }

    @Test
    fun `Check return failure if have error in SaveNote - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            whenever(
                saveNote()
            ).thenReturn(
                resultFailure(
                    "ISaveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> ISaveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check call saveNote if process execute successfully - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getTypeEquipHeader()
            ).thenReturn(
                Result.success(TypeEquip.NORMAL)
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            verify(saveNote, atLeastOnce()).invoke()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.NOTE_WORK
            )
        }
}