package br.com.usinasantafe.cmm.presenter.view.checkList.questionUpdate

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.utils.WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class QuestionUpdateCheckListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Test
    fun check_open_screen_and_return_failure_if_not_have_data_in_table_config() =
        runTest(
            timeout = 10.minutes
        ) {

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CHECKLIST")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionUpdateCheckListViewModel.checkUpdate -> ICheckUpdateCheckList -> IConfigRepository.getNroEquip -> IConfigSharedPreferencesDatasource.getNroEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_failure_if_web_service_return_error() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CHECKLIST")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. QuestionUpdateCheckListViewModel.checkUpdate -> ICheckUpdateCheckList -> IItemCheckListRepository.checkUpdateByNroEquip -> IItemCheckListRetrofitDatasource.checkUpdateByNroEquip -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_if_web_service_return_data_correct() =
        runTest(
            timeout = 10.minutes
        ) {
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody("""{"qtd":1}""")
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CHECKLIST")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATENÇÃO! HÁ NOVAS ATUALIZAÇÕES PARA QUESTÕES DE CHECKLIST. DESEJA ATUALIZÁ-LAS?")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_failure_on_click_update_if_web_service_return_error() =
        runTest(
            timeout = 10.minutes
        ) {
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("""{"qtd":1}""")
                        "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CHECKLIST")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATENÇÃO! HÁ NOVAS ATUALIZAÇÕES PARA QUESTÕES DE CHECKLIST. DESEJA ATUALIZÁ-LAS?")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("SIM")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("CHECKLIST")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. QuestionUpdateCheckListViewModel.updateAllDatabase -> IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.listByNroEquip -> IItemCheckListRetrofitDatasource.listByNroEquip -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            QuestionUpdateCheckListScreen(
                onNavItemCheckList = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

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

        if (level == 1) return

    }

}