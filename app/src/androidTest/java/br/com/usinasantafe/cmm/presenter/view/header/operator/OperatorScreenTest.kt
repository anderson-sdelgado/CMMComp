package br.com.usinasantafe.cmm.presenter.view.header.operator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
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
class OperatorScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Test
    fun verify_check_msg_field_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"MATRICULA OPERADOR\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_reg_invalid() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("1")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"MATRICULA OPERADOR\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_failure_update_if_not_have_data_in_table_config() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> UpdateTableColab -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_failure_update_if_web_service_return_errors() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> UpdateTableColab -> IColabRepository.recoverAll -> IColabRetrofitDatasource.recoverAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_failure_update_if_web_service_return_data_incorrect() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultColabFailureRetrofitScreen)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> UpdateTableColab -> IColabRepository.recoverAll -> IColabRetrofitDatasource.recoverAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 14 path \$[0].regColab")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_success_update_if_web_service_return_data_correct() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultColabRetrofitScreen)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            OperatorHeaderScreen(
                onNavInitialMenu = {},
                onNavEquip = {}
            )
        }
    }

    private suspend fun initialRegister() {

        val gson = Gson()

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 1,
                checkMotoMec = true,
                idBD = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        val resultColabRetrofit = """
            [{"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"}]
        """.trimIndent()

        val itemTypeColab = object : TypeToken<List<ColabRoomModel>>() {}.type
        val colabList = gson.fromJson<List<ColabRoomModel>>(resultColabRetrofit, itemTypeColab)
        colabDao.insertAll(colabList)
    }

    private val resultColabRetrofitScreen = """
        [{"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"}]
    """.trimIndent()

    private val resultColabFailureRetrofitScreen = """
        [{"regColab":19759a,"nameColab":"ANDERSON DA SILVA DELGADO"}]
    """.trimIndent()

}