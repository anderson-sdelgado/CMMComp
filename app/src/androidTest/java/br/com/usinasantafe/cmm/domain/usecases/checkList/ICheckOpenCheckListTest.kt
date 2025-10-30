package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.TypeEquip
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
class ICheckOpenCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckOpenCheckList

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
            )
        }

    @Test
    fun check_return_false_if_id_check_list_is_0() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 0,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val result = usecase()
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
    fun check_return_true_if_id_check_list_not_is_0_and_idTurnCheckListLast_is_null() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val result = usecase()
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
    fun check_return_failure_if_header_moto_mec_is_empty() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                    idTurnCheckListLast = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IMotoMecRepository.getIdTurnHeader -> IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getIdTurn()' on a null object reference"
            )
        }

    @Test
    fun check_return_true_if_idTurnHeader_is_different_idTurnCheckListLast() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                    idTurnCheckListLast = 2
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                true
            )
        }

    @Test
    fun check_return_failure_if_idTurnHeader_is_equal_idTurnCheckListLast_and_dataCheckListLast_is_null() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                    idTurnCheckListLast = 1
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckOpenCheckList -> IConfigRepository.getDateCheckListLast -> IConfigSharedPreferencesDatasource.getDateCheckListLast"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_if_dateCheckListLast_is_different_date_now() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                    idTurnCheckListLast = 1,
                    dateLastCheckList = Date(1750775182000)
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                    dateHourInitial = Date(1748359002000),
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
                true
            )
        }

    @Test
    fun check_return_false_if_dateCheckListLast_is_equal_date_now() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idEquip = 1,
                    idTurnCheckListLast = 1,
                    dateLastCheckList = Date()
                )
            )
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 2,
                        typeEquip = TypeEquip.NORMAL,
                        hourMeter = 0.0,
                        classify = 1,
                        flagMechanic = true,
                        flagTire = true
                    )
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
                    dateHourInitial = Date(),
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
                false
            )
        }
}