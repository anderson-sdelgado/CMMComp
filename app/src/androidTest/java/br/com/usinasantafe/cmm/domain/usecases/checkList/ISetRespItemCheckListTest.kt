package br.com.usinasantafe.cmm.domain.usecases.checkList

import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.variable.RespItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.RespItemCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.RespItemCheckListSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
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
class ISetRespItemCheckListTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetRespItemCheckList

    @Inject
    lateinit var respItemCheckListSharedPreferencesDatasource: RespItemCheckListSharedPreferencesDatasource

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemCheckListDao: ItemCheckListDao

    @Inject
    lateinit var headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource

    @Inject
    lateinit var headerCheckListDao: HeaderCheckListDao

    @Inject
    lateinit var respItemCheckListDao: RespItemCheckListDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {
            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val resultListBefore = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultListBefore.isSuccess,
                true
            )
            val listBefore = resultListBefore.getOrNull()
            assertEquals(
                listBefore!!.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.idItem,
                1
            )
            assertEquals(
                modelBefore.option,
                OptionRespCheckList.ANALYZE
            )
            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultListAfter = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultListAfter.isSuccess,
                true
            )
            val listAfter = resultListAfter.getOrNull()
            assertEquals(
                listAfter!!.size,
                1
            )
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.idItem,
                1
            )
            assertEquals(
                modelAfter.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room() =
        runTest {

            initialRegister(1)

            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                1
            )
            val modelAfter = list[0]
            assertEquals(
                modelAfter.idItem,
                1
            )
            assertEquals(
                modelAfter.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_item_check_list_room() =
        runTest {

            initialRegister(2)

            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.saveCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalArgumentException: Field 'nroEquip' cannot be null."
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                1
            )
            val modelAfter = list[0]
            assertEquals(
                modelAfter.idItem,
                1
            )
            assertEquals(
                modelAfter.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_true_if_count_item_check_list_room_greater_than_pos() =
        runTest {

            initialRegister(3)

            val result = usecase(
                pos = 1,
                id = 1,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                1
            )
            val modelAfter = list[0]
            assertEquals(
                modelAfter.idItem,
                1
            )
            assertEquals(
                modelAfter.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences_and_pos_2() =
        runTest {
            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val result = usecase(
                pos = 2,
                id = 2,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idItem,
                1
            )
            assertEquals(
                model1.option,
                OptionRespCheckList.ANALYZE
            )
            val model2 = list[1]
            assertEquals(
                model2.idItem,
                2
            )
            assertEquals(
                model2.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_room_and_pos_2() =
        runTest {

            initialRegister(1)

            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val result = usecase(
                pos = 2,
                id = 2,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference"
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idItem,
                1
            )
            assertEquals(
                model1.option,
                OptionRespCheckList.ANALYZE
            )
            val model2 = list[1]
            assertEquals(
                model2.idItem,
                2
            )
            assertEquals(
                model2.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_item_check_list_room_and_pos_2() =
        runTest {

            initialRegister(2)

            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val result = usecase(
                pos = 2,
                id = 2,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.saveCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalArgumentException: Field 'nroEquip' cannot be null."
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idItem,
                1
            )
            assertEquals(
                model1.option,
                OptionRespCheckList.ANALYZE
            )
            val model2 = list[1]
            assertEquals(
                model2.idItem,
                2
            )
            assertEquals(
                model2.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_failure_if_count_item_check_list_room_equals_pos_and_nroEquip_is_null() =
        runTest {

            initialRegister(3)

            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val result = usecase(
                pos = 2,
                id = 2,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetRespItemCheckList -> ICheckListRepository.saveCheckList"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalArgumentException: Field 'nroEquip' cannot be null."
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()
            assertEquals(
                list!!.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idItem,
                1
            )
            assertEquals(
                model1.option,
                OptionRespCheckList.ANALYZE
            )
            val model2 = list[1]
            assertEquals(
                model2.idItem,
                2
            )
            assertEquals(
                model2.option,
                OptionRespCheckList.REPAIR
            )
        }

    @Test
    fun check_return_false_if_count_item_check_list_room_equals_pos() =
        runTest {

            initialRegister(4)

            respItemCheckListSharedPreferencesDatasource.save(
                RespItemCheckListSharedPreferencesModel(
                    idItem = 1,
                    option = OptionRespCheckList.ANALYZE
                )
            )
            val result = usecase(
                pos = 2,
                id = 2,
                option = OptionRespCheckList.REPAIR
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val resultBoolean = result.getOrNull()!!
            assertEquals(
                resultBoolean,
                false
            )
            val resultList = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultList.isSuccess,
                true
            )
            val list = resultList.getOrNull()!!
            assertEquals(
                list.size,
                0
            )

            val headerRoomModelList = headerCheckListDao.all()
            assertEquals(
                headerRoomModelList.size,
                1
            )
            val modelHeader = headerRoomModelList[0]
            assertEquals(
                modelHeader.nroEquip,
                2200
            )
            assertEquals(
                modelHeader.regOperator,
                19759
            )
            assertEquals(
                modelHeader.nroTurn,
                2
            )
            val respItemCheckListRoomModelList = respItemCheckListDao.all()
            assertEquals(
                respItemCheckListRoomModelList.size,
                2
            )
            val modelRespItemCheckList1 = respItemCheckListRoomModelList[0]
            assertEquals(
                modelRespItemCheckList1.id,
                1
            )
            assertEquals(
                modelRespItemCheckList1.idItem,
                1
            )
            assertEquals(
                modelRespItemCheckList1.idHeader,
                1
            )
            assertEquals(
                modelRespItemCheckList1.option,
                OptionRespCheckList.ANALYZE
            )
            val modelRespItemCheckList2 = respItemCheckListRoomModelList[1]
            assertEquals(
                modelRespItemCheckList2.id,
                2
            )
            assertEquals(
                modelRespItemCheckList2.idItem,
                2
            )
            assertEquals(
                modelRespItemCheckList2.idHeader,
                1
            )
            assertEquals(
                modelRespItemCheckList2.option,
                OptionRespCheckList.REPAIR
            )
        }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 10,
                checkMotoMec = true,
                idServ = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if(level == 1) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
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
        )

        if(level == 2) return

        itemCheckListDao.insertAll(
            listOf(
                ItemCheckListRoomModel(
                    idCheckList = 1,
                    descrItemCheckList = "Item 1",
                    idItemCheckList = 1
                ),
                ItemCheckListRoomModel(
                    idCheckList = 1,
                    descrItemCheckList = "Item 2",
                    idItemCheckList = 2
                )
            )
        )

        if(level == 3) return

        headerCheckListSharedPreferencesDatasource.save(
            HeaderCheckListSharedPreferencesModel(
                nroEquip = 2200,
                regOperator = 19759,
                nroTurn = 2,
                dateHour = Date()
            )
        )

        if(level == 4) return

    }

}