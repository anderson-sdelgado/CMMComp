package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
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
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.WEB_OS_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
class HeaderInitialFlowTest {

    companion object {

        private lateinit var mockWebServer: MockWebServer

        private val jsonUpdateActivity = """
                [
                    {
                        "idActivity":10,
                        "codActivity":20,
                        "descrActivity":"ATIVIDADE 1"
                    },
                    {
                        "idActivity":20,
                        "codActivity":30,
                        "descrActivity":"ATIVIDADE 2"
                    }
                ]
            """.trimIndent()

        private val jsonUpdateColab = """
                [
                    {
                        "regColab":19759,
                        "nameColab":"ANDERSON DA SILVA DELGADO"
                    }
                ]
            """.trimIndent()

        private val jsonUpdateEquip = """
                [
                    {
                        "idEquip":30,
                        "nroEquip":2200,
                        "codClass":1,
                        "descrClass":"TRATOR",
                        "codTurnEquip":1,
                        "idCheckList":0,
                        "typeFert":1,
                        "hourMeter":100.0,
                        "measure":200.0,
                        "classify":1,
                        "flagMechanic":True
                    }
                ]
            """.trimIndent()

        private val jsonUpdateREquipActivity = """
            [
                {
                    "idEquip":30,
                    "idActivity":10
                },
                {
                    "idEquip":30,
                    "idActivity":20
                }
            ]
        """.trimIndent()

        private val jsonUpdateTurn = """
                [
                    {
                        "idTurn":1,
                        "codTurnEquip":1,
                        "nroTurn":1,
                        "descrTurn":"TURNO 1"
                    }
                ]
            """.trimIndent()

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

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var rOSActivityDao: ROSActivityDao

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

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 5")

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

        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()

        Log.d("TestDebug", "Position 7")

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

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithRegColab = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithRegColab.isSuccess,
            true
        )
        val entityWithRegColab = resultEntityWithRegColab.getOrNull()!!
        assertEquals(
            entityWithRegColab.regOperator,
            19759
        )
        composeTestRule.onNodeWithText("2200 - TRATOR")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithIdEquip = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdEquip.isSuccess,
            true
        )
        val entityWithIdEquip = resultEntityWithIdEquip.getOrNull()!!
        assertEquals(
            entityWithIdEquip.idEquip,
            30
        )

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2200 - TRATOR")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("TURNO 1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("TURNO 1")
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        Log.d("TestDebug", "Position 12")

        val resultEntityWithIdTurn = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdTurn.isSuccess,
            true
        )
        val entityWithIdTurn = resultEntityWithIdTurn.getOrNull()!!
        assertEquals(
            entityWithIdTurn.idTurn,
            1
        )

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("TURNO 1")
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()

        Log.d("TestDebug", "Position 15")

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

        val osList = osDao.listAll()
        assertEquals(
            osList.size,
            1
        )
        val os = osList[0]
        assertEquals(
            os.nroOS,
            123456
        )
        assertEquals(
            os.idLibOS,
            10
        )
        assertEquals(
            os.idPropAgr,
            20
        )
        assertEquals(
            os.areaOS,
            150.75,
            0.0
        )
        assertEquals(
            os.idEquip,
            30
        )
        val rOSActivityList = rOSActivityDao.listAll()
        assertEquals(
            rOSActivityList.size,
            1
        )
        val rOSActivity = rOSActivityList[0]
        assertEquals(
            rOSActivity.idROSActivity,
            1
        )
        assertEquals(
            rOSActivity.idOS,
            1
        )
        assertEquals(
            rOSActivity.idActivity,
            10
        )

        val resultEntityWithNroOS = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithNroOS.isSuccess,
            true
        )
        val entityWithNroOS = resultEntityWithNroOS.getOrNull()!!
        assertEquals(
            entityWithNroOS.nroOS,
            123456
        )

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 18")

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

        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithIdActivity = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdActivity.isSuccess,
            true
        )
        val entityWithIdActivity = resultEntityWithIdActivity.getOrNull()!!
        assertEquals(
            entityWithIdActivity.idActivity,
            10
        )

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 19")

        composeTestRule.waitUntilTimeout(3_000)

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

        val resultEntityWithMeasure = headerMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdActivity.isSuccess,
            true
        )
        val entityWithMeasure = resultEntityWithMeasure.getOrNull()!!
        assertEquals(
            entityWithMeasure.hourMeter,
            10000.0
        )

        val equip = equipDao.getByIdEquip(30)
        assertEquals(
            equip.hourMeter,
            10000.0,
            0.0
        )

        val headerMotoMecList = headerMotoMecDao.listAll()
        assertEquals(
            headerMotoMecList.size,
            1
        )
        val headerMotoMec = headerMotoMecList[0]
        assertEquals(
            headerMotoMec.regOperator,
            19759
        )
        assertEquals(
            headerMotoMec.idEquip,
            30
        )
        assertEquals(
            headerMotoMec.idTurn,
            1
        )
        assertEquals(
            headerMotoMec.nroOS,
            123456
        )
        assertEquals(
            headerMotoMec.idActivity,
            10
        )
        assertEquals(
            headerMotoMec.hourMeterInitial,
            10000.0,
            0.0
        )
        assertEquals(
            headerMotoMec.status,
            Status.OPEN
        )

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

        val itemTypeActivity = object : TypeToken<List<ActivityRoomModel>>() {}.type
        val activityList = gson.fromJson<List<ActivityRoomModel>>(jsonUpdateActivity, itemTypeActivity)
        activityDao.insertAll(activityList)

        val itemTypeColab = object : TypeToken<List<ColabRoomModel>>() {}.type
        val colabList = gson.fromJson<List<ColabRoomModel>>(jsonUpdateColab, itemTypeColab)
        colabDao.insertAll(colabList)

        val itemTypeEquip = object : TypeToken<List<EquipRoomModel>>() {}.type
        val equipList = gson.fromJson<List<EquipRoomModel>>(jsonUpdateEquip, itemTypeEquip)
        equipDao.insertAll(equipList)

        val itemTypeREquipActivity = object : TypeToken<List<REquipActivityRoomModel>>() {}.type
        val rEquipActivityList = gson.fromJson<List<REquipActivityRoomModel>>(jsonUpdateREquipActivity, itemTypeREquipActivity)
        rEquipActivityDao.insertAll(rEquipActivityList)

        val itemTypeTurn = object : TypeToken<List<TurnRoomModel>>() {}.type
        val turnList = gson.fromJson<List<TurnRoomModel>>(jsonUpdateTurn, itemTypeTurn)
        turnDao.insertAll(turnList)

    }
}
