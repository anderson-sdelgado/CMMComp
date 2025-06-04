package br.com.usinasantafe.cmm.infra.repositories.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.infra.datasource.room.variable.NoteMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever

class INoteMotoMecRepositoryTest {

    private val noteMotoMecSharedPreferencesDatasource = mock<NoteMotoMecSharedPreferencesDatasource>()
    private val noteMotoMecRoomDatasource = mock<NoteMotoMecRoomDatasource>()
    private val repository = INoteMotoMecRepository(
        noteMotoMecSharedPreferencesDatasource = noteMotoMecSharedPreferencesDatasource,
        noteMotoMecRoomDatasource = noteMotoMecRoomDatasource
    )

    @Test
    fun `setNroOS - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setNroOS`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setNroOS(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setNroOS(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMotoMecRepository.setNroOS -> INoteMotoMecSharedPreferencesDatasource.setNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setNroOS - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setNroOS(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setNroOS(1)
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
    fun `setIdActivity- Check return failure if have error in NoteMotoMecSharedPreferencesDatasource setIdActivity`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.setIdActivity",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.setIdActivity(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMotoMecRepository.setIdActivity -> INoteMotoMecSharedPreferencesDatasource.setIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setIdActivity - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.setIdActivity(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setIdActivity(1)
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
    fun `save - Check return failure if have error in NoteMotoMecSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    context = "INoteMotoMecSharedPreferencesDatasource.get",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMotoMecRepository.save -> INoteMotoMecSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return failure if NoteMotoMecSharedPreferencesModel have field empty`() =
        runTest {
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    NoteMotoMecSharedPreferencesModel()
                )
            )
            val result = repository.save(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMotoMecRepository.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `save - Check return failure if have error in NoteMotoMecRoomDatasource save`() =
        runTest {
            val modelSharedPreferences = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<NoteMotoMecRoomModel>().apply {
                whenever(
                    noteMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    resultFailure(
                        context = "INoteMotoMecRoomDatasource.save",
                        message = "-",
                        cause = Exception()
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
                "INoteMotoMecRepository.save -> INoteMotoMecRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.idHeader,
                1
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val modelSharedPreferences = NoteMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1
            )
            whenever(
                noteMotoMecSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferences)
            )
            val modelCaptor = argumentCaptor<NoteMotoMecRoomModel>().apply {
                whenever(
                    noteMotoMecRoomDatasource.save(capture())
                ).thenReturn(
                    Result.success(true)
                )
            }
            val result = repository.save(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val model = modelCaptor.firstValue
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.idHeader,
                1
            )
        }

}