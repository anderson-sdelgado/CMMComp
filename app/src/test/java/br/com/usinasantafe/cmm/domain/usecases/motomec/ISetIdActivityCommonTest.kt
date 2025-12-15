package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeActivity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp HEADER_DEFAULT`() =
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
            val result = usecase(1)
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
    fun `Check return correct if function execute successfully - FlowApp HEADER_DEFAULT`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp NOTE_STOP`() =
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
                flowApp = FlowApp.NOTE_STOP
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
    fun `Check return failure if have error in NoteMotoMecRepository setIdActivity - FlowApp NOTE_STOP`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRepository.setIdActivity",
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
                "ISetIdActivityCommon -> INoteMotoMecRepository.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_STOP`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
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
                true
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp NOTE_WORK`() =
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
                flowApp = FlowApp.NOTE_WORK
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
    fun `Check return failure if have error in NoteMotoMecRepository setIdActivity - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRepository.setIdActivity",
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
                "ISetIdActivityCommon -> INoteMotoMecRepository.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in FunctionActivityRepository listByIdActivity - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
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
    fun `Check return false if have TypeActivity TRANSHIPMENT in functionActivity by idActivity - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.REEL,
                        ),
                        FunctionActivity(
                            idFunctionActivity = 2,
                            idActivity = 1,
                            typeActivity = TypeActivity.TRANSHIPMENT,
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
                false
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getId - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(emptyList())
            )
            whenever(
                motoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRepository.getId",
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
                "ISetIdActivityCommon -> IHeaderMotoMecRepository.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository save - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        FunctionActivity(
                            idFunctionActivity = 1,
                            idActivity = 1,
                            typeActivity = TypeActivity.REEL,
                        )
                    )
                )
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
                    "INoteMotoMecRepository.save",
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
                "ISetIdActivityCommon -> INoteMotoMecRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                motoMecRepository.setIdActivityHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.setIdActivityNote(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                functionActivityRepository.listByIdActivity(1)
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
                Result.success(true)
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
                true
            )
        }

}