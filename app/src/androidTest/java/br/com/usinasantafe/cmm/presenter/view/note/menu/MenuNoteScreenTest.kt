package br.com.usinasantafe.cmm.presenter.view.note.menu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class MenuNoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.descrEquip -> IGetDescrEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {

            hiltRule.inject()

            initialRegister()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("2200 - TRATOR").assertIsDisplayed()
            composeTestRule.onNodeWithText("MENU").assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_failure_if_not_has_header_open() =
        runTest(
            timeout = 1.minutes
        ) {

            hiltRule.inject()

            initialRegister()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("2200 - TRATOR").assertIsDisplayed()
            composeTestRule.onNodeWithText("MENU").assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("FINALIZAR BOLETIM")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.onButtonReturn -> ICheckNoteHeaderOpen -> IHeaderMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen -> java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_not_has_note_in_header_open() =
        runTest(
            timeout = 1.minutes
        ) {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("2200 - TRATOR").assertIsDisplayed()
            composeTestRule.onNodeWithText("MENU").assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("FINALIZAR BOLETIM")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. MenuNoteViewModel.onButtonReturn -> ICheckNoteHeaderOpen -> IHeaderMotoMecRepository.getIdByHeaderOpen -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen -> java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0")

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            MenuNoteScreen(
                onNavOS = {},
                onNavActivityList = {},
                onNavMeasure = {}
            )
        }
    }


    private suspend fun initialRegister(level: Int = 1) {

        val gson = Gson()

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

        if (level == 1) return

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

        if (level == 2) return

    }

}