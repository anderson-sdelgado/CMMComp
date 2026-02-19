package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
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
    private val usecase = ISetIdActivityCommon(
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager,
        functionActivityRepository = functionActivityRepository,
        saveNote = saveNote
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp HEADER_INITIAL`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRepository.setIdActivity",
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
                "ISetIdActivityCommon -> IHeaderMotoMecRepository.setIdActivity"
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
            verify(motoMecRepository, never()).getIdByHeaderOpen()
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
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
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
                "ISetIdActivityCommon -> IMotoMecRepository.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getNroOSHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.getNroOSHeader",
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
                "ISetIdActivityCommon -> IMotoMecRepository.getNroOSHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
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
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.getNroOSHeader()
            ).thenReturn(
                Result.success(1)
            )
            usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            verify(motoMecRepository, atLeastOnce()).setIdActivityHeader(1)
            verify(saveNote, atLeastOnce()).invoke()
        }
}