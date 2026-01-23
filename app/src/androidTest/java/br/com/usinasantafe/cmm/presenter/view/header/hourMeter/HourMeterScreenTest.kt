package br.com.usinasantafe.cmm.presenter.view.header.hourMeter

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class HourMeterScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Test
    fun check_open() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_digit_add() = runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            setContent()
            composeTestRule.onNodeWithText("1")
                .performClick()
            composeTestRule.onNodeWithText("2")
                .performClick()
            composeTestRule.onNodeWithText("3")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_digit_clean() = runTest(
        timeout = 10.minutes
    ) {

        hiltRule.inject()

        setContent()
        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("2")
            .performClick()
        composeTestRule.onNodeWithText("3")
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
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("3")
            .performClick()
        composeTestRule.onNodeWithText("2")
            .performClick()

        composeTestRule.waitUntilTimeout(10_000)

    }

    @Test
    fun check_clean() = runTest(
        timeout = 10.minutes
    ) {

        hiltRule.inject()

        setContent()
        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("2")
            .performClick()
        composeTestRule.onNodeWithText("3")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()
        composeTestRule.onNodeWithText("APAGAR")
            .performClick()

        composeTestRule.waitUntilTimeout(10_000)

    }

    @Test
    fun check_msg_field_empty() = runTest(
        timeout = 10.minutes
    ) {

        hiltRule.inject()

        setContent()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"HORÍMETRO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

        composeTestRule.waitUntilTimeout(3_000)

    }

    @Test
    fun check_msg_failure_because_database_is_empty() = runTest(
        timeout = 10.minutes
    ) {

        hiltRule.inject()

        initialRegister(1)

        setContent()

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

        initialRegister(1)

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MeasureViewModel.checkMeasureHeader -> ICheckMeasureInitial -> IEquipRepository.getMeasureByIdEquip -> IEquipRoomDatasource.getMeasureByIdEquip -> java.lang.NullPointerException: Attempt to invoke virtual method 'double br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getMeasurement()' on a null object reference")

        composeTestRule.waitUntilTimeout(3_000)

    }

    @Test
    fun check_msg_if_measure_database_is_bigger_than_measure_input() = runTest (
        timeout = 10.minutes
    ) {

        hiltRule.inject()

        initialRegister(2)

        setContent()

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

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("text_alert_dialog_check").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_check").assertTextEquals("O HODÔMETRO REGISTRADO 10.000,0 É MENOR QUE O ANTERIOR DE 20.000,0. DESEJA MANTÊ-LO?")

        composeTestRule.waitUntilTimeout(3_000)


    }

    private fun setContent() {
        composeTestRule.setContent {
            HourMeterHeaderScreen(
                onNavActivityList = {},
                onNavMenuNote = {},
                onNavInitialMenu = {},
                onNavQuestionUpdateCheckList = {},
                onNavMotorPump = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        val gson = Gson()

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                idEquip = 1,
            )
        )

        if (level == 1) return

        val resultEquipRetrofit = """
                [
                    {
                        "id":1,
                        "nro":2200,
                        "codClass":1,
                        "descrClass":"TRATOR",
                        "codTurnEquip":1,
                        "idCheckList":1,
                        "typeEquip":1,
                        "hourmeter":100.0,
                        "measurement":20000.0,
                        "type":1,
                        "classify":1,
                        "flagApontMecan":True,
                        "flagApontPneu":True
                    }
                ]
            """.trimIndent()

        val itemTypeEquip = object : TypeToken<List<EquipRoomModel>>() {}.type
        val equipList = gson.fromJson<List<EquipRoomModel>>(resultEquipRetrofit, itemTypeEquip)
        equipDao.insertAll(equipList)

        if (level == 2) return

    }
}