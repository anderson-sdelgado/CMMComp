package br.com.usinasantafe.cmm.domain.usecases.motomec

import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ItemMenuModel
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquipMain
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
class ISetNoteMotoMecTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetNoteMotoMec

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

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_room() =
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
                "ISetNoteMotoMec -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference"
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_moto_mec_shared_preferences() =
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
                "ISetNoteMotoMec -> IMotoMecRepository.getNroOSHeader -> IHeaderMotoMecSharedPreferencesDatasource.getNroOS"
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
                "ISetNoteMotoMec -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_success_if_execute_process_successfully_and_stop_is_null() =
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
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            asserts()
        }

    @Test
    fun check_return_success_if_execute_process_successfully_and_stop_is_not_null() =
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
                true
            )
            asserts(idStop = 20)
        }

    private suspend fun initialRegister(level: Int) {

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 10,
                typeEquipMain = TypeEquipMain.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10000.0,
                dateHourInitial = Date(1748359002),
                statusCon = true,
                statusSend = StatusSend.SENT
            )
        )

        if(level == 1) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456
            )
        )

        if(level == 2) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456,
                idActivity = 1
            )
        )

        if(level == 3) return

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

        if(level == 4) return

    }

    private suspend fun asserts(
        idStop: Int? = null
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
            headerRoomModel.typeEquipMain,
            TypeEquipMain.NORMAL
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
            noteSharedPreferencesModel.nroOS,
            123456
        )
        assertEquals(
            noteSharedPreferencesModel.idActivity,
            1
        )
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

    }

}