package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IEquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IHeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
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
class ISetHourMeterTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetHourMeter

    @Inject
    lateinit var equipSharedPreferencesDatasource: IEquipSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: IHeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_value_input_is_incorrect() =
        runTest {
            val result = usecase(
                hourMeter = "fdasfdsafd",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> stringToDouble"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.text.ParseException: Unparseable number: \"fdasfdsafd\""
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_shared_preferences() =
        runTest {
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IEquipRepository.updateHourMeter -> IEquipSharedPreferencesDatasource.updateHourMeter"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_shared_preferences_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.getTypeEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getTypeEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultEquipSharedPreferences = equipSharedPreferencesDatasource.get()
            assertEquals(
                resultEquipSharedPreferences.isSuccess,
                true
            )
            val equipSharedPreferences = resultEquipSharedPreferences.getOrThrow()
            assertEquals(
                equipSharedPreferences!!.hourMeter,
                2000.0
            )
        }

    @Test
    fun check_return_FlowApp_REEL_FERT_if_equip_is_TypeEquip_REEL_FERT_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecSharedPreferences(typeEquip = TypeEquip.REEL_FERT)
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.REEL_FERT
            )
            val list = headerMotoMecDao.all()
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun check_return_FlowApp_HEADER_INITIAL_if_idCheckList_is_0_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences(
                idCheckList = 0
            )
            saveHeaderMotoMecSharedPreferences()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_INITIAL
            )
            assertHeaderRoomInitial()
        }

    @Test
    fun check_return_FlowApp_CHECK_LIST_if_idTurnCheckListLast_is_null_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecSharedPreferences()
            saveConfigSharedPreferences(null)
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
            assertHeaderRoomInitial()
        }

    @Test
    fun check_return_FlowApp_CHECK_LIST_if_idTurnCheckListLast_is_different_of_idTurnHeader_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecSharedPreferences()
            saveConfigSharedPreferences(2)
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
            assertHeaderRoomInitial()
        }

    @Test
    fun check_return_FlowApp_HEADER_INITIAL_if_dateCheckListLast_is_equal_of_date_now_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecSharedPreferences()
            saveConfigSharedPreferences()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_INITIAL
            )
            assertHeaderRoomInitial()
        }

    @Test
    fun check_return_FlowApp_CHECK_LIST_if_dateCheckListLast_is_different_of_date_now_and_flow_header_initial() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecSharedPreferences()
            saveConfigSharedPreferences(
                dateLastCheckList = Date(1770924013)
            )
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_INITIAL
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.CHECK_LIST
            )
            assertHeaderRoomInitial()
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room_and_flow_header_finish() =
        runTest {
            saveEquipSharedPreferences()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.setHourMeterFinishHeader -> IHeaderMotoMecRoomDatasource.setHourMeterFinish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setHourMeterFinish(java.lang.Double)' on a null object reference"
            )
            val resultEquipSharedPreferences = equipSharedPreferencesDatasource.get()
            assertEquals(
                resultEquipSharedPreferences.isSuccess,
                true
            )
            val equipSharedPreferences = resultEquipSharedPreferences.getOrThrow()
            assertEquals(
                equipSharedPreferences!!.hourMeter,
                2000.0
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_shared_preferences_and_flow_header_finish() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecRoom()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHourMeter -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultEquipSharedPreferences = equipSharedPreferencesDatasource.get()
            assertEquals(
                resultEquipSharedPreferences.isSuccess,
                true
            )
            val equipSharedPreferences = resultEquipSharedPreferences.getOrThrow()
            assertEquals(
                equipSharedPreferences!!.hourMeter,
                2000.0
            )
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.hourMeterFinish,
                2000.0
            )
            assertEquals(
                headerMotoMecRoom.status,
                Status.OPEN
            )
        }

    @Test
    fun check_return_FlowApp_HEADER_FINISH_if_not_have_performance_to_header_and_flow_header_finish() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecRoom()
            saveHeaderMotoMecSharedPreferences()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.HEADER_FINISH
            )
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.hourMeterFinish,
                2000.0
            )
            assertEquals(
                headerMotoMecRoom.status,
                Status.FINISH
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SEND
            )
        }


    @Test
    fun check_return_FlowApp_PERFORMANCE_if_have_performance_to_header_and_flow_header_finish() =
        runTest {
            saveEquipSharedPreferences()
            saveHeaderMotoMecRoom()
            saveHeaderMotoMecSharedPreferences()
            savePerformanceRoom()
            val result = usecase(
                hourMeter = "2.000,0",
                flowApp = FlowApp.HEADER_FINISH
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowApp.PERFORMANCE
            )
            val resultHeaderMotoMecRoom = headerMotoMecDao.all()
            assertEquals(
                resultHeaderMotoMecRoom.size,
                1
            )
            val headerMotoMecRoom = resultHeaderMotoMecRoom.first()
            assertEquals(
                headerMotoMecRoom.hourMeterFinish,
                2000.0
            )
            assertEquals(
                headerMotoMecRoom.status,
                Status.OPEN
            )
            assertEquals(
                headerMotoMecRoom.statusSend,
                StatusSend.SENT
            )
        }

    private suspend fun savePerformanceRoom(){
        performanceDao.insert(
            PerformanceRoomModel(
                idHeader = 1,
                nroOS = 123456,
                value = 2000.0
            ),
        )
        performanceDao.insert(
            PerformanceRoomModel(
                idHeader = 1,
                nroOS = 123456,
            ),
        )
    }

    private suspend fun saveHeaderMotoMecRoom() {
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
                statusSend = StatusSend.SENT
            )
        )
    }

    private suspend fun saveConfigSharedPreferences(
        idTurnCheckListLast: Int? = 1,
        dateLastCheckList: Date = Date()
    ) {
        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                checkMotoMec = true,
                idServ = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED,
                idTurnCheckListLast = idTurnCheckListLast,
                dateLastCheckList = dateLastCheckList
            )
        )
    }

    private suspend fun saveEquipSharedPreferences(
        idCheckList: Int = 1
    ) {
        equipSharedPreferencesDatasource.save(
            EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = idCheckList,
                typeEquip = TypeEquip.NORMAL,
                hourMeter = 200.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
        )
    }

    private suspend fun saveHeaderMotoMecSharedPreferences(
        typeEquip: TypeEquip = TypeEquip.NORMAL
    ) {
        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                id = 1,
                regOperator = 19759,
                idEquip = 10,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                statusCon = true,
                typeEquip = typeEquip
            )
        )
    }

    private suspend fun assertHeaderRoomInitial(){
        val list = headerMotoMecDao.all()
        assertEquals(
            list.size,
            1
        )
        val model = list.first()
        assertEquals(
            model.id,
            1
        )
        assertEquals(
            model.regOperator,
            19759
        )
        assertEquals(
            model.idEquip,
            10
        )
        assertEquals(
            model.idTurn,
            1
        )
        assertEquals(
            model.nroOS,
            123456
        )
        assertEquals(
            model.idActivity,
            1
        )
        assertEquals(
            model.hourMeterInitial,
            2000.0
        )
        assertEquals(
            model.statusCon,
            true
        )
        assertEquals(
            model.typeEquip,
            TypeEquip.NORMAL
        )
        assertEquals(
            model.hourMeterFinish,
            null
        )
        assertEquals(
            model.idEquipMotorPump,
            null
        )
        assertEquals(
            model.idServ,
            null
        )
    }

}