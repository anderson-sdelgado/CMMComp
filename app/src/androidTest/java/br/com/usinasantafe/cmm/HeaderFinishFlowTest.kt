package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_NO_ALERT_DIALOG_CHECK
import br.com.usinasantafe.cmm.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class HeaderFinishFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow() = runTest(
        timeout = 10.minutes
    ) {

        initialRegister()

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FINALIZAR BOLETIM")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("FINALIZAR BOLETIM")
            .performClick()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout(3_000)

        scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FINALIZAR BOLETIM")
            .performClick()

        Log.d("TestDebug", "Position 4")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 5")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("3")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("0")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_NO_ALERT_DIALOG_CHECK)
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout(3_000)

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

        Log.d("TestDebug", "Position 7")

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 8")

        val equip = equipDao.getByIdEquip(10)
        assertEquals(
            equip.hourMeter,
            300000.0,
            0.0
        )

        val headerMotoMecList = headerMotoMecDao.all()
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
            10
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
            1
        )
        assertEquals(
            headerMotoMec.hourMeterInitial,
            10000.0,
            0.0
        )
        assertEquals(
            headerMotoMec.hourMeterFinish!!,
            300000.0,
            0.0
        )
        assertEquals(
            headerMotoMec.status,
            Status.FINISH
        )

        composeTestRule.waitUntilTimeout(3_000)

    }

    private suspend fun initialRegister() {

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                regOperator = 19759,
                idEquip = 10,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeter = 200.0,
            )
        )

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 10,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 10000.0,
                dateHourInitial = Date(1748359002),
                statusCon = true
            )
        )

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 10,
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
                    idEquip = 10,
                    nroEquip = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeFert = 1,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true
                )
            )
        )

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = null,
                statusCon = true
            )
        )

    }


}