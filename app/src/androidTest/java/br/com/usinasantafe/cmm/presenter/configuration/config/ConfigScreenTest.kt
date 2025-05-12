package br.com.usinasantafe.cmm.presenter.configuration.config

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.cmm.br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ConfigScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var turnDao: TurnDao

    @Test
    fun verify_check_open_screen_config_and_service_without_connection() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(2_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            val result = configSharedPreferencesDatasource.has()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )

            composeTestRule.waitUntilTimeout(2_000)

    }

    @Test
    fun verify_check_return_colab_data_with_failure() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFailureColab
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(2_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> UpdateTableColab -> IColabRepository.recoverAll -> IColabRetrofitDatasource.recoverAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 17 path \$[0].matricColab")

            val result = configSharedPreferencesDatasource.has()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    nroEquip = 2200,
                    password = "12345",
                    idEquip = 1,
                    checkMotoMec = true,
                    idBD = 1,
                    version = "1.0",
                    app = "PMM",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )
            composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_check_return_equip_data_with_failure() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFailureEquip
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(2_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> UpdateTableEquip -> IEquipRepository.addAll -> IEquipRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_equip.idEquip (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    nroEquip = 2200,
                    password = "12345",
                    idEquip = 1,
                    checkMotoMec = true,
                    idBD = 1,
                    version = "1.0",
                    app = "PMM",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )
            val colabRoomModelList = colabDao.listAll()
            assertEquals(
                colabRoomModelList.size,
                1
            )
            val roomModel = colabRoomModelList[0]
            assertEquals(
                roomModel.regColab,
                19759
            )
            assertEquals(
                roomModel.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )
            val equipRoomModelList = equipDao.listAll()
            assertEquals(
                equipRoomModelList.size,
                0
            )

            composeTestRule.waitUntilTimeout(2_000)

    }

    @Test
    fun verify_check_return_turn_data_with_failure() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFailureTurn
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(2_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> UpdateTableTurn -> ITurnRepository.recoverAll -> ITurnRetrofitDatasource.recoverAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 33 path \$[0].codTurnEquip")

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    nroEquip = 2200,
                    password = "12345",
                    idEquip = 1,
                    checkMotoMec = true,
                    idBD = 1,
                    version = "1.0",
                    app = "PMM",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )
            val colabRoomModelList = colabDao.listAll()
            assertEquals(
                colabRoomModelList.size,
                1
            )
            val roomModel = colabRoomModelList[0]
            assertEquals(
                roomModel.regColab,
                19759
            )
            assertEquals(
                roomModel.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )
            val equipRoomModelList = equipDao.listAll()
            assertEquals(
                equipRoomModelList.size,
                1
            )
            val equipRoomModel = equipRoomModelList[0]
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
            assertEquals(
                equipRoomModel.hourmeter,
                100.0,
                0.0
            )
            assertEquals(
                equipRoomModel.measurement,
                200.0,
                0.0
            )
            assertEquals(
                equipRoomModel.type,
                1
            )
            assertEquals(
                equipRoomModel.classify,
                1
            )
            assertEquals(
                equipRoomModel.flagApontMecan,
                true
            )
            assertEquals(
                equipRoomModel.flagApontPneu,
                true
            )
            composeTestRule.waitUntilTimeout(2_000)

        }

    @Test
    fun verify_check_success_update() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccess
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(2_000)

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    nroEquip = 2200,
                    password = "12345",
                    idEquip = 1,
                    checkMotoMec = true,
                    idBD = 1,
                    version = "1.0",
                    app = "PMM",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.UPDATED
                )
            )
            val colabRoomModelList = colabDao.listAll()
            assertEquals(
                colabRoomModelList.size,
                1
            )
            val roomModel = colabRoomModelList[0]
            assertEquals(
                roomModel.regColab,
                19759
            )
            assertEquals(
                roomModel.nameColab,
                "ANDERSON DA SILVA DELGADO"
            )
            val equipRoomModelList = equipDao.listAll()
            assertEquals(
                equipRoomModelList.size,
                1
            )
            val equipRoomModel = equipRoomModelList[0]
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
            assertEquals(
                equipRoomModel.hourmeter,
                100.0,
                0.0
            )
            assertEquals(
                equipRoomModel.measurement,
                200.0,
                0.0
            )
            assertEquals(
                equipRoomModel.type,
                1
            )
            assertEquals(
                equipRoomModel.classify,
                1
            )
            assertEquals(
                equipRoomModel.flagApontMecan,
                true
            )
            assertEquals(
                equipRoomModel.flagApontPneu,
                true
            )
            val turnRoomModelList = turnDao.listAll()
            assertEquals(
                turnRoomModelList.size,
                1
            )
            val turnRoomModel = turnRoomModelList[0]
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

    }

    private fun setContent() {
        composeTestRule.setContent {
            ConfigScreen (
                onNavInitialMenu = {},
            )
        }
    }

    private val dispatcherFailureColab: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/find-token.php" -> MockResponse().setBody(resultTokenRetrofitScreen)
                "/colab.php" -> MockResponse().setBody(resultColabFailureRetrofitScreen)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFailureEquip: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/find-token.php" -> MockResponse().setBody(resultTokenRetrofitScreen)
                "/colab.php" -> MockResponse().setBody(resultColabRetrofitScreen)
                "/equip.php" -> MockResponse().setBody(resultEquipFailureRetrofitScreen)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFailureTurn: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/find-token.php" -> MockResponse().setBody(resultTokenRetrofitScreen)
                "/colab.php" -> MockResponse().setBody(resultColabRetrofitScreen)
                "/equip.php" -> MockResponse().setBody(resultEquipRetrofitScreen)
                "/turn.php" -> MockResponse().setBody(resultTurnFailureRetrofitScreen)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSuccess: Dispatcher = object : Dispatcher() {

        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/find-token.php" -> MockResponse().setBody(resultTokenRetrofitScreen)
                "/colab.php" -> MockResponse().setBody(resultColabRetrofitScreen)
                "/equip.php" -> MockResponse().setBody(resultEquipRetrofitScreen)
                "/turn.php" -> MockResponse().setBody(resultTurnRetrofitScreen)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

}

val resultTokenRetrofitScreen = """{"idBD":1,"idEquip":1}""".trimIndent()

val resultColabRetrofitScreen = """
    [{"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultColabFailureRetrofitScreen = """
    [{"regColab":19759a,"nameColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultEquipFailureRetrofitScreen = """
    [
      {"idEquip":1,"nroEquip":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeFert":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1},
      {"idEquip":1,"nroEquip":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeFert":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1}
    ]
""".trimIndent()

val resultEquipRetrofitScreen = """
    [
      {"idEquip":1,"nroEquip":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeFert":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1}
    ]
""".trimIndent()

val resultTurnFailureRetrofitScreen = """
    [
      {"idTurn":1,"codTurnEquip":1,nroTurn":1,"descrTurn":"Turno 1"}
    ]
""".trimIndent()

val resultTurnRetrofitScreen = """
    [
      {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"}
    ]
""".trimIndent()



