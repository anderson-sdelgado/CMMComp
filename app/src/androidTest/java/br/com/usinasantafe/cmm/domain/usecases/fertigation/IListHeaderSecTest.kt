package br.com.usinasantafe.cmm.domain.usecases.fertigation

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.TypeEquip
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
class IListHeaderSecTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListHeaderSec

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var equipDao: EquipDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IEquipRepository.getIdEquipMain -> IEquipSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_room() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IMotoMecRepository.getIdHeaderByIdEquipAndOpen -> IHeaderMotoMecRoomDatasource.getIdByIdEquipAndOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_empty_list_if_not_have_header_sec() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
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
    fun check_return_failure_if_not_have_data_fielded_in_equip_room() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
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
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1000,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    idHeader = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IListHeaderSec -> IEquipRepository.getNroById -> IEquipRoomDatasource.getNroById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'long br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getNro()' on a null object reference"
            )
        }

    @Test
    fun check_return_empty_list_if_process_execute_success() =
        runTest {
            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
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
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1000,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    idHeader = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1000,
                        nro = 2200,
                        codClass = 1,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.NORMAL
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val item = list[0]
            assertEquals(
                item.id,
                2
            )
            assertEquals(
                item.descr,
                "2200"
            )
        }
}