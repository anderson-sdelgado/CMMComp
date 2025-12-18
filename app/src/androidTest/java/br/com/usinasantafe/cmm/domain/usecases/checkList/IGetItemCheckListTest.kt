package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IGetItemCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: IGetItemCheckList

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var turnDao: TurnDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemCheckListDao: ItemCheckListDao

    @Inject
    lateinit var headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal_and_pos_1() =
        runTest {
            val result = usecase(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetItemCheckList -> IConfigRepository.getNroEquip -> IConfigSharedPreferencesDatasource.getNroEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

//    @Test
//    fun check_return_failure_if_not_have_data_in_header_moto_mec_room_and_pos_1() =
//        runTest {
//
//            initialRegisterPos1(1)
//
//            val result = usecase(1)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList -> IMotoMecRepository.getRegOperatorHeader -> IHeaderMotoMecRoomDatasource.getRegOperatorOpen"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_turn_room_and_pos_1() =
//        runTest {
//
//            initialRegisterPos1(2)
//
//            val result = usecase(1)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList -> ITurnRepository.getNroTurnByIdTurn -> ITurnRoomDatasource.getNroTurnByIdTurn"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel.getNroTurn()' on a null object reference"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_equip_room_and_pos_1() =
//        runTest {
//
//            initialRegisterPos1(3)
//
//            val result = usecase(1)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_item_check_list_room_and_pos_1() =
//        runTest {
//
//            initialRegisterPos1(4)
//
//            val result = usecase(1)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"
//            )
//        }
//
//    @Test
//    fun check_return_correct_if_process_is_success_and_pos_1() =
//        runTest {
//
//            initialRegisterPos1(5)
//
//            val result = usecase(1)
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            val model = result.getOrNull()!!
//            assertEquals(
//                model.id,
//                1
//            )
//            assertEquals(
//                model.descr,
//                "Teste 1"
//            )
//
//            val resultGet = headerCheckListSharedPreferencesDatasource.get()
//            assertEquals(
//                resultGet.isSuccess,
//                true
//            )
//            val headerModel = resultGet.getOrNull()!!
//            assertEquals(
//                headerModel.regOperator,
//                123465
//            )
//            assertEquals(
//                headerModel.nroEquip,
//                2200L
//            )
//            assertEquals(
//                headerModel.nroTurn,
//                2
//            )
//
//
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_config_internal_and_pos_greater_than_1() =
//        runTest {
//            val result = usecase(2)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_equip_room_and_pos_greater_than_1() =
//        runTest {
//
//            initialRegisterPosGreater1(1)
//
//            val result = usecase(2)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
//            )
//        }
//
//    @Test
//    fun check_return_failure_if_not_have_data_in_item_check_list_room_and_pos_greater_than_1() =
//        runTest {
//
//            initialRegisterPosGreater1(2)
//
//            val result = usecase(2)
//            assertEquals(
//                result.isFailure,
//                true
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.message,
//                "IGetItemCheckList"
//            )
//            assertEquals(
//                result.exceptionOrNull()!!.cause.toString(),
//                "java.lang.IndexOutOfBoundsException: Index 1 out of bounds for length 0"
//            )
//        }
//
//    @Test
//    fun check_return_correct_if_process_is_success_and_pos_greater_than_1() =
//        runTest {
//
//            initialRegisterPosGreater1(3)
//
//            val result = usecase(2)
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            val model = result.getOrNull()!!
//            assertEquals(
//                model.id,
//                2
//            )
//            assertEquals(
//                model.descr,
//                "Teste 2"
//            )
//        }
//
//    private suspend fun initialRegisterPos1(level: Int) {
//
//        configSharedPreferencesDatasource.save(
//            ConfigSharedPreferencesModel(
//                number = 16997417840,
//                password = "12345",
//                checkMotoMec = true,
//                idServ = 1,
//                version = "1.0",
//                app = "PMM",
//                flagUpdate = FlagUpdate.UPDATED
//            )
//        )
//
//        if(level == 1) return
//
//        headerMotoMecDao.insert(
//            HeaderMotoMecRoomModel(
//                regOperator = 123465,
//                idEquip = 1,
//                typeEquipMain = TypeEquipMain.NORMAL,
//                idTurn = 1,
//                nroOS = 123456,
//                idActivity = 1,
//                hourMeterInitial = 10.0,
//                dateHourInitial = Date(1748359002),
//                statusCon = true
//            )
//        )
//
//        if(level == 2) return
//
//        turnDao.insertAll(
//            listOf(
//                TurnRoomModel(
//                    idTurn = 1,
//                    codTurnEquip = 1,
//                    nroTurn = 2,
//                    descrTurn = "Turno 1"
//                )
//            )
//        )
//
//        if(level == 3) return
//
//        equipDao.insertAll(
//            listOf(
//                EquipRoomModel(
//                    id = 1,
//                    nro = 2200,
//                    codClass = 1,
//                    descrClass = "TRATOR",
//                    codTurnEquip = 1,
//                    idCheckList = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    hourMeter = 5000.0,
//                    classify = 1,
//                    flagMechanic = true,
//                    flagTire = true
//                )
//            )
//        )
//
//        if(level == 4) return
//
//        itemCheckListDao.insertAll(
//            listOf(
//                ItemCheckListRoomModel(
//                    idItemCheckList = 1,
//                    descrItemCheckList = "Teste 1",
//                    idCheckList = 1
//                ),
//                ItemCheckListRoomModel(
//                    idItemCheckList = 2,
//                    descrItemCheckList = "Teste 2",
//                    idCheckList = 1
//                ),
//                ItemCheckListRoomModel(
//                    idItemCheckList = 3,
//                    descrItemCheckList = "Teste 3",
//                    idCheckList = 1
//                )
//            )
//        )
//
//        if(level == 5) return
//
//    }
//
//    private suspend fun initialRegisterPosGreater1(level: Int) {
//
//        configSharedPreferencesDatasource.save(
//            ConfigSharedPreferencesModel(
//                number = 16997417840,
//                nroEquip = 2200,
//                password = "12345",
//                idEquip = 1,
//                checkMotoMec = true,
//                idServ = 1,
//                version = "1.0",
//                app = "PMM",
//                flagUpdate = FlagUpdate.UPDATED
//            )
//        )
//
//        if(level == 1) return
//
//        equipDao.insertAll(
//            listOf(
//                EquipRoomModel(
//                    id = 1,
//                    nro = 2200,
//                    codClass = 1,
//                    descrClass = "TRATOR",
//                    codTurnEquip = 1,
//                    idCheckList = 1,
//                    typeEquipMain = TypeEquipMain.NORMAL,
//                    hourMeter = 5000.0,
//                    classify = 1,
//                    flagMechanic = true,
//                    flagTire = true
//                )
//            )
//        )
//
//        if(level == 2) return
//
//        itemCheckListDao.insertAll(
//            listOf(
//                ItemCheckListRoomModel(
//                    idItemCheckList = 1,
//                    descrItemCheckList = "Teste 1",
//                    idCheckList = 1
//                ),
//                ItemCheckListRoomModel(
//                    idItemCheckList = 2,
//                    descrItemCheckList = "Teste 2",
//                    idCheckList = 1
//                ),
//                ItemCheckListRoomModel(
//                    idItemCheckList = 3,
//                    descrItemCheckList = "Teste 3",
//                    idCheckList = 1
//                )
//            )
//        )
//
//        if(level == 3) return
//
//    }

}