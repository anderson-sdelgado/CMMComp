package br.com.usinasantafe.cmm.presenter.view.header.turnList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TurnListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao

    @Inject
    lateinit var turnDao: br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. TurnListViewModel.turnList -> IGetTurnList -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_failure_if_not_have_data_table_equip_and_turn() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. TurnListViewModel.turnList -> IGetTurnList -> IEquipRepository.getCodTurnEquipByIdEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_list_empty_if_not_have_data_table_turn() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("TURNO 1")
                .assertIsNotDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_list_turn_if_all_data_was_correct() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("TURNO 1")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_failure_update_if_web_service_return_errors() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultTurnFailure)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("TURNO 1")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. TurnListViewModel.updateAllDatabase -> UpdateTableTurn -> ITurnRepository.recoverAll -> java.lang.IllegalArgumentException: The field 'codTurnEquip' cannot is null.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_success_update_if_web_service_return_data_correct() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultTurn)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("TURNO 1")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            TurnListHeaderScreen(
                onNavEquip = {},
                onNavOS = {}
            )
        }
    }


    private suspend fun initialRegister(ret: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 1,
                checkMotoMec = true,
                idServ = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (ret == 1) return

        val resultEquipRetrofit = """
                [
                  {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1}
                ]
            """.trimIndent()

        val gson = Gson()
        val itemTypeEquip = object : TypeToken<List<EquipRoomModel>>() {}.type
        val equipList = gson.fromJson<List<EquipRoomModel>>(resultEquipRetrofit, itemTypeEquip)
        equipDao.insertAll(equipList)

        if (ret == 2) return

        val resultTurnRetrofit = """
                [
                  {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"}
                ]
            """.trimIndent()

        val itemTypeTurn = object : TypeToken<List<TurnRoomModel>>() {}.type
        val turnList = gson.fromJson<List<TurnRoomModel>>(resultTurnRetrofit, itemTypeTurn)
        turnDao.insertAll(turnList)

    }

    private val resultTurnFailure = """
        [
          {"idTurn":1,"codTurnoEquip":1,"nroTurn":1,"descrTurn":"TURNO A"}
        ]
    """.trimIndent()

    private val resultTurn = """
        [
          {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"TURNO A"}
        ]
    """.trimIndent()

}