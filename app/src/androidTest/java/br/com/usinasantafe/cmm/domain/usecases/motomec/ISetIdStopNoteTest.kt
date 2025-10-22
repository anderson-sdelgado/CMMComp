package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISetIdStopNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetIdStopNote

    @Inject
    lateinit var noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
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
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IHeaderMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"
            )
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelNoteAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelNoteAfter.idStop,
                1
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            noteMotoMecSharedPreferencesDatasource.save(
                NoteMotoMecSharedPreferencesModel(
                    nroOS = 123456,
                    idActivity = 1,
                    statusCon = false
                )
            )
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    statusCon = false
                )
            )
            val resultNoteGetBefore = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetBefore.isSuccess,
                true
            )
            val modelNoteBefore = resultNoteGetBefore.getOrNull()!!
            assertEquals(
                modelNoteBefore.nroOS,
                123456
            )
            assertEquals(
                modelNoteBefore.idActivity,
                1
            )
            assertEquals(
                modelNoteBefore.idStop,
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
            val listNoteBefore = noteMotoMecDao.all()
            assertEquals(
                listNoteBefore.size,
                0
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
            val resultNoteGetAfter = noteMotoMecSharedPreferencesDatasource.get()
            assertEquals(
                resultNoteGetAfter.isSuccess,
                true
            )
            val modelSharedPreferencesAfter = resultNoteGetAfter.getOrNull()!!
            assertEquals(
                modelSharedPreferencesAfter.idActivity,
                1
            )
            assertEquals(
                modelSharedPreferencesAfter.nroOS,
                123456
            )
            assertEquals(
                modelSharedPreferencesAfter.idStop,
                1
            )
            assertEquals(
                modelSharedPreferencesAfter.statusCon,
                false
            )
            val listAfter = noteMotoMecDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val modelRoomAfter = listAfter.first()
            assertEquals(
                modelRoomAfter.idStop,
                1
            )
            assertEquals(
                modelRoomAfter.idActivity,
                1
            )
            assertEquals(
                modelRoomAfter.nroOS,
                123456
            )
            assertEquals(
                modelRoomAfter.id,
                1
            )
            assertEquals(
                modelRoomAfter.idHeader,
                1
            )
            assertEquals(
                modelRoomAfter.statusCon,
                false
            )
        }

}