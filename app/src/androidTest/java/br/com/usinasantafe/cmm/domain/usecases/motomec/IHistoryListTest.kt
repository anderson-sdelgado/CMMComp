package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.FlowNote
import br.com.usinasantafe.cmm.utils.TypeEquip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IHistoryListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IListHistory

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHistoryList -> IMotoMecRepository.noteList -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_empty_if_not_have_note() =
        runTest {

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_stop_room() =
        runTest {

            initialRegister(2)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHistoryList -> IStopRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_activity_room() =
        runTest {

            initialRegister(3)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHistoryList -> IActivityRepository.getById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_correct_if_process_execute_successfully() =
        runTest {

            initialRegister(4)

            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.function,
                FlowNote.STOP
            )
            assertEquals(
                model.descr,
                "CHECKLIST"
            )
            assertEquals(
                model.dateHour,
                "21/10/2025 11:22"
            )
            assertEquals(
                model.detail,
                ""
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.function,
                FlowNote.WORK
            )
            assertEquals(
                model2.descr,
                "TRANSPORTE DE CANA"
            )
            assertEquals(
                model2.dateHour,
                "22/10/2025 11:22"
            )
            assertEquals(
                model2.detail,
                ""
            )
        }

    private suspend fun initialRegister(level: Int) {

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 123456.0,
                statusCon = true
            )
        )

        if (level == 1) return

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                statusCon = true,
                dateHour = Date(1761056520000)
            )
        )

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                statusCon = true,
                dateHour = Date(1761142920000)
            )
        )

        if (level == 2) return

        stopDao.insertAll(
            listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 10,
                    descrStop = "CHECKLIST"
                )
            )
        )

        if (level == 3) return

        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 1,
                    codActivity = 20,
                    descrActivity = "TRANSPORTE DE CANA"
                )
            )
        )

        if (level == 4) return

    }

}