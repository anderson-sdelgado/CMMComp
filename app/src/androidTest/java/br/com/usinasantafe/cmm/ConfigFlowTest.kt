package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IEquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.lib.WEB_ALL_ACTIVITY
import br.com.usinasantafe.cmm.lib.WEB_ALL_COLAB
import br.com.usinasantafe.cmm.lib.WEB_ALL_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.lib.WEB_ALL_FUNCTION_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_ITEM_MENU
import br.com.usinasantafe.cmm.lib.WEB_ALL_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_R_ITEM_MENU_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_ALL_TURN
import br.com.usinasantafe.cmm.lib.WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_SAVE_TOKEN
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipSharedPreferencesDatasource: IEquipSharedPreferencesDatasource

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemCheckListDao: ItemCheckListDao

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var turnDao: TurnDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var functionStopDao: FunctionStopDao

    @Inject
    lateinit var itemMenuDao: ItemMenuDao

    @Inject
    lateinit var rItemMenuStopDao: RItemMenuStopDao

    companion object {

        private lateinit var mockWebServer: MockWebServer

        private val resultToken = """
            {
                "idServ": 16,
                "equip": {
                    "id": 2065,
                    "nro": 2200,
                    "classify": 1,
                    "codClass": 8,
                    "descrClass": "CAVALO CANAVIEIRO",
                    "codTurnEquip": 22,
                    "idCheckList": 3522,
                    "typeEquip": 1,
                    "hourMeter": 0,
                    "flagMechanic": 0,
                    "flagTire": 0
                }
            }
        """.trimIndent()
        private val resultActivity = """
            [
                {"idActivity":1,"codActivity":10,"descrActivity":"Test"},
                {"idActivity":2,"codActivity":20,"descrActivity":"Test2"}
            ]
        """.trimIndent()

        private val resultColab = """
            [
                {"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"},
                {"regColab":18017,"nameColab":"RONALDO"}
            ]
        """.trimIndent()

        private val resultEquip = """
            [
              {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","typeEquip":1},
              {"id":2,"nro":1000002,"codClass":2,"descrClass":"Classe 2","typeEquip":1}
            ]
        """.trimIndent()

        private val resultFunctionActivity = """
            [
              {"idFunctionActivity":1,"idActivity":1,"typeActivity":1},
              {"idFunctionActivity":2,"idActivity":2,"typeActivity":2}
            ]
        """.trimIndent()

        private val resultFunctionStop = """
            [
              {"idFunctionStop":1,"idStop":1,"typeStop":1},
              {"idFunctionStop":2,"idStop":2,"typeStop":2}
            ]
        """.trimIndent()

        private val resultItemCheckList = """
            [
              {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
              {"idItemCheckList":2,"idCheckList":101,"descrItemCheckList":"Verificar Freios"}
            ]
        """.trimIndent()

        private val resultItemMenuPMM = """
            [
              {"id":1,"descr":"ITEM 1","idType": 1,"pos": 1,"idFunction": 1,"idApp": 1},
              {"id":2,"descr":"ITEM 2","idType": 1,"pos": 2,"idFunction": 2,"idApp": 1}
            ]
        """.trimIndent()

        private val resultRActivityStop = """
            [
                {"idActivity":101,"idStop":301},
                {"idActivity":102,"idStop":303}
            ]
        """.trimIndent()

        private val resultREquipActivity = """
            [
              {"idREquipActivity":1,"idEquip":30,"idActivity":10},
              {"idREquipActivity":2,"idEquip":40,"idActivity":10}
            ]
        """.trimIndent()

        private val resultStop = """
            [
                {"idStop":1,"codStop":10,"descrStop":"PARADA PARA ALMOCO"},
                {"idStop":2,"codStop":20,"descrStop":"CHUVA"}
            ]
        """.trimIndent()

        private val resultTurn = """
            [
              {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"},
              {"idTurn":2,"codTurnEquip":2,"nroTurn":2,"descrTurn":"Turno 2"}
            ]
        """.trimIndent()

        private val resultRItemMenuStop = """
            [
                {"id": 1,"idFunction": 1,"idApp": 1,"idStop": 1},
                {"id": 2,"idFunction": 2,"idApp": 2,"idStop": 2}
            ]
        """.trimIndent()

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val dispatcherSuccess: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                        "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                        "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                        "/$WEB_ALL_EQUIP" -> MockResponse().setBody(resultEquip)
                        "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                        "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                        "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                        "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenuPMM)
                        "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                        "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                        "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                        "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                        "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurn)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }

            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccess
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            mockWebServer.shutdown()
        }
    }

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow() =
        runTest(
            timeout = 10.minutes
        ) {

            Log.d("TestDebug", "Position 1")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
            composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
            composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

            Log.d("TestDebug", "Position 2")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 3")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 4")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 5")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()

            Log.d("TestDebug", "Position 6")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CANCELAR")
                .performClick()

            Log.d("TestDebug", "Position 7")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 8")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 9")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 10")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 11")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 12")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 13")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 14")

            composeTestRule.waitUntilTimeout(10_000)

            asserts()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 15")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 16")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("MATRICULA OPERADOR").assertIsDisplayed()

            Log.d("TestDebug", "Position 17")

            composeTestRule.waitUntilTimeout()

            Log.d("TestDebug", "Position Finish")

            composeTestRule.waitUntilTimeout()

    }


    private suspend fun asserts() {
        val resultGetConfig = configSharedPreferencesDatasource.get()
        assertEquals(
            resultGetConfig.isSuccess,
            true
        )
        val config = resultGetConfig.getOrNull()!!
        assertEquals(
            config.idServ,
            16
        )
        assertEquals(
            config.number,
            16997417840
        )
        assertEquals(
            config.password,
            "12345"
        )
        assertEquals(
            config.checkMotoMec,
            true
        )
        assertEquals(
            config.version,
            "1.0"
        )
        assertEquals(
            config.app,
            "PMM"
        )

        val resultGetEquip = equipSharedPreferencesDatasource.get()
        assertEquals(
            resultGetEquip.isSuccess,
            true
        )
        val equip = resultGetEquip.getOrNull()!!
        assertEquals(
            equip.id,
            2065
        )
        assertEquals(
            equip.nro,
            2200
        )
        assertEquals(
            equip.classify,
            1
        )
        assertEquals(
            equip.codClass,
            8
        )
        assertEquals(
            equip.descrClass,
            "CAVALO CANAVIEIRO"
        )
        assertEquals(
            equip.typeEquip,
            TypeEquip.NORMAL
        )
        assertEquals(
            equip.idCheckList,
            3522
        )
        assertEquals(
            equip.codTurnEquip,
            22
        )
        assertEquals(
            equip.hourMeter,
            0.0
        )
        assertEquals(
            equip.flagMechanic,
            false
        )
        assertEquals(
            equip.flagTire,
            false
        )


        val activityRoomModelList = activityDao.all()
        assertEquals(
            activityRoomModelList.size,
            2
        )
        val activityRoomModel1 = activityRoomModelList[0]
        assertEquals(
            activityRoomModel1.id,
            1
        )
        assertEquals(
            activityRoomModel1.cod,
            10
        )
        assertEquals(
            activityRoomModel1.descr,
            "Test"
        )
        val activityRoomModel2 = activityRoomModelList[1]
        assertEquals(
            activityRoomModel2.id,
            2
        )
        assertEquals(
            activityRoomModel2.cod,
            20
        )
        assertEquals(
            activityRoomModel2.descr,
            "Test2"
        )

        val colabRoomModelList = colabDao.all()
        assertEquals(
            colabRoomModelList.size,
            2
        )
        val colabRoomModel1 = colabRoomModelList[0]
        assertEquals(
            colabRoomModel1.regColab,
            18017
        )
        assertEquals(
            colabRoomModel1.nameColab,
            "RONALDO"
        )
        val colabRoomModel2 = colabRoomModelList[1]
        assertEquals(
            colabRoomModel2.regColab,
            19759
        )
        assertEquals(
            colabRoomModel2.nameColab,
            "ANDERSON DA SILVA DELGADO"
        )

        val equipRoomModelList = equipDao.all()
        assertEquals(
            equipRoomModelList.size,
            2
        )
        val equipRoomModel1 = equipRoomModelList[0]
        assertEquals(
            equipRoomModel1.id,
            1
        )
        assertEquals(
            equipRoomModel1.nro,
            1000001
        )
        assertEquals(
            equipRoomModel1.codClass,
            1
        )
        assertEquals(
            equipRoomModel1.descrClass,
            "Classe 1"
        )
        val equipRoomModel2 = equipRoomModelList[1]
        assertEquals(
            equipRoomModel2.id,
            2
        )
        assertEquals(
            equipRoomModel2.nro,
            1000002
        )
        assertEquals(
            equipRoomModel2.codClass,
            2
        )
        assertEquals(
            equipRoomModel2.descrClass,
            "Classe 2"
        )

        val functionActivityRoomModelList = functionActivityDao.all()
        assertEquals(
            functionActivityRoomModelList.size,
            2
        )
        val functionActivityRoomModel1 = functionActivityRoomModelList[0]
        assertEquals(
            functionActivityRoomModel1.idFunctionActivity,
            1
        )
        assertEquals(
            functionActivityRoomModel1.idActivity,
            1
        )
        assertEquals(
            functionActivityRoomModel1.typeActivity,
            TypeActivity.PERFORMANCE
        )
        val functionActivityRoomModel2 = functionActivityRoomModelList[1]
        assertEquals(
            functionActivityRoomModel2.idFunctionActivity,
            2
        )
        assertEquals(
            functionActivityRoomModel2.idActivity,
            2
        )
        assertEquals(
            functionActivityRoomModel2.typeActivity,
            TypeActivity.TRANSHIPMENT
        )

        val functionStopRoomModelList = functionStopDao.all()
        assertEquals(
            functionStopRoomModelList.size,
            2
        )
        val functionStopRoomModel1 = functionStopRoomModelList[0]
        assertEquals(
            functionStopRoomModel1.idFunctionStop,
            1
        )
        assertEquals(
            functionStopRoomModel1.idStop,
            1
        )
        assertEquals(
            functionStopRoomModel1.typeStop,
            TypeStop.CHECKLIST
        )
        val functionStopRoomModel2 = functionStopRoomModelList[1]
        assertEquals(
            functionStopRoomModel2.idFunctionStop,
            2
        )
        assertEquals(
            functionStopRoomModel2.idStop,
            2
        )
        assertEquals(
            functionStopRoomModel2.typeStop,
            TypeStop.IMPLEMENT
        )

        val itemCheckListRoomModelList = itemCheckListDao.all()
        assertEquals(
            itemCheckListRoomModelList.size,
            2
        )
        val itemCheckListRoomModel1 = itemCheckListRoomModelList[0]
        assertEquals(
            itemCheckListRoomModel1.idItemCheckList,
            1
        )
        assertEquals(
            itemCheckListRoomModel1.idCheckList,
            101
        )
        assertEquals(
            itemCheckListRoomModel1.descrItemCheckList,
            "Verificar Nível de Óleo"
        )
        val itemCheckListRoomModel2 = itemCheckListRoomModelList[1]
        assertEquals(
            itemCheckListRoomModel2.idItemCheckList,
            2
        )
        assertEquals(
            itemCheckListRoomModel2.idCheckList,
            101
        )
        assertEquals(
            itemCheckListRoomModel2.descrItemCheckList,
            "Verificar Freios"
        )

        val itemMenuPMMRoomModelList = itemMenuDao.all()
        assertEquals(
            itemMenuPMMRoomModelList.size,
            2
        )
        val itemMenuPMMRoomModel1 = itemMenuPMMRoomModelList[0]
        assertEquals(
            itemMenuPMMRoomModel1.id,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.descr,
            "ITEM 1"
        )
        assertEquals(
            itemMenuPMMRoomModel1.idType,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.pos,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.idFunction,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.idApp,
            1
        )
        val itemMenuPMMRoomModel2 = itemMenuPMMRoomModelList[1]
        assertEquals(
            itemMenuPMMRoomModel2.id,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.descr,
            "ITEM 2"
        )
        assertEquals(
            itemMenuPMMRoomModel2.idType,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel2.pos,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.idFunction,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.idApp,
            1
        )

        val rActivityStopRoomModelList = rActivityStopDao.all()
        assertEquals(
            rActivityStopRoomModelList.size,
            2
        )
        val rActivityStopRoomModel1 = rActivityStopRoomModelList[0]
        assertEquals(
            rActivityStopRoomModel1.idRActivityStop,
            1
        )
        assertEquals(
            rActivityStopRoomModel1.idActivity,
            101
        )
        assertEquals(
            rActivityStopRoomModel1.idStop,
            301
        )
        val rActivityStopRoomModel2 = rActivityStopRoomModelList[1]
        assertEquals(
            rActivityStopRoomModel2.idRActivityStop,
            2
        )
        assertEquals(
            rActivityStopRoomModel2.idActivity,
            102
        )
        assertEquals(
            rActivityStopRoomModel2.idStop,
            303
        )

        val rEquipActivityRoomModelList = rEquipActivityDao.all()
        assertEquals(
            rEquipActivityRoomModelList.size,
            2
        )
        val rEquipActivityRoomModel1 = rEquipActivityRoomModelList[0]
        assertEquals(
            rEquipActivityRoomModel1.idREquipActivity,
            1
        )
        assertEquals(
            rEquipActivityRoomModel1.idEquip,
            30
        )
        assertEquals(
            rEquipActivityRoomModel1.idActivity,
            10
        )
        val rEquipActivityRoomModel2 = rEquipActivityRoomModelList[1]
        assertEquals(
            rEquipActivityRoomModel2.idREquipActivity,
            2
        )
        assertEquals(
            rEquipActivityRoomModel2.idEquip,
            40
        )
        assertEquals(
            rEquipActivityRoomModel2.idActivity,
            10
        )


        val rItemMenuStopRoomModelList = rItemMenuStopDao.all()
        assertEquals(
            rItemMenuStopRoomModelList.size,
            2
        )
        val rItemMenuStopRoomModel1 = rItemMenuStopRoomModelList[0]
        assertEquals(
            rItemMenuStopRoomModel1.id,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idFunction,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idApp,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idStop,
            1
        )
        val rItemMenuStopRoomModel2 = rItemMenuStopRoomModelList[1]
        assertEquals(
            rItemMenuStopRoomModel2.id,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idFunction,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idApp,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idStop,
            2
        )

        val stopRoomModelList = stopDao.all()
        assertEquals(
            stopRoomModelList.size,
            2
        )
        val stopRoomModel1 = stopRoomModelList[0]
        assertEquals(
            stopRoomModel1.idStop,
            1
        )
        assertEquals(
            stopRoomModel1.codStop,
            10
        )
        assertEquals(
            stopRoomModel1.descrStop,
            "PARADA PARA ALMOCO"
        )
        val stopRoomModel2 = stopRoomModelList[1]
        assertEquals(
            stopRoomModel2.idStop,
            2
        )
        assertEquals(
            stopRoomModel2.codStop,
            20
        )
        assertEquals(
            stopRoomModel2.descrStop,
            "CHUVA"
        )

        val turnRoomModelList = turnDao.all()
        assertEquals(
            turnRoomModelList.size,
            2
        )
        val turnRoomModel1 = turnRoomModelList[0]
        assertEquals(
            turnRoomModel1.idTurn,
            1
        )
        assertEquals(
            turnRoomModel1.codTurnEquip,
            1
        )
        assertEquals(
            turnRoomModel1.nroTurn,
            1
        )
        assertEquals(
            turnRoomModel1.descrTurn,
            "Turno 1"
        )
        val turnRoomModel2 = turnRoomModelList[1]
        assertEquals(
            turnRoomModel2.idTurn,
            2
        )
        assertEquals(
            turnRoomModel2.codTurnEquip,
            2
        )
        assertEquals(
            turnRoomModel2.nroTurn,
            2
        )
        assertEquals(
            turnRoomModel2.descrTurn,
            "Turno 2"
        )

    }

}

