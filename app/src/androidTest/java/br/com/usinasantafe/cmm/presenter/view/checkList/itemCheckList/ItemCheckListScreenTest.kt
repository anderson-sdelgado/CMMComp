package br.com.usinasantafe.cmm.presenter.view.checkList.itemCheckList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.RespItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.RespItemCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate.QuestionUpdateCheckListScreen
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ItemCheckListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

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

    @Inject
    lateinit var respItemCheckListSharedPreferencesDatasource: RespItemCheckListSharedPreferencesDatasource

    @Inject
    lateinit var headerCheckListDao: HeaderCheckListDao

    @Inject
    lateinit var respItemCheckListDao: RespItemCheckListDao

    @Test
    fun check_open_screen_and_msg_failure_if_config_shared_preferences_datasource_is_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ItemCheckListViewModel.get -> IGetItemCheckList -> IConfigRepository.getNroEquip -> IConfigSharedPreferencesDatasource.getNroEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_failure_if_header_room_is_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ItemCheckListViewModel.get -> IGetItemCheckList -> IMotoMecRepository.getRegOperatorHeader -> IHeaderMotoMecRoomDatasource.getRegOperatorOpen -> java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getRegOperator()' on a null object reference")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_failure_if_turn_room_is_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ItemCheckListViewModel.get -> IGetItemCheckList -> ITurnRepository.getNroTurnByIdTurn -> ITurnRoomDatasource.getNroTurnByIdTurn -> java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel.getNroTurn()' on a null object reference")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_failure_if_equip_room_is_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ItemCheckListViewModel.get -> IGetItemCheckList -> IEquipRepository.getIdCheckListByIdEquip -> IEquipRoomDatasource.getIdCheckListByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'int br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getIdCheckList()' on a null object reference")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_failure_if_item_check_list_room_is_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ItemCheckListViewModel.get -> IGetItemCheckList -> java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_return_item() =
        runTest {

            hiltRule.inject()

            initialRegister(5)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_note_check_list() =
        runTest {

            hiltRule.inject()

            initialRegister(5)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONFORME (OK)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONFORME (REPARO)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            val resultGetHeader = headerCheckListSharedPreferencesDatasource.get()
            assertEquals(
                resultGetHeader.isSuccess,
                true
            )
            val header = resultGetHeader.getOrNull()!!
            assertEquals(
                header.regOperator,
                123465
            )
            assertEquals(
                header.nroTurn,
                1
            )
            assertEquals(
                header.nroEquip,
                2200
            )

            val resultListResp = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultListResp.isSuccess,
                true
            )
            val listResp = resultListResp.getOrNull()!!
            assertEquals(
                listResp.size,
                2
            )
            val respItemModel1 = listResp[0]
            assertEquals(
                respItemModel1.idItem,
                1
            )
            assertEquals(
                respItemModel1.option,
                OptionRespCheckList.ACCORDING
            )
            val respItemModel2 = listResp[1]
            assertEquals(
                respItemModel2.idItem,
                2
            )
            assertEquals(
                respItemModel2.option,
                OptionRespCheckList.REPAIR
            )

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONFORME (REPARO)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONFORME (OK)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONFORME (ANALISAR)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONFORME (OK)")
                .performClick()

            composeTestRule.waitUntilTimeout()

            val resultListRespFinish = respItemCheckListSharedPreferencesDatasource.list()
            assertEquals(
                resultListRespFinish.isSuccess,
                true
            )
            val listRespFinish = resultListRespFinish.getOrNull()!!
            assertEquals(
                listRespFinish.size,
                0
            )

            val headerListRoom = headerCheckListDao.all()
            assertEquals(
                headerListRoom.size,
                1
            )
            val headerRoom = headerListRoom[0]
            assertEquals(
                headerRoom.regOperator,
                123465
            )
            assertEquals(
                headerRoom.nroTurn,
                1
            )
            assertEquals(
                headerRoom.nroEquip,
                2200
            )
            assertEquals(
                headerRoom.statusSend,
                StatusSend.SEND
            )

            val respListRoom = respItemCheckListDao.all()
            assertEquals(
                respListRoom.size,
                5
            )
            val respRoom1 = respListRoom[0]
            assertEquals(
                respRoom1.id,
                1
            )
            assertEquals(
                respRoom1.idItem,
                1
            )
            assertEquals(
                respRoom1.option,
                OptionRespCheckList.ACCORDING
            )
            val respRoom2 = respListRoom[1]
            assertEquals(
                respRoom2.id,
                2
            )
            assertEquals(
                respRoom2.idItem,
                2
            )
            assertEquals(
                respRoom2.option,
                OptionRespCheckList.REPAIR
            )
            val respRoom3 = respListRoom[2]
            assertEquals(
                respRoom3.id,
                3
            )
            assertEquals(
                respRoom3.idItem,
                3
            )
            assertEquals(
                respRoom3.option,
                OptionRespCheckList.ACCORDING
            )
            val respRoom4 = respListRoom[3]
            assertEquals(
                respRoom4.id,
                4
            )
            assertEquals(
                respRoom4.idItem,
                4
            )
            assertEquals(
                respRoom4.option,
                OptionRespCheckList.ANALYZE
            )
            val respRoom5 = respListRoom[4]
            assertEquals(
                respRoom5.id,
                5
            )
            assertEquals(
                respRoom5.idItem,
                5
            )
            assertEquals(
                respRoom5.option,
                OptionRespCheckList.ACCORDING
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            ItemCheckListScreen(
                onNavMenuNote = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 10,
                checkMotoMec = true,
                idBD = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (level == 1) return

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 123465,
                idEquip = 10,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10.0,
                dateHourInitial = Date(1748359002),
                statusCon = true
            )
        )

        if (level == 2) return

        turnDao.insertAll(
            listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 1,
                    nroTurn = 1,
                    descrTurn = "Turn 1"
                ),
                TurnRoomModel(
                    idTurn = 2,
                    codTurnEquip = 1,
                    nroTurn = 2,
                    descrTurn = "Turn 2"
                )
            )
        )

        if (level == 3) return

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true
                )
            )
        )

        if (level == 4) return

        itemCheckListDao.insertAll(
            listOf(
                ItemCheckListRoomModel(
                    idItemCheckList = 1,
                    idCheckList = 1,
                    descrItemCheckList = "Item 1",
                ),
                ItemCheckListRoomModel(
                    idItemCheckList = 2,
                    idCheckList = 1,
                    descrItemCheckList = "Item 2",
                ),
                ItemCheckListRoomModel(
                    idItemCheckList = 3,
                    idCheckList = 1,
                    descrItemCheckList = "Item 3",
                ),
                ItemCheckListRoomModel(
                    idItemCheckList = 4,
                    idCheckList = 1,
                    descrItemCheckList = "Item 4",
                ),
                ItemCheckListRoomModel(
                    idItemCheckList = 5,
                    idCheckList = 1,
                    descrItemCheckList = "Item 5",
                )
            )
        )

        if (level == 5) return

    }
}