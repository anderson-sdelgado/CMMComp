package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMechanicRoomDatasource
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IMechanicRepositoryTest {

    private val noteMechanicRoomDatasource = mock<NoteMechanicRoomDatasource>()
    private val repository = IMechanicRepository(
        noteMechanicRoomDatasource = noteMechanicRoomDatasource
    )

    @Test
    fun `checkNoteOpenByIdHeader - Check return failure if have error in NoteMechanicRoomDatasource checkNoteOpenByIdHeader`() =
        runTest {
            whenever(
                noteMechanicRoomDatasource.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "INoteMechanicRoomDatasource.checkNoteOpenByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkNoteOpenByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.checkNoteOpenByIdHeader -> INoteMechanicRoomDatasource.checkNoteOpenByIdHeader"
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
                noteMechanicRoomDatasource.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkNoteOpenByIdHeader(1)
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
                noteMechanicRoomDatasource.setFinishNote()
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
                noteMechanicRoomDatasource.setFinishNote()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setFinishNote()
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