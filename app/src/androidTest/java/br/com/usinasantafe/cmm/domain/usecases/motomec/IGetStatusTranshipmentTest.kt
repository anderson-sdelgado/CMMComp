package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.StatusTranshipment
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
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class IGetStatusTranshipmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetStatusTranshipment

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

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
                "IGetStatusTranshipment -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_WITHOUT_NOTE_if_not_have_data_in_note_moto_mec_room() =
        runTest {
            initialRegister(1)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.WITHOUT_NOTE
            )
        }

    @Test
    fun check_return_WITHOUT_NOTE_if_not_have_note_moto_mec_in_header() =
        runTest {
            initialRegister(2)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.WITHOUT_NOTE
            )
        }

    @Test
    fun check_return_TIME_INVALID_if_idEquipTrans_is_not_null_and_date_hour_is_minor_than_date_hour_initial() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(3)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.TIME_INVALID
            )
        }

    @Test
    fun check_return_Ok_if_idEquipTrans_is_null() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(4)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.OK
            )
        }

    @Test
    fun check_return_OK_if_idEquipTrans_is_not_null_and_date_hour_is_greater_than_date_hour_initial() =
        runTest(
            timeout = 2.minutes
        ) {
            initialRegister(5)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                StatusTranshipment.OK
            )
        }

    private suspend fun initialRegister(level: Int) {

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
                statusCon = true
            )
        )

        if(level == 1) return

        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                idHeader = 2,
                nroOS = 123456,
                idActivity = 1,
                idStop = 2,
            )
        )
        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = null,
            )
        )

        if(level == 2) return

        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                idEquipTrans = 1,
                dateHour = Date(),
                statusCon = true
            )
        )

        if(level == 3) return

        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                dateHour = Date(),
                statusCon = true
            )
        )

        if(level == 4) return

        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                idEquipTrans = 10,
                dateHour = Date(1764342015000),
                statusCon = true
            )
        )

        if(level == 5) return
    }

}