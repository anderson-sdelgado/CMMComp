package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.domain.repositories.variable.FertigationRepository
import br.com.usinasantafe.cmm.domain.usecases.motomec.SaveNote
import br.com.usinasantafe.cmm.lib.StartWorkManager
import br.com.usinasantafe.cmm.utils.resultFailure
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetSpeedPressureTest {

    private val fertigationRepository = mock<FertigationRepository>()
    private val saveNote = mock<SaveNote>()
    private val startWorkManager = mock<StartWorkManager>()
    private val usecase = ISetSpeedPressure(
        fertigationRepository = fertigationRepository,
        saveNote = saveNote,
        startWorkManager = startWorkManager
    )

    @Test
    fun `Check return failure if have error in FertigationRepository setSpeedPressure`() =
        runTest {
            whenever(
                fertigationRepository.setSpeedPressure(1)
            ).thenReturn(
                resultFailure(
                    "IFertigationRepository.setSpeedPressure",
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
                "ISetSpeedPressure -> IFertigationRepository.setSpeedPressure"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in Save`() =
        runTest {
            whenever(
                saveNote()
            ).thenReturn(
                resultFailure(
                    "Save",
                    "-",
                    Exception()
                )
            )
            val result = usecase(1)
            verify(fertigationRepository, atLeastOnce()).setSpeedPressure(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSpeedPressure -> Save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            val result = usecase(1)
            verify(fertigationRepository, atLeastOnce()).setSpeedPressure(1)
            verify(saveNote, atLeastOnce()).invoke()
            verify(startWorkManager, atLeastOnce()).invoke()
            assertEquals(
                result.isSuccess,
                true
            )
        }

}