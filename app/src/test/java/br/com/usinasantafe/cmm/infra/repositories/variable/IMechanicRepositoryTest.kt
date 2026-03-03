package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.utils.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.MechanicRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.MechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.MechanicRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IMechanicRepositoryTest {

    private val mechanicRoomDatasource = mock<MechanicRoomDatasource>()
    private val mechanicSharedPreferencesDatasource = mock<MechanicSharedPreferencesDatasource>()
    private val repository = IMechanicRepository(
        mechanicRoomDatasource = mechanicRoomDatasource,
        mechanicSharedPreferencesDatasource = mechanicSharedPreferencesDatasource
    )

    @Test
    fun `checkNoteOpenByIdHeader - Check return failure if have error in MechanicRoomDatasource checkNoteOpenByIdHeader`() =
        runTest {
            whenever(
                mechanicRoomDatasource.checkNoteOpenByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IMechanicRoomDatasource.checkNoteOpenByIdHeader",
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
                "IMechanicRepository.hasNoteOpenByIdHeader -> IMechanicRoomDatasource.checkNoteOpenByIdHeader"
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
    fun `setFinishNote - Check return failure if have error in MechanicRoomDatasource setFinishNote`() =
        runTest {
            whenever(
                mechanicRoomDatasource.setFinishNote()
            ).thenReturn(
                resultFailure(
                    "IMechanicRoomDatasource.setFinishNote",
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
                "IMechanicRepository.setFinishNote -> IMechanicRoomDatasource.setFinishNote"
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

    @Test
    fun `setNroOS - Check return failure if have error in MechanicSharedPreferencesDatasource setNroOS`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.setNroOS(123456)
            ).thenReturn(
                resultFailure(
                    "IMechanicSharedPreferencesDatasource.setNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.setNroOS(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.setNroOS -> IMechanicSharedPreferencesDatasource.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOS - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setNroOS(123456)
            verify(mechanicSharedPreferencesDatasource, atLeastOnce()).setNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
        }

    @Test
    fun `getNroOS - Check return failure if have error in MechanicSharedPreferencesDatasource getNroOS`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.getNroOS()
            ).thenReturn(
                resultFailure(
                    "IMechanicSharedPreferencesDatasource.getNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getNroOS()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.getNroOS -> IMechanicSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.getNroOS()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getNroOS()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `setSeqItem - Check return failure if have error in MechanicSharedPreferencesDatasource setSeqItem`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.setSeqItem(1)
            ).thenReturn(
                resultFailure(
                    "IMechanicSharedPreferencesDatasource.setSeqItem",
                    "-",
                    Exception()
                )
            )
            val result = repository.setSeqItem(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.setSeqItem -> IMechanicSharedPreferencesDatasource.setSeqItem"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setSeqItem - Check return correct if function execute successfully`() =
        runTest {
            val result = repository.setSeqItem(1)
            verify(mechanicSharedPreferencesDatasource, atLeastOnce()).setSeqItem(1)
            assertEquals(
                result.isSuccess,
                true
            )
        }
    
    @Test
    fun `save - Check return failure if have error in MechanicSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IMechanicSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.save -> IMechanicSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return failure if Mechanic Shared Preferences is null`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MechanicSharedPreferencesModel()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.save -> entityToRoomModel"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: nroOS is required"
            )
        }

    @Test
    fun `save - Check return failure if have error in MechanicDatasource save`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MechanicSharedPreferencesModel(
                        nroOS = 123456,
                        seqItem = 2
                    )
                )
            )
            val modelCaptor = argumentCaptor< MechanicRoomModel>().apply {
                whenever(
                    mechanicRoomDatasource.save(capture())
                ).thenReturn(
                    resultFailure(
                        "IMechanicDatasource.save",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IMechanicRepository.save -> IMechanicDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.seqItem,
                2
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                mechanicSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    MechanicSharedPreferencesModel(
                        nroOS = 123456,
                        seqItem = 2
                    )
                )
            )
            val modelCaptor = argumentCaptor< MechanicRoomModel>().apply {
                whenever(
                    mechanicRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(Unit)
                )
            }
            val result = repository.save(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.seqItem,
                2
            )
        }
}