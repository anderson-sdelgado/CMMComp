package br.com.usinasantafe.cmm.domain.usecases.common

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.HeaderMotoMecRepository
import br.com.usinasantafe.cmm.domain.repositories.variable.NoteMotoMecRepository
import br.com.usinasantafe.cmm.utils.FlowApp
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdActivityCommonTest {

    private val headerMotoMecRepository = mock<HeaderMotoMecRepository>()
    private val noteMotoMecRepository = mock<NoteMotoMecRepository>()
    private val usecase = ISetIdActivityCommon(
        headerMotoMecRepository = headerMotoMecRepository,
        noteMotoMecRepository = noteMotoMecRepository
    )

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository setIdActivity - FlowApp HEADER_DEFAULT`() =
        runTest {
            whenever(
                headerMotoMecRepository.setIdActivity(1)
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
                "ISetIdActivity -> IHeaderMotoMecRepository.setIdActivity"
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
                headerMotoMecRepository.setIdActivity(1)
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
                headerMotoMecRepository.setIdActivity(1)
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
                "ISetIdActivity -> IHeaderMotoMecRepository.setIdActivity"
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
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
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
                "ISetIdActivity -> INoteMotoMecRepository.setIdActivity"
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
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
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
                headerMotoMecRepository.setIdActivity(1)
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
                "ISetIdActivity -> IHeaderMotoMecRepository.setIdActivity"
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
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
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
                "ISetIdActivity -> INoteMotoMecRepository.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getId - FlowApp NOTE_WORK`() =
        runTest {
            whenever(
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdByHeaderOpen()
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
                "ISetIdActivity -> IHeaderMotoMecRepository.getId"
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
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                noteMotoMecRepository.save(1)
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
                "ISetIdActivity -> INoteMotoMecRepository.save"
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
                headerMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                noteMotoMecRepository.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerMotoMecRepository.getIdByHeaderOpen()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                noteMotoMecRepository.save(1)
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