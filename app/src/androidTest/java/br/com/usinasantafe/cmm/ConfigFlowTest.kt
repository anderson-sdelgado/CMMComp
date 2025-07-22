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
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.view.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.utils.WEB_ALL_ACTIVITY
import br.com.usinasantafe.cmm.utils.WEB_ALL_COLAB
import br.com.usinasantafe.cmm.utils.WEB_ALL_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.utils.WEB_ALL_STOP
import br.com.usinasantafe.cmm.utils.WEB_EQUIP_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.utils.WEB_ALL_TURN
import br.com.usinasantafe.cmm.utils.WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.utils.WEB_SAVE_TOKEN
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigFlowTest {

    companion object {

        private lateinit var mockWebServer: MockWebServer

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val resultToken = """{"idBD":1,"idEquip":1}""".trimIndent()

            val resultActivity = """
                [{"idActivity":1,"codActivity":10,"descrActivity":"Test"}]
            """.trimIndent()

            val resultColab = """
                [{"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"}]
            """.trimIndent()

            val resultEquip = """
                [
                  {"idEquip":1,"nroEquip":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeFert":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1}
                ]
            """.trimIndent()

            val resultREquipActivity = """
                [
                  {"idREquipActivity":1,"idEquip":30,"idActivity":10}
                ]
            """.trimIndent()

            val resultTurn = """
                [
                  {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"}
                ]
            """.trimIndent()

            val resultRActivityStop = """
                [{"idRActivityStop":20,"idActivity":10,"idStop":1}]
            """.trimIndent()

            val resultStop = """
                [{"idStop":1,"codStop":10,"descrStop":"PARADA PARA ALMOCO"}]
            """.trimIndent()

            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                        "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                        "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                        "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                        "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                        "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurn)
                        "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                        "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }

            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            mockWebServer.shutdown()
        }
    }

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var turnDao: TurnDao

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

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
            composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
            composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

            Log.d("TestDebug", "Position 2")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 3")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 4")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 5")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()

            Log.d("TestDebug", "Position 6")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CANCELAR")
                .performClick()

            Log.d("TestDebug", "Position 7")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .performClick()

            Log.d("TestDebug", "Position 8")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SENHA").assertIsDisplayed()
            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 9")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 10")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 11")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 12")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            Log.d("TestDebug", "Position 13")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            Log.d("TestDebug", "Position 14")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            Log.d("TestDebug", "Position 15")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 16")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("MATRICULA OPERADOR").assertIsDisplayed()

            Log.d("TestDebug", "Position 17")

            composeTestRule.waitUntilTimeout(3_000)

            val listActivity = activityDao.listAll()
            assertEquals(
                listActivity.size,
                1
            )
            val activityRoomModel = listActivity[0]
            assertEquals(
                activityRoomModel.idActivity,
                1
            )
            assertEquals(
                activityRoomModel.codActivity,
                10
            )
            assertEquals(
                activityRoomModel.descrActivity,
                "Test"
            )

            val listColab = colabDao.all()
            assertEquals(
                listColab.size,
                1
            )
            val colabRoomModel = listColab[0]
            assertEquals(
                colabRoomModel.regColab,
                19759
            )
            assertEquals(
                colabRoomModel.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )

            val listEquip = equipDao.listAll()
            assertEquals(
                listEquip.size,
                1
            )
            val equipRoomModel = listEquip[0]
            assertEquals(
                equipRoomModel.idEquip,
                1
            )
            assertEquals(
                equipRoomModel.nroEquip,
                1000001
            )
            assertEquals(
                equipRoomModel.codClass,
                1
            )
            assertEquals(
                equipRoomModel.descrClass,
                "Classe 1"
            )
            assertEquals(
                equipRoomModel.codTurnEquip,
                1
            )
            assertEquals(
                equipRoomModel.idCheckList,
                1
            )
            assertEquals(
                equipRoomModel.typeFert,
                1
            )

            val listRActivityStop = rActivityStopDao.listAll()
            assertEquals(
                listRActivityStop.size,
                1
            )
            val rActivityStopRoomModel = listRActivityStop[0]
            assertEquals(
                rActivityStopRoomModel.idRActivityStop,
                1
            )
            assertEquals(
                rActivityStopRoomModel.idActivity,
                10
            )
            assertEquals(
                rActivityStopRoomModel.idStop,
                1
            )

            val listREquipActivity = rEquipActivityDao.listAll()
            assertEquals(
                listREquipActivity.size,
                1
            )
            val rEquipActivityRoomModel = listREquipActivity[0]
            assertEquals(
                rEquipActivityRoomModel.idREquipActivity,
                1
            )
            assertEquals(
                rEquipActivityRoomModel.idEquip,
                30
            )
            assertEquals(
                rEquipActivityRoomModel.idActivity,
                10
            )

            val listStop = stopDao.listAll()
            assertEquals(
                listStop.size,
                1
            )
            val stopRoomModel = listStop[0]
            assertEquals(
                stopRoomModel.idStop,
                1
            )
            assertEquals(
                stopRoomModel.codStop,
                10
            )
            assertEquals(
                stopRoomModel.descrStop,
                "PARADA PARA ALMOCO"
            )

            val listTurn = turnDao.listAll()
            assertEquals(
                listTurn.size,
                1
            )
            val turnRoomModel = listTurn[0]
            assertEquals(
                turnRoomModel.idTurn,
                1
            )
            assertEquals(
                turnRoomModel.codTurnEquip,
                1
            )
            assertEquals(
                turnRoomModel.nroTurn,
                1
            )
            assertEquals(
                turnRoomModel.descrTurn,
                "Turno 1"
            )

            Log.d("TestDebug", "Position Finish")

    }

}

