package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.Status
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
class ISetHourMeterTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetHourMeter

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }
//
//    @Test
//    fun check_return_failure_if_data_is_empty_header_initial() =
//        runTest {
//            val modelBefore = headerMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                modelBefore.isSuccess,
//                true
//            )
//            assertEquals(
//                modelBefore.getOrNull()!!,
//                HeaderMotoMecSharedPreferencesModel()
//            )
//            val result = usecase("2000,0")
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ISetHourMeter -> IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//            val modelAfter = headerMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                modelAfter.isSuccess,
//                true
//            )
//            assertEquals(
//                modelAfter.getOrNull()!!,
//                HeaderMotoMecSharedPreferencesModel(
//                    hourMeter = 2000.0
//                )
//            )
//        }
//
//    @Test
//    fun check_data_if_execute_successfully_header_initial() =
//        runTest {
//            headerMotoMecSharedPreferencesDatasource.save(
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                )
//            )
//            val headerModelBefore = headerMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                headerModelBefore.isSuccess,
//                true
//            )
//            assertEquals(
//                headerModelBefore.getOrNull()!!,
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 2200,
//                        codClass = 1,
//                        descrClass = "TRATOR",
//                        typeEquip = TypeEquip.REEL_FERT
//                    )
//                )
//            )
//            val listEquipBefore = equipDao.all()
//            assertEquals(
//                listEquipBefore.size,
//                1
//            )
//            val equipBefore = listEquipBefore[0]
//            assertEquals(
//                equipBefore.id,
//                1
//            )
//            val result = usecase("2000,0")
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//            val headerModelAfter = headerMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                headerModelAfter.isSuccess,
//                true
//            )
//            assertEquals(
//                headerModelAfter.getOrNull()!!,
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                    hourMeter = 2000.0
//                )
//            )
//            val listEquipAfter = equipDao.all()
//            assertEquals(
//                listEquipAfter.size,
//                1
//            )
//            val equipAfter = listEquipAfter[0]
//            assertEquals(
//                equipAfter.id,
//                1
//            )
//            val listHeaderAfter = headerMotoMecDao.all()
//            assertEquals(
//                listHeaderAfter.size,
//                1
//            )
//            val headerAfter = listHeaderAfter[0]
//            assertEquals(
//                headerAfter.id,
//                1
//            )
//            assertEquals(
//                headerAfter.regOperator,
//                123456
//            )
//            assertEquals(
//                headerAfter.idEquip,
//                1
//            )
//            assertEquals(
//                headerAfter.hourMeterInitial,
//                2000.0,
//                0.0
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_number_digit_incorrect_header_initial() =
//        runTest {
//            headerMotoMecSharedPreferencesDatasource.save(
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                )
//            )
//            val headerModelBefore = headerMotoMecSharedPreferencesDatasource.get()
//            assertEquals(
//                headerModelBefore.isSuccess,
//                true
//            )
//            assertEquals(
//                headerModelBefore.getOrNull()!!,
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 2200,
//                        codClass = 1,
//                        descrClass = "TRATOR",
//                        typeEquip = TypeEquip.REEL_FERT
//                    )
//                )
//            )
//            val listEquipBefore = equipDao.all()
//            assertEquals(
//                listEquipBefore.size,
//                1
//            )
//            val equipBefore = listEquipBefore[0]
//            assertEquals(
//                equipBefore.id,
//                1
//            )
//            val result = usecase("dd20d00as0")
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ISetHourMeter"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.text.ParseException: Unparseable number: \"dd20d00as0\""
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_data_is_empty_header_finish() =
//        runTest {
//            val result = usecase(
//                hourMeter = "3.000,0",
//                flowApp = FlowApp.HEADER_FINISH
//            )
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ISetMeasure -> IHeaderMotoMecRepository.getIdEquip -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_data_alter_if_execution_successfully_header_finish() =
//        runTest {
//            headerMotoMecDao.insert(
//                HeaderMotoMecRoomModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    typeEquip = TypeEquip.NORMAL,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                    hourMeterInitial = 2000.0,
//                    statusCon = true
//                )
//            )
//            val listHeaderBefore = headerMotoMecDao.all()
//            assertEquals(
//                listHeaderBefore.size,
//                1
//            )
//            val headerBefore = listHeaderBefore[0]
//            assertEquals(
//                headerBefore.id,
//                1
//            )
//            assertEquals(
//                headerBefore.regOperator,
//                123456
//            )
//            assertEquals(
//                headerBefore.idEquip,
//                1
//            )
//            assertEquals(
//                headerBefore.hourMeterInitial,
//                2000.0,
//                0.0
//            )
//            assertEquals(
//                headerBefore.hourMeterFinish,
//                null
//            )
//            assertEquals(
//                headerBefore.status,
//                Status.OPEN
//            )
//            headerMotoMecSharedPreferencesDatasource.save(
//                HeaderMotoMecSharedPreferencesModel(
//                    regOperator = 123456,
//                    idEquip = 1,
//                    idTurn = 1,
//                    nroOS = 1,
//                    idActivity = 1,
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 2200,
//                        codClass = 1,
//                        descrClass = "TRATOR",
//                        typeEquip = TypeEquip.REEL_FERT
//                    )
//                )
//            )
//            val listEquipBefore = equipDao.all()
//            assertEquals(
//                listEquipBefore.size,
//                1
//            )
//            val equipBefore = listEquipBefore[0]
//            assertEquals(
//                equipBefore.id,
//                1
//            )
//            val result = usecase(
//                hourMeter = "3.000,0",
//                flowApp = FlowApp.HEADER_FINISH
//            )
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//            val listHeaderAfter = headerMotoMecDao.all()
//            assertEquals(
//                listHeaderAfter.size,
//                1
//            )
//            val headerAfter = listHeaderAfter[0]
//            assertEquals(
//                headerAfter.id,
//                1
//            )
//            assertEquals(
//                headerAfter.regOperator,
//                123456
//            )
//            assertEquals(
//                headerAfter.idEquip,
//                1
//            )
//            assertEquals(
//                headerAfter.hourMeterInitial,
//                2000.0,
//                0.0
//            )
//            assertEquals(
//                headerAfter.hourMeterFinish!!,
//                3000.0,
//                0.0
//            )
//            assertEquals(
//                headerAfter.status,
//                Status.CLOSE
//            )
//            val listEquipAfter = equipDao.all()
//            assertEquals(
//                listEquipAfter.size,
//                1
//            )
//            val equipAfter = listEquipAfter[0]
//            assertEquals(
//                equipAfter.id,
//                1
//            )
//        }



