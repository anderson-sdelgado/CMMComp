package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.TypeActivity
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class MenuNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemMenuDao: ItemMenuDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var functionStopDao: FunctionStopDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMechanicDao: NoteMechanicDao

    @Test
    fun check_msg_if_not_have_data_in_config_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.descrEquip -> IGetDescrEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_if_not_have_data_in_moto_mec_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.descrEquip -> IGetDescrEquip -> IEquipRepository.getDescrByIdEquip -> IEquipRoomDatasource.getDescrByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'long br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getNro()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_if_not_have_data_in_equip_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IEquipRepository.getTypeEquipByIdEquip -> IEquipRoomDatasource.getTypeEquipByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.utils.TypeEquip br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getTypeEquip()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_if_not_have_id_activity_in_config_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_if_not_have_data_in_header_moto_mec_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)
            initialRegisterSec(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IMotoMecRepository.checkNoteHasByIdStop -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen -> java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_msg_list_if_not_have_data_in_item_menu_pmm_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_list_if_menu_basic() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(5)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_normal() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true
            )
            initialRegisterSec(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_fert() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true,
                typeEquip = TypeEquip.FERT
            )
            initialRegisterSec(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_list_if_menu_is_all_and_equip_fert_and_return_list() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true,
                typeEquip = TypeEquip.FERT,
                idEquip = 10
            )
            initialRegisterSec(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_msg_in_click_if_have_note_mechanic_open() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            initialRegister(
                level = 5,
                flagMechanic = true,
                flagTire = true,
                typeEquip = TypeEquip.FERT,
                idEquip = 10
            )
            initialRegisterSec(3)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("TRABALHANDO").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("POR FAVOR, FINALIZE O APONTAMENTO DE MANUTENÇÃO PARA INICIAR OUTRO APONTAMENTO.")

            composeTestRule.waitUntilTimeout()

        }

    private fun setContent() {
        composeTestRule.setContent {
            MenuNoteScreen(
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {}
            )
        }
    }

    private suspend fun initialRegister(
        level: Int,
        typeEquip: TypeEquip = TypeEquip.NORMAL,
        flagMechanic: Boolean = false,
        flagTire: Boolean = false,
        idEquip: Int = 20,
    ) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                nroEquip = 310,
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
                idEquip = idEquip
            )
        )

        if (level == 1) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 2) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 20,
                    nro = 2200L,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = typeEquip,
                    hourMeter = 1000.0,
                    classify = 1,
                    flagMechanic = flagMechanic,
                    flagTire = flagTire,
                )
            )
        )

        if (level == 3) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
                idActivity = 1
            )
        )

        if (level == 4) return

        itemMenuDao.insertAll(
            listOf(
                ItemMenuRoomModel(
                    id = 1,
                    descr = "TRABALHANDO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 2,
                    descr = "PARADO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 3,
                    descr = "RENDIMENTO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 4,
                    descr = "NOVO TRANSBORDO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 5,
                    descr = "TROCAR IMPLEMENTO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 6,
                    descr = "RECOLHIMENTO MANGUEIRA",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 7,
                    descr = "APONTAR MANUTENÇÃO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 8,
                    descr = "FINALIZAR MANUTENÇÃO",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 9,
                    descr = "CALIBRAGEM DE PNEU",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 10,
                    descr = "TROCA DE PNEU",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 11,
                    descr = "APONTAR CARRETEL",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 12,
                    descr = "FINALIZAR BOLETIM",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                ),
                ItemMenuRoomModel(
                    id = 13,
                    descr = "RETORNAR PRA LISTA",
                    idType = 1,
                    pos = 1,
                    idFunction = 1,
                    idApp = 1,
                )
            )
        )

        if (level == 5) return

    }

    private suspend fun initialRegisterSec(level: Int) {
        functionActivityDao.insertAll(
            listOf(
                FunctionActivityRoomModel(
                    idFunctionActivity = 1,
                    idActivity = 1,
                    typeActivity = TypeActivity.PERFORMANCE
                ),
                FunctionActivityRoomModel(
                    idFunctionActivity = 2,
                    idActivity = 1,
                    typeActivity = TypeActivity.TRANSHIPMENT
                ),
                FunctionActivityRoomModel(
                    idFunctionActivity = 3,
                    idActivity = 1,
                    typeActivity = TypeActivity.IMPLEMENT
                )
            )
        )

        functionStopDao.insertAll(
            listOf(
                FunctionStopRoomModel(
                    idFunctionStop = 1,
                    idStop = 1,
                    typeStop = TypeStop.REEL
                )
            )
        )

        if (level == 1) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
            )
        )

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                statusCon = true
            )
        )

        if (level == 2) return

        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 1,
                os = 123456,
                seq = 1,
                dateHourFinish = null
            )
        )
        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 2,
                os = 123456,
                seq = 1,
                dateHourFinish = null
            )
        )
        noteMechanicDao.insert(
            NoteMechanicRoomModel(
                idHeader = 1,
                os = 123456,
                seq = 1,
                dateHourFinish = Date()
            )
        )

        if (level == 3) return

    }

}