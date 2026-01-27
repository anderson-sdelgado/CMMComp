package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IMechanicRepositoryTest {

    private val mechanicRoomDatasource = mock<MechanicRoomDatasource>()
    private val repository = IMechanicRepository(
        mechanicRoomDatasource = mechanicRoomDatasource
    )

    @Test
    fun `checkNoteOpenByIdHeader - Check return failure if have error in NoteMechanicRoomDatasource checkNoteOpenByIdHeader`() =
        runTest {
            whenever(
                mechanicRoomDatasource.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "INoteMechanicRoomDatasource.checkNoteOpenByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasNoteOpenByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.hasNoteOpenByIdHeader -> INoteMechanicRoomDatasource.checkNoteOpenByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkNoteOpenByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                mechanicRoomDatasource.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.hasNoteOpenByIdHeader(1)
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
    fun `setFinishNote - Check return failure if have error in NoteMechanicDatasource setFinishNote`() =
        runTest {
            whenever(
                mechanicRoomDatasource.setFinishNote()
            ).thenReturn(
                resultFailure(
                    "INoteMechanicDatasource.setFinishNote",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFinishNote()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.setFinishNote -> INoteMechanicDatasource.setFinishNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }
    
    @Test
    fun `setFinishNote - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                mechanicRoomDatasource.setFinishNote()
            ).thenReturn(
                Result.success(Unit)
            )
            val result = repository.setFinishNote()
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