////////////////////////////////CHECK OPEN CHECKLIST //////////////////////////////////////////////



//    @Test
//    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
//        runTest {
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckOpenCheckList -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_equip_room() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckOpenCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
//            )
//        }
//
//    @Test
//    fun check_return_false_if_id_check_list_is_0() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 0,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                false
//            )
//        }
//
//    @Test
//    fun check_return_true_if_id_check_list_not_is_0_and_idTurnCheckListLast_is_null() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_header_moto_mec_is_empty() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1,
//                    idTurnCheckListLast = 1
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckOpenCheckList -> IMotoMecRepository.getIdTurnHeader -> IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getIdTurn()' on a null object reference"
//            )
//        }
//
//    @Test
//    fun check_return_true_if_idTurnHeader_is_different_idTurnCheckListLast() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1,
//                    idTurnCheckListLast = 2
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            headerMotoMecDao.insert(
//                HeaderMotoMecRoomModel(
//                    regOperator = 123465,
//                    idEquip = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    idTurn = 1,
//                    nroOS = 123456,
//                    idActivity = 1,
//                    hourMeterInitial = 10.0,
//                    dateHourInitial = Date(1748359002),
//                    statusCon = true
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_idTurnHeader_is_equal_idTurnCheckListLast_and_dataCheckListLast_is_null() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1,
//                    idTurnCheckListLast = 1
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            headerMotoMecDao.insert(
//                HeaderMotoMecRoomModel(
//                    regOperator = 123465,
//                    idEquip = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    idTurn = 1,
//                    nroOS = 123456,
//                    idActivity = 1,
//                    hourMeterInitial = 10.0,
//                    dateHourInitial = Date(1748359002),
//                    statusCon = true
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "ICheckOpenCheckList -> IConfigRepository.getDateCheckListLast -> IConfigSharedPreferencesDatasource.getDateCheckListLast"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_return_true_if_dateCheckListLast_is_different_date_now() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1,
//                    idTurnCheckListLast = 1,
//                    dateLastCheckList = Date(1750775182000)
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            headerMotoMecDao.insert(
//                HeaderMotoMecRoomModel(
//                    regOperator = 123465,
//                    idEquip = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    idTurn = 1,
//                    nroOS = 123456,
//                    idActivity = 1,
//                    hourMeterInitial = 10.0,
//                    dateHourInitial = Date(1748359002000),
//                    statusCon = true
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                true
//            )
//        }
//
//    @Test
//    fun check_return_false_if_dateCheckListLast_is_equal_date_now() =
//        runTest {
//            configSharedPreferencesDatasource.save(
//                ConfigSharedPreferencesModel(
//                    idEquip = 1,
//                    idTurnCheckListLast = 1,
//                    dateLastCheckList = Date()
//                )
//            )
//            equipDao.insertAll(
//                listOf(
//                    EquipRoomModel(
//                        id = 1,
//                        nro = 10,
//                        codClass = 20,
//                        descrClass = "TRATOR",
//                        codTurnEquip = 1,
//                        idCheckList = 2,
//                        typeEquipMain = TypeEquipMain.NORMAL,
//                        hourMeter = 0.0,
//                        classify = 1,
//                        flagMechanic = true,
//                        flagTire = true
//                    )
//                )
//            )
//            headerMotoMecDao.insert(
//                HeaderMotoMecRoomModel(
//                    regOperator = 123465,
//                    idEquip = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    idTurn = 1,
//                    nroOS = 123456,
//                    idActivity = 1,
//                    hourMeterInitial = 10.0,
//                    dateHourInitial = Date(),
//                    statusCon = true
//                )
//            )
//            val result = usecase()
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            assertEquals(
//                result.getOrNull()!!,
//                false
//            )
//        }
}