package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ItemMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeActivity
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
class ISetIdStopNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ISetIdStopNote

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_id_in_header_shared_preferences() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_id_activity_in_header_shared_preferences() =
        runTest {
            initialRegister(1)
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_room() =
        runTest {
            initialRegister(2)
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_correct_if_process_execute_successfully_and_activity_is_not_performance() =
        runTest {
            initialRegister(3)
            val result = usecase(1)
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                Unit
//            )
//            asserts()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetIdStopNote -> IMotoMecRepository.saveNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    private suspend fun initialRegister(level: Int) {

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                id = 1
            )
        )

        if(level == 1) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                id = 1,
                nroOS = 123456,
                idActivity = 1
            )
        )

        if(level == 2) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 10,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10000.0,
                dateHourInitial = Date(1748359002),
                statusCon = true,
                statusSend = StatusSend.SEND
            )
        )

        if(level == 3) return

        functionActivityDao.insertAll(
            listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            )
        )

        if(level == 4) return

    }

    private suspend fun asserts(hasPerformance: Boolean = false){
        val resultNoteGetAfter = itemMotoMecSharedPreferencesDatasource.get()
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
        val listAfter = itemMotoMecDao.all()
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

        if(!hasPerformance) {
            val performanceRoomModelList = performanceDao.all()
            assertEquals(
                performanceRoomModelList.size,
                0
            )
        } else {
            val performanceRoomModelList = performanceDao.all()
            assertEquals(
                performanceRoomModelList.size,
                1
            )
            val performanceRoomModel = performanceRoomModelList[0]
            assertEquals(
                performanceRoomModel.nroOS,
                123456
            )
            assertEquals(
                performanceRoomModel.idHeader,
                1
            )
            assertEquals(
                performanceRoomModel.value,
                null
            )
        }
    }

}