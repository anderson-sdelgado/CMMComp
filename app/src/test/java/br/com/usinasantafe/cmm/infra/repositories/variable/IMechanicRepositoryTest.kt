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

}