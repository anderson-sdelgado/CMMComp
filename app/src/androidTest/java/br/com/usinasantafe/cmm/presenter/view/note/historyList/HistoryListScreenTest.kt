package br.com.usinasantafe.cmm.presenter.view.note.historyList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.TypeEquip
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class HistoryListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var noteMotoMecDao: NoteMotoMecDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Test
    fun check_open_screen_and_msg_failure_if_not_have_data_in_header_moto_mec_room() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. HistoryListViewModel.recoverHistoryList -> IHistoryList -> IMotoMecRepository.noteList -> IHeaderMotoMecRoomDatasource.getIdByHeaderOpen -> java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.Integer br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()' on a null object reference")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_empty_list_if_not_have_note() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONTÉM APONTAMENTO NO BOLETIM.").assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_msg_failure_if_not_have_data_in_stop_room() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. HistoryListViewModel.recoverHistoryList -> IHistoryList -> IStopRepository.getById -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_failure_if_not_have_data_in_activity_room() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. HistoryListViewModel.recoverHistoryList -> IHistoryList -> IActivityRepository.getById -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_show_list_if_process_execute_successfully() =
        runTest {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            HistoryListScreen()
        }
    }

    private suspend fun initialRegister(level: Int) {

        headerMotoMecDao.insert(
            HeaderMotoMecRoomModel(
                regOperator = 19759,
                idEquip = 1,
                typeEquip = TypeEquip.NORMAL,
                idTurn = 1,
                nroOS = 123456,
                idActivity = 1,
                hourMeterInitial = 123456.0,
                statusCon = true
            )
        )

        if (level == 1) return

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                idStop = 1,
                statusCon = true,
                dateHour = Date(1761056520000)
            )
        )

        noteMotoMecDao.insert(
            NoteMotoMecRoomModel(
                idHeader = 1,
                nroOS = 123456,
                idActivity = 1,
                statusCon = true,
                dateHour = Date(1761142920000)
            )
        )

        if (level == 2) return

        stopDao.insertAll(
            listOf(
                StopRoomModel(
                    idStop = 1,
                    codStop = 10,
                    descrStop = "CHECKLIST"
                )
            )
        )

        if (level == 3) return

        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 1,
                    codActivity = 20,
                    descrActivity = "TRANSPORTE DE CANA"
                )
            )
        )

        if (level == 4) return

    }

}