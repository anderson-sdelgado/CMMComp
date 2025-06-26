package br.com.usinasantafe.cmm.domain.usecases.note

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.variable.MotoMecRepository
import br.com.usinasantafe.cmm.domain.usecases.background.StartWorkManager
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISetIdStopNoteTest {

    private val motoMecRepository = mock<MotoMecRepository>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetIdStopNote(
        motoMecRepository = motoMecRepository,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository setIdStop`() =
        runTest {
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                resultFailure(
                    "INoteMotoMecRepository.setIdStop",
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
                "ISetIdStopNote -> INoteMotoMecRepository.setIdStop"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in HeaderMotoMecRepository getIdByHeaderOpen`() =
        runTest {
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdByOpenHeader()
            ).thenReturn(
                resultFailure(
                    "IHeaderMotoMecRepository.getId",
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
                "ISetIdStopNote -> IHeaderMotoMecRepository.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in NoteMotoMecRepository save`() =
        runTest {
            whenever(
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdByOpenHeader()
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
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> INoteMotoMecRepository.save"
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
                motoMecRepository.setIdStop(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                motoMecRepository.getIdByOpenHeader()
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                motoMecRepository.saveNote(1)
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

}