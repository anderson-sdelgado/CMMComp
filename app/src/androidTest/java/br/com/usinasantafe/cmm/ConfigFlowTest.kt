package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.presenter.configuration.config.TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.presenter.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.cmm.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.utils.WEB_ALL_ACTIVITY
import br.com.usinasantafe.cmm.utils.WEB_ALL_COLAB
import br.com.usinasantafe.cmm.utils.WEB_GET_EQUIP_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.utils.WEB_ALL_TURN
import br.com.usinasantafe.cmm.utils.WEB_GET_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP
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
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
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

            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                        "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                        "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                        "/$WEB_GET_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                        "/$WEB_GET_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                        "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurn)
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

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun config_initial() =
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

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
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

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
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

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
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

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
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

        Log.d("TestDebug", "Position Finish")

    }


}

