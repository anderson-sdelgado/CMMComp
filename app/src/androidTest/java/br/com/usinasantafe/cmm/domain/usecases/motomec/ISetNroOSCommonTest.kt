package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetNroOSCommonTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNroOS

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_data_if_data_is_empty_header_default() =
        runTest {
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore,
                HeaderMotoMecSharedPreferencesModel()
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroOS,
                123456
            )
        }

    @Test
    fun check_data_change_if_table_has_data_header_default() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                123456
            )
            val result = usecase("456789")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroOS,
                456789
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_room() =
        runTest {
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore,
                HeaderMotoMecSharedPreferencesModel()
            )
            val resultNoteGetBefore = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore,
                NoteMotoMecSharedPreferencesModel()
            )
            val result = usecase(
                nroOS = "123456",
                flowApp = FlowApp.NOTE_WORK
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNroOSCommon -> IMotoMecRepository.setNroOS -> IHeaderMotoMecRoomDatasource.getStatusConByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"
            )
        }

    @Test
    fun check_return_data_if_data_is_empty_note_work() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore,
                HeaderMotoMecSharedPreferencesModel()
            )
            val resultNoteGetBefore = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore,
                NoteMotoMecSharedPreferencesModel()
            )
            val result = usecase(
                nroOS = "123456",
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
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.nroOS,
                123456
            )
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.nroOS,
                123456
            )
            assertEquals(
                modelNoteAfter.statusCon,
                true
            )
        }

    @Test
    fun check_data_change_if_table_has_data_note_work() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = false
                )
            )
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    statusCon = false
                )
            )
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore.nroOS,
                123456
            )
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    statusCon = true
                )
            )
            val resultNoteGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.nroOS,
                123456
            )
            val result = usecase(
                nroOS = "456789",
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
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.nroOS,
                456789
            )
            assertEquals(
                modelHeaderAfter.statusCon,
                false
            )
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.nroOS,
                456789
            )
            assertEquals(
                modelNoteAfter.statusCon,
                false
            )
        }

}