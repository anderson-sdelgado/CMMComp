package br.com.usinasantafe.cmm.presenter.view.common.activityList

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.utils.FlagUpdate
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ActivityListCommonScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var osDao: OSDao

    @Inject
    lateinit var rOSActivityDao: ROSActivityDao

    @Test
    fun check_open_screen_and_return_failure_if_not_have_data_in_table_config() =
        runTest(
            timeout = 10.minutes
        ) {

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ActivityListViewModel.activityList -> IGetActivityList -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_failure_if_not_have_data_in_table_header_moto_mec() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ActivityListViewModel.activityList -> IGetActivityList -> IHeaderMotoMecRepository.getNroOS -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_list_empty_if_not_have_data_in_all_table() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_list_without_data_in_table_os() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_list_with_data_in_table_os() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister(4)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_data_web_service_is_incorrect() =
        runTest(
            timeout = 10.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultActivityFailure)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ActivityListViewModel.updateAllDatabase -> UpdateTableAtividade -> IActivityRepository.addAll -> IActivityRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_activity.idActivity (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun verify_check_msg_success_update_if_web_service_return_data_correct() =
        runTest(
            timeout = 10.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultActivity)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("ATIVIDADE")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("ATUALIZAR DADOS")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            ActivityListCommonScreen(
                onNavOS = {},
                onNavMeasure = {},
                onNavStopList = {},
                onMenuNote = {},
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

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456,
            )
        )

        if (level == 2) return

        rEquipActivityDao.insertAll(
            listOf(
                REquipActivityRoomModel(
                    idREquipActivity = 1,
                    idEquip = 1,
                    idActivity = 1
                ),
                REquipActivityRoomModel(
                    idREquipActivity = 2,
                    idEquip = 1,
                    idActivity = 2
                ),
                REquipActivityRoomModel(
                    idREquipActivity = 3,
                    idEquip = 1,
                    idActivity = 3
                ),
                REquipActivityRoomModel(
                    idREquipActivity = 4,
                    idEquip = 4,
                    idActivity = 3
                ),
                REquipActivityRoomModel(
                    idREquipActivity = 5,
                    idEquip = 5,
                    idActivity = 3
                )
            )
        )
        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 1,
                    codActivity = 10,
                    descrActivity = "ATIVIDADE 1"
                ),
                ActivityRoomModel(
                    idActivity = 2,
                    codActivity = 20,
                    descrActivity = "ATIVIDADE 2"
                ),
                ActivityRoomModel(
                    idActivity = 3,
                    codActivity = 30,
                    descrActivity = "ATIVIDADE 3"
                )
            )
        )

        if (level == 3) return

        osDao.insertAll(
            listOf(
                OSRoomModel(
                    idOS = 1,
                    nroOS = 123456,
                    idLibOS = 10,
                    idPropAgr = 20,
                    areaOS = 50.5,
                    idEquip = 30
                ),
                OSRoomModel(
                    idOS = 2,
                    nroOS = 456789,
                    idLibOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0,
                    idEquip = 31
                )
            )
        )
        rOSActivityDao.insertAll(
            listOf(
                ROSActivityRoomModel(
                    idROSActivity = 1,
                    idOS = 1,
                    idActivity = 2
                ),
                ROSActivityRoomModel(
                    idROSActivity = 2,
                    idOS = 1,
                    idActivity = 3
                ),
                ROSActivityRoomModel(
                    idROSActivity = 3,
                    idOS = 1,
                    idActivity = 4
                ),
                ROSActivityRoomModel(
                    idROSActivity = 4,
                    idOS = 2,
                    idActivity = 3
                ),
                ROSActivityRoomModel(
                    idROSActivity = 5,
                    idOS = 3,
                    idActivity = 4
                )
            )
        )

        if (level == 4) return
    }

    private val resultActivityFailure = """
                [
                    {
                        "idActivity":10,
                        "codActivity":20,
                        "descrActivity":"TESTE 1"
                    },
                    {
                        "idActivity":10,
                        "codActivity":30,
                        "descrActivity":"TESTE 2"
                    }
                ]
            """.trimIndent()

    private val resultActivity = """
                [
                    {
                        "idActivity":10,
                        "codActivity":20,
                        "descrActivity":"TESTE 1"
                    },
                    {
                        "idActivity":20,
                        "codActivity":30,
                        "descrActivity":"TESTE 2"
                    }
                ]
            """.trimIndent()

}