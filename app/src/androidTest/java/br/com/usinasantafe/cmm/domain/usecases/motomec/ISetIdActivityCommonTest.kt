package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
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
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetIdActivityCommonTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetIdActivityCommon

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_data_if_data_is_empty_header_default() =
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
            val result = usecase(1)
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
                modelHeaderAfter.idActivity,
                1
            )
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter,
                NoteMotoMecSharedPreferencesModel()
            )
        }

    @Test
    fun check_data_change_if_table_has_data_header_default() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore.idActivity,
                1
            )
            val result = usecase(2)
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
                modelHeaderAfter.idActivity,
                2
            )
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter,
                NoteMotoMecSharedPreferencesModel()
            )
        }

    @Test
    fun check_return_data_if_data_is_empty_note_stop() =
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
            val resultNoteGetBefore = itemMotoMecSharedPreferencesDatasource.get()
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
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.idActivity,
                1
            )
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.idActivity,
                1
            )
        }

    @Test
    fun check_data_change_if_table_has_data_note_stop() =
        runTest {
            headerMotoMecSharedPreferencesDatasource.save(
                HeaderMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            itemMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    idActivity = 1
                )
            )
            val resultHeaderGetBefore = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetBefore.isSuccess,
                true
            )
            val modelHeaderBefore = resultHeaderGetBefore.getOrNull()!!
            assertEquals(
                modelHeaderBefore.idActivity,
                1
            )
            val resultNoteGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.idActivity,
                1
            )
            val result = usecase(
                id = 2,
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
            val resultHeaderGetAfter = headerMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultHeaderGetAfter.isSuccess,
                true
            )
            val modelHeaderAfter = resultHeaderGetAfter.getOrNull()!!
            assertEquals(
                modelHeaderAfter.idActivity,
                2
            )
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.idActivity,
                2
            )
        }

    @Test
    fun check_return_failure_if_data_header_is_empty_note_work() =
        runTest {
            itemMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultNoteGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.idActivity,
                null
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
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
                "ISetIdActivity -> IHeaderMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"
            )
        }

    @Test
    fun check_return_failure_if_data_note_is_empty_note_work() =
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
                    statusCon = true
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
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_data_if_data_note_is_empty_note_work() =
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
                    statusCon = true
                )
            )
            itemMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456
                )
            )
            val resultNoteGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.idActivity,
                null
            )
            val listHeaderBefore = headerMotoMecDao.all()
            assertEquals(
                listHeaderBefore.size,
                1
            )
            val modelHeaderBefore = listHeaderBefore.first()
            assertEquals(
                modelHeaderBefore.id,
                1
            )
            val listNoteBefore = itemMotoMecDao.all()
            assertEquals(
                listNoteBefore.size,
                0
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
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.idActivity,
                1
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val modelNoteAfterList = listAfter.first()
            assertEquals(
                modelNoteAfterList.idActivity,
                1
            )
            assertEquals(
                modelNoteAfterList.nroOS,
                123456
            )
            assertEquals(
                modelNoteAfterList.id,
                1
            )
            assertEquals(
                modelNoteAfterList.idHeader,
                1
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
                    statusCon = true
                )
            )
            itemMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    idActivity = 1
                )
            )
            val resultNoteGetBefore = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.idActivity,
                1
            )
            assertEquals(
                modelNoteBefore.nroOS,
                123456
            )
            val listBefore = itemMotoMecDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val listHeaderBefore = headerMotoMecDao.all()
            assertEquals(
                listHeaderBefore.size,
                1
            )
            val modelHeaderBefore = listHeaderBefore.first()
            assertEquals(
                modelHeaderBefore.id,
                1
            )
            val result = usecase(
                id = 2,
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
            val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.idActivity,
                2
            )
            assertEquals(
                modelNoteAfter.nroOS,
                123456
            )
            val listAfter = itemMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val modelNoteAfterList = listAfter.first()
            assertEquals(
                modelNoteAfterList.idActivity,
                2
            )
            assertEquals(
                modelNoteAfterList.nroOS,
                123456
            )
            assertEquals(
                modelNoteAfterList.id,
                1
            )
            assertEquals(
                modelNoteAfterList.idHeader,
                1
            )
        }
}