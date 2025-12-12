package br.com.usinasantafe.cmm.presenter.view.note.stopList

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.NoteMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.WEB_ALL_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_STOP
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class StopListNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var itemMotoMecSharedPreferencesDatasource: ItemMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Test
    fun check_open_screen_and_return_failure_if_not_have_data_in_table_note_internal() =
        runTest(
            timeout = 10.minutes
        ) {

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. StopListNoteViewModel.stopList -> IGetStopList -> INoteMotoMecRepository.getIdActivity -> INoteMotoMecSharedPreferencesDatasource.getIdActivity -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)


        }

    @Test
    fun check_open_screen_and_return_empty_list_if_not_have_data_in_table_r_activity_stop() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(0)

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_empty_list_if_not_have_data_in_table_stop() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(0)

            composeTestRule.waitUntilTimeout(10_000)

        }


    @Test
    fun check_open_screen_and_return_list_if_have_data() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_not_have_data_token_to_update() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. StopListNoteViewModel.updateAllDatabase -> UpdateTableRActivityStop -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_data_web_service_is_incorrect() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. StopListNoteViewModel.updateAllDatabase -> UpdateTableRActivityStop -> IRActivityStopRepository.recoverAll -> IRActivityStopRetrofitDatasource.recoverAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_data_web_service_r_activity_stop_is_incorrect() =
        runTest(
            timeout = 10.minutes
        ) {

            val mockWebServer = MockWebServer()
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStopFailureRetrofit)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. StopListNoteViewModel.updateAllDatabase -> UpdateTableRActivityStop -> IRActivityStopRepository.addAll -> IRActivityStopRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_r_activity_stop.idRActivityStop (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_data_web_service_stop_is_incorrect() =
        runTest(
            timeout = 10.minutes
        ) {

            val mockWebServer = MockWebServer()
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStopRetrofit)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. StopListNoteViewModel.updateAllDatabase -> UpdateTableStop -> IStopRepository.recoverAll -> IStopRetrofitDatasource.recoverAll -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_success_if_data_web_service_is_correct() =
        runTest(
            timeout = 10.minutes
        ) {

            val mockWebServer = MockWebServer()
            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStopRetrofit)
                        "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStopRetrofit)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)


        }

    @Test
    fun check_search_typing() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("PARADA")
                .assertIsDisplayed()
            composeTestRule
                .onAllNodesWithTag("item_list_1")
                .assertCountEquals(1)
            composeTestRule.onNodeWithTag(TAG_FILTER_TEXT_FIELD_STOP_LIST_SCREEN)
                .performTextInput("MANUT")

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            StopListNoteScreen(
                onNavActivityList = {},
                onNavMenuNote = {}
            )
        }

    }

    private val resultRActivityStopFailureRetrofit = """
        [
          {"idRActivityStop":1,"idActivity":10,"idStop":401},
          {"idRActivityStop":1,"idActivity":10,"idStop":402}
        ]
    """.trimIndent()

    private val resultRActivityStopRetrofit = """
        [
          {"idRActivityStop":1,"idActivity":1,"idStop":1},
          {"idRActivityStop":2,"idActivity":1,"idStop":2}
        ]
    """.trimIndent()

    private val resultStopRetrofit = """
        [
          {"idStop":1,"codStop":401,"descrStop":"MANUTENCAO MECANICA"},
          {"idStop":2,"codStop":402,"descrStop":"ABASTECIMENTO"}
        ]
    """.trimIndent()

    private suspend fun initialRegister(level: Int) {

        itemMotoMecSharedPreferencesDatasource.save(
            NoteMotoMecSharedPreferencesModel(
                idActivity = 1,
            )
        )

        if (level == 1) return

        rActivityStopDao.insertAll(
            listOf(
                RActivityStopRoomModel(
                    idRActivityStop = 1,
                    idActivity = 1,
                    idStop = 1
                ),
                RActivityStopRoomModel(
                    idRActivityStop = 2,
                    idActivity = 1,
                    idStop = 2
                ),
                RActivityStopRoomModel(
                    idRActivityStop = 3,
                    idActivity = 1,
                    idStop = 3
                ),
                RActivityStopRoomModel(
                    idRActivityStop = 4,
                    idActivity = 2,
                    idStop = 4
                )
            )
        )

        if (level == 2) return

        stopDao.insertAll(
            listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 10,
                    descrStop = "MANUTENCAO MECANICA"
                ),
                StopRoomModel(
                    idStop = 2,
                    codStop = 420,
                    descrStop = "ABASTECIMENTO"
                ),
                StopRoomModel(
                    idStop = 4,
                    codStop = 40,
                    descrStop = "PARADA 4"
                ),
            )
        )

        if (level == 3) return

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                checkMotoMec = true,
                idServ = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (level == 4) return

    }
}