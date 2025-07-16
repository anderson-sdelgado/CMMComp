package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.WEB_OS_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
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
import kotlin.time.Duration.Companion.minutes


@HiltAndroidTest
class CheckListFlowTest {

    companion object {

        private lateinit var mockWebServer: MockWebServer

        private val jsonRetrofitOS = """
                [
                    {
                        "idOS":1,
                        "nroOS":123456,
                        "idLibOS":10,
                        "idPropAgr":20,
                        "areaOS":150.75,
                        "typeOS":1,
                        "idEquip":30
                    }
                ]
            """.trimIndent()

        private val jsonRetrofitROSActivity = """
            [
                {
                    "idROSActivity":1,
                    "idOS":1,
                    "idActivity":10
                }
            ]
        """.trimIndent()

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_OS_LIST_BY_NRO_OS" -> MockResponse().setBody(jsonRetrofitOS)
                        "/$WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS" -> MockResponse().setBody(jsonRetrofitROSActivity)
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
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var turnDao: TurnDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow() = runTest(
        timeout = 2.minutes
    ) {

        initialRegister()

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("7")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2200 - TRATOR")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("TURNO 1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("TURNO 1")
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("2")
            .performClick()
        composeTestRule.onNodeWithText("3")
            .performClick()
        composeTestRule.onNodeWithText("4")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("6")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ATIVIDADE")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 20")

        composeTestRule.waitUntilTimeout(3_000)

    }

    private suspend fun initialRegister() {

        val gson = Gson()

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 30,
                checkMotoMec = true,
                idBD = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 10,
                    codActivity = 20,
                    descrActivity = "ATIVIDADE 1"
                ),
                ActivityRoomModel(
                    idActivity = 20,
                    codActivity = 30,
                    descrActivity = "ATIVIDADE 2"
                )
            )
        )

        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    regColab = 19759,
                    nameColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 30,
                    nroEquip = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourMeter = 100.0,
                    classify = 1,
                    flagMechanic = true
                )
            )
        )

        rEquipActivityDao.insertAll(
            listOf(
                REquipActivityRoomModel(
                    idEquip = 30,
                    idActivity = 10
                ),
                REquipActivityRoomModel(
                    idEquip = 30,
                    idActivity = 20
                )
            )
        )

        turnDao.insertAll(
            listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 1,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
        )

    }
}
