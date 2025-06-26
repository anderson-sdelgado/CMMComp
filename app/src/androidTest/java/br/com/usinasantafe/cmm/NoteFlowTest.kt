package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.NoteMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.WEB_GET_OS_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.WEB_GET_R_OS_ACTIVITY_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class NoteFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var noteMotoMecSharedPreferencesDatasource: NoteMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var stopDao: StopDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow() = runTest(
        timeout = 2.minutes
    ) {

        initialRegister()

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("TRABALHANDO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("TRABALHANDO")
            .performClick()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("TRABALHANDO")
            .performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithNroOS = noteMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithNroOS.isSuccess,
            true
        )
        val entityWithNroOS = resultEntityWithNroOS.getOrNull()!!
        assertEquals(
            entityWithNroOS.nroOS,
            123456
        )

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 8")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithIdActivity = noteMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdActivity.isSuccess,
            true
        )
        val entityWithIdActivity = resultEntityWithIdActivity.getOrNull()!!
        assertEquals(
            entityWithIdActivity.nroOS,
            123456
        )
        assertEquals(
            entityWithIdActivity.idActivity,
            10
        )
        assertEquals(
            entityWithIdActivity.idStop,
            null
        )
        val listNote = noteMotoMecDao.listAll()
        assertEquals(
            listNote.size,
            1
        )
        val entity = listNote[0]
        assertEquals(
            entity.idHeader,
            1
        )
        assertEquals(
            entity.nroOS,
            123456
        )
        assertEquals(
            entity.idActivity,
            10
        )
        assertEquals(
            entity.idStop,
            null
        )

        Log.d("TestDebug", "Position 10")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PARADO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("PARADO")
            .performClick()

        Log.d("TestDebug", "Position 11")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 12")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PARADO")
            .performClick()

        Log.d("TestDebug", "Position 13")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 14")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithIdActivityStop = noteMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdActivityStop.isSuccess,
            true
        )
        val entityWithIdActivityStop = resultEntityWithIdActivityStop.getOrNull()!!
        assertEquals(
            entityWithIdActivityStop.nroOS,
            123456
        )
        assertEquals(
            entityWithIdActivityStop.idActivity,
            10
        )

        Log.d("TestDebug", "Position 15")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 17")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("200 - PARADA PARA REFEICAO")
            .performClick()

        Log.d("TestDebug", "Position 18")

        composeTestRule.waitUntilTimeout(3_000)

        val resultEntityWithIdStop = noteMotoMecSharedPreferencesDatasource.get()
        assertEquals(
            resultEntityWithIdStop.isSuccess,
            true
        )
        val entityWithIdStop = resultEntityWithIdStop.getOrNull()!!
        assertEquals(
            entityWithIdStop.nroOS,
            123456
        )
        assertEquals(
            entityWithIdStop.idActivity,
            10
        )
        assertEquals(
            entityWithIdStop.idStop,
            1
        )
        val listNoteStop = noteMotoMecDao.listAll()
        assertEquals(
            listNote.size,
            1
        )
        val entityActivity = listNoteStop[0]
        assertEquals(
            entityActivity.idHeader,
            1
        )
        assertEquals(
            entityActivity.nroOS,
            123456
        )
        assertEquals(
            entityActivity.idActivity,
            10
        )
        assertEquals(
            entityActivity.idStop,
            null
        )
        val entityStop = listNoteStop[1]
        assertEquals(
            entityStop.idHeader,
            1
        )
        assertEquals(
            entityStop.nroOS,
            123456
        )
        assertEquals(
            entityStop.idActivity,
            10
        )
        assertEquals(
            entityStop.idStop,
            1
        )

        Log.d("TestDebug", "Position 19")

        composeTestRule.waitUntilTimeout(3_000)

    }

    private suspend fun initialRegister() {

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456
            )
        )

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 123465,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10.0,
                dateHourInitial = Date(1748359002),
                statusCon = true
            )
        )

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

        val jsonRetrofitOS = """
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

        val jsonRetrofitROSActivity = """
            [
                {
                    "idROSActivity":1,
                    "idOS":1,
                    "idActivity":10
                }
            ]
        """.trimIndent()

        val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {

            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/$WEB_GET_OS_LIST_BY_NRO_OS" -> MockResponse().setBody(jsonRetrofitOS)
                    "/$WEB_GET_R_OS_ACTIVITY_LIST_BY_NRO_OS" -> MockResponse().setBody(
                        jsonRetrofitROSActivity
                    )
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

        val mockWebServer = MockWebServer()
        mockWebServer.dispatcher = dispatcherSuccessFlow
        mockWebServer.start()

        BaseUrlModuleTest.url = mockWebServer.url("/").toString()

        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 10,
                    codActivity = 20,
                    descrActivity = "ATIVIDADE 1"
                )
            )
        )

        rEquipActivityDao.insertAll(
            listOf(
                REquipActivityRoomModel(
                    idREquipActivity = 1,
                    idEquip = 30,
                    idActivity = 10
                )
            )
        )

        rActivityStopDao.insertAll(
            listOf(
                RActivityStopRoomModel(
                    idRActivityStop= 1,
                    idActivity = 10,
                    idStop = 1
                )
            )
        )

        stopDao.insertAll(
            listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 200,
                    descrStop = "PARADA PARA REFEICAO"
                )
            )
        )

    }

}