package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeActivity
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
class ISetNoteTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNote

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Inject
    lateinit var rItemMenuStopDao: RItemMenuStopDao

    @Inject
    lateinit var performanceDao: PerformanceDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_id_in_header_moto_mec_shared_preferences() =
        runTest {
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecSharedPreferencesDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_nro_os_in_header_moto_mec_shared_preferences() =
        runTest {
            initialRegister(1)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_id_activity_in_header_moto_mec_shared_preferences() =
        runTest {
            initialRegister(2)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {
            initialRegister(3)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetNote -> IMotoMecRepository.saveNote -> IHeaderMotoMecRoomDatasource.setSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'void br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setStatusSend(br.com.usinasantafe.cmm.lib.StatusSend)' on a null object reference"
            )
        }

    @Test
    fun check_return_success_if_execute_process_successfully_and_stop_is_null() =
        runTest {
            initialRegister(4)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            asserts()
        }

    @Test
    fun check_return_success_if_execute_process_successfully_and_stop_is_not_null() =
        runTest {
            initialRegister(5)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            asserts(idStop = 20)
        }

    @Test
    fun check_return_success_if_execute_process_successfully_and_stop_is_not_null_and_insert_performance() =
        runTest {
            initialRegister(6)
            val result = usecase(
                ItemMenuModel(
                    id = 1,
                    descr = "Item 1",
                    function = 1 to ITEM_NORMAL,
                    type = 1 to ITEM_NORMAL,
                    app = 2 to ECM
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            asserts(
                idStop = 20,
                hasPerformance = true
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
                nroOS = 123456
            )
        )

        if(level == 2) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                id = 1,
                nroOS = 123456,
                idActivity = 1
            )
        )

        if(level == 3) return

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

        if(level == 4) return

        rItemMenuStopDao.insertAll(
            listOf(
                RItemMenuStopRoomModel(
                    id = 1,
                    idFunction = 1,
                    idApp = 2,
                    idStop = 20
                )
            )
        )

        if(level == 5) return

        functionActivityDao.insertAll(
            listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                )
            )
        )

        if(level == 6) return

    }

    private suspend fun asserts(
        idStop: Int? = null,
        hasPerformance: Boolean = false
    ){

        val headerRoomModelList = headerMotoMecDao.all()
        assertEquals(
            headerRoomModelList.size,
            1
        )
        val headerRoomModel = headerRoomModelList[0]
        assertEquals(
            headerRoomModel.regOperator,
            19759
        )
        assertEquals(
            headerRoomModel.idEquip,
            10
        )
        assertEquals(
            headerRoomModel.typeEquip,
            TypeEquip.NORMAL
        )
        assertEquals(
            headerRoomModel.idTurn,
            1
        )
        assertEquals(
            headerRoomModel.nroOS,
            123456
        )
        assertEquals(
            headerRoomModel.idActivity,
            1
        )
        assertEquals(
            headerRoomModel.hourMeterInitial,
            10000.0
        )
        assertEquals(
            headerRoomModel.dateHourInitial,
            Date(1748359002)
        )
        assertEquals(
            headerRoomModel.statusCon,
            true
        )
        assertEquals(
            headerRoomModel.statusSend,
            StatusSend.SEND
        )

        val resultNoteSharedPreferencesModel = itemMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultNoteSharedPreferencesModel.isSuccess,
            true
        )
        val noteSharedPreferencesModel = resultNoteSharedPreferencesModel.getOrNull()!!
        assertEquals(
            noteSharedPreferencesModel.idStop,
            idStop
        )

        val noteRoomModelList = itemMotoMecDao.all()
        assertEquals(
            noteRoomModelList.size,
            1
        )
        val noteRoomModel = noteRoomModelList[0]
        assertEquals(
            noteRoomModel.nroOS,
            123456
        )
        assertEquals(
            noteRoomModel.idActivity,
            1
        )
        assertEquals(
            noteRoomModel.idStop,
            idStop
        )
        assertEquals(
            noteRoomModel.idHeader,
            1
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