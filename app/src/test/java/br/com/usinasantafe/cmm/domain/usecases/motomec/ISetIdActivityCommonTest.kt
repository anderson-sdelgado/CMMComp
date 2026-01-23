package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class ISetIdActivityCommonTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val functionActivityRepository = mock<FunctionActivityRepository>()
    private val usecase = ISetIdActivityCommon(
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager,
        functionActivityRepository = functionActivityRepository
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
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.HEADER_INITIAL
            )
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
                Result.success(Unit)
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
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_STOP
            )
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
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
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
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
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
    fun `Check return failure if have error in MotoMecRepository insertInitialPerformance and Function Activity list contains PERFORMANCE  - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.PERFORMANCE
                        )
                    )
                )
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.insertInitialPerformance",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.insertInitialPerformance"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen and Function Activity list contains PERFORMANCE  - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                functionActivityRepository.listById(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.PERFORMANCE
                        )
                    )
                )
            )
            whenever(
                motoMecRepository.insertInitialPerformance()
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
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
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
    fun `Check return failure if have error in MotoMecRepository getIdByHeaderOpen - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
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
    fun `Check return failure if have error in MotoMecRepository saveNote - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
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
                motoMecRepository.saveNote(1)
            ).thenReturn(
                resultFailure(
                    "IMotoMecRepository.saveNote",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                id = 1,
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdActivityCommon -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in MotoMecRepository getTypeEquipHeader - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(Unit)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(Unit)
            )
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
                motoMecRepository.saveNote(1)
            ).thenReturn(
                Result.success(Unit)
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

}