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
import br.com.usinasantafe.cmm.external.room.dao.variable.MechanicDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.MechanicRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquipMain
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.utils.dataMenu
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    lateinit var itemMotoMecDao: ItemMotoMecDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var mechanicDao: MechanicDao

    ////////////////////////////////////////// PMM ////////////////////////////////////////////////

    @Test
    fun pmm_check_msg_if_not_have_data_in_config_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_msg_if_not_have_id_equip_in_moto_mec_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_msg_if_not_have_equip_in_moto_mec_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getId -> java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_msg_if_not_have_data_in_equip_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IListItemMenu.pmmList -> IEquipRepository.getTypeEquipByIdEquip -> IEquipRoomDatasource.getTypeEquipByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.lib.TypeEquip br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getTypeEquip()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_msg_if_not_have_id_activity_in_header_moto_mec_shared_preferences() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IListItemMenu.pmmList -> IMotoMecRepository.getIdActivityHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdActivity -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_msg_if_not_have_item_menu_room() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(5)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pmm_check_return_list_if_menu_basic() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            registerPMM(6)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun pmm_check_return_list_if_menu_is_all_but_without_reel_and_equip_normal() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            registerPMM(
                level = 6,
                flagMechanic = true,
                flagTire = true
            )
            registerSecPMM(1)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun pmm_check_return_list_if_menu_is_all_and_equip_normal_() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            registerPMM(
                level = 6,
                flagMechanic = true,
                flagTire = true
            )
            registerSecPMM(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun pmm_check_return_list_if_menu_is_all_and_equip_fert() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            registerPMM(
                level = 6,
                flagMechanic = true,
                flagTire = true,
                typeEquipMain = TypeEquipMain.FERT
            )
            registerSecPMM(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun pmm_check_return_list_if_menu_is_all_and_equip_fert_and_return_list() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            registerPMM(
                level = 6,
                flagMechanic = true,
                flagTire = true,
                typeEquipMain = TypeEquipMain.FERT,
                idEquip = 10
            )
            registerSecPMM(2)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun pmm_check_msg_in_click_if_have_note_mechanic_open() =
        runTest(
            timeout = 2.minutes
        ) {

            hiltRule.inject()

            registerPMM(
                level = 6,
                flagMechanic = true,
                flagTire = true,
                typeEquipMain = TypeEquipMain.FERT,
                idEquip = 10
            )
            registerSecPMM(3)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("TRABALHANDO").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("POR FAVOR, FINALIZE O APONTAMENTO DE MANUTENÇÃO PARA INICIAR OUTRO APONTAMENTO.")

            composeTestRule.waitUntilTimeout()

        }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////// ECM ////////////////////////////////////////////////

    @Test
    fun ecm_check_msg_failure_if_not_have_data_in_item_menu_room() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun ecm_check_msg_failure_list_if_not_have_data_in_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerECM(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun ecm_check_msg_failure_list_if_not_have_data_in_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerECM(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun ecm_check_msg_failure_list_if_not_have_data_in_equip_room() =
        runTest {

            hiltRule.inject()

            registerECM(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun ecm_check_return_list_if_have_data() =
        runTest {

            hiltRule.inject()

            registerECM(4)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////// PCOMP ////////////////////////////////////////////////

    @Test
    fun pcomp_check_msg_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> IListItemMenu -> IMotoMecRepository.getFlowCompostingHeader -> IHeaderMotoMecRoomDatasource.getFlowComposting -> java.lang.NullPointerException: Attempt to invoke virtual method 'br.com.usinasantafe.cmm.lib.FlowComposting br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getFlowComposting()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_input_check_msg_failure_if_not_have_data_in_item_menu_room() =
        runTest {

            hiltRule.inject()

            registerPCOMP(1, FlowComposting.INPUT)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_input_check_msg_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerPCOMP(2, FlowComposting.INPUT)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_input_check_msg_failure_if_not_have_data_in_header_moto_mec_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerPCOMP(3, FlowComposting.INPUT)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_input_check_msg_failure_if_not_have_data_in_equip_room() =
        runTest {

            hiltRule.inject()

            registerPCOMP(4, FlowComposting.INPUT)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.descrEquip -> IGetDescrEquip -> IEquipRepository.getDescrByIdEquip -> IEquipRoomDatasource.getDescrByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'long br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getNro()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)


        }

    @Test
    fun pcomp_input_check_return_list_if_have_data() =
        runTest {

            hiltRule.inject()

            registerPCOMP(5, FlowComposting.INPUT)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }


    @Test
    fun pcomp_compound_check_msg_failure_if_not_have_data_in_item_menu_room() =
        runTest {

            hiltRule.inject()

            registerPCOMP(1, FlowComposting.COMPOUND)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.menuList -> listItemMenu -> EmptyList!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_compound_check_msg_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerPCOMP(2, FlowComposting.COMPOUND)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IConfigRepository.getIdEquip -> IConfigSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_compound_check_msg_failure_if_not_have_data_in_header_moto_mec_shared_preferences() =
        runTest {

            hiltRule.inject()

            registerPCOMP(3, FlowComposting.COMPOUND)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteScreenKt.MenuNoteScreen -> MenuNoteViewModel.flowEquipNote -> IGetFlowEquip -> IMotoMecRepository.getIdEquipHeader -> IHeaderMotoMecSharedPreferencesDatasource.getIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun pcomp_compound_check_msg_failure_if_not_have_data_in_equip_room() =
        runTest {

            hiltRule.inject()

            registerPCOMP(4, FlowComposting.COMPOUND)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.descrEquip -> IGetDescrEquip -> IEquipRepository.getDescrByIdEquip -> IEquipRoomDatasource.getDescrByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'long br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getNro()' on a null object reference")

            composeTestRule.waitUntilTimeout(3_000)


        }

    @Test
    fun pcomp_compound_check_return_list_if_have_data() =
        runTest {

            hiltRule.inject()

            registerPCOMP(5, FlowComposting.COMPOUND)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun setContent() {
        composeTestRule.setContent {
            MenuNoteScreen(
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {},
                onNavListReel = {},
                onNavProduct = {},
                onNavWill = {},
                onNavMenuCertificate = {},
                onNavHistory = {},
                onNavTrailer = {},
                onNavEquipTire = {},
                onNavImplement = {},
                onNavOSMechanical = {},
                onNavTranshipment = {},
                onNavOSListPerformance = {},
                onNavUncouplingTrailer = {},
                onNavOSListFertigation = {},
                onNavInfoLoadingCompound = {},
                onNavInfoLocalSugarcaneLoading = {}
            )
        }
    }

    private suspend fun registerPMM(
        level: Int,
        typeEquipMain: TypeEquipMain = TypeEquipMain.NORMAL,
        flagMechanic: Boolean = false,
        flagTire: Boolean = false,
        idEquip: Int = 20,
    ) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
            )
        )

        if (level == 1) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 2) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquipMain = TypeEquipMain.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
            )
        )

        if (level == 3) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 20,
                    nro = 2200L,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    typeEquip = TypeEquipSecondary.REEL
                )
            )
        )

        if (level == 4) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
                idActivity = 1
            )
        )

        if (level == 5) return

        val gson = Gson()
        val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
        val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
        itemMenuDao.insertAll(
            itemMenuRoomModel
        )

        if (level == 6) return

    }

    private suspend fun registerSecPMM(level: Int) {

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

        itemMotoMecDao.insert(
            ItemMotoMecRoomModel(
                id = 1,
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                statusCon = true
            )
        )

        if (level == 2) return

        mechanicDao.insert(
            MechanicRoomModel(
                idHeader = 1,
                os = 123456,
                item = 1,
                dateHourFinish = null
            )
        )
        mechanicDao.insert(
            MechanicRoomModel(
                idHeader = 2,
                os = 123456,
                item = 1,
                dateHourFinish = null
            )
        )
        mechanicDao.insert(
            MechanicRoomModel(
                idHeader = 1,
                os = 123456,
                item = 1,
                dateHourFinish = Date()
            )
        )

        if (level == 3) return

    }

    private suspend fun registerECM(level: Int){

        val gson = Gson()
        val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
        val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
        itemMenuDao.insertAll(
            itemMenuRoomModel
        )

        if (level == 1) return

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
            )
        )

        if (level == 2) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 3) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 20,
                    nro = 2200L,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    typeEquip = TypeEquipSecondary.REEL
                ),
            )
        )

        if (level == 4) return

    }

    private suspend fun registerPCOMP(
        level: Int,
        flowComposting: FlowComposting
    ){

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                id = 1,
                regOperator = 19759,
                idEquip = 20,
                typeEquipMain = TypeEquipMain.NORMAL,
                flowComposting = flowComposting,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 1000.0,
                statusCon = true
            )
        )

        if (level == 1) return

        val gson = Gson()
        val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
        val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
        itemMenuDao.insertAll(
            itemMenuRoomModel
        )

        if (level == 2) return

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                app = "PMM",
                version = "1.00",
                checkMotoMec = false,
                idServ = 1,
            )
        )

        if (level == 3) return

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 20,
            )
        )

        if (level == 4) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 20,
                    nro = 2200L,
                    codClass = 1,
                    descrClass = "CAMINHAO",
                    typeEquip = TypeEquipSecondary.REEL
                ),
            )
        )

        if (level == 5) return

    }


}