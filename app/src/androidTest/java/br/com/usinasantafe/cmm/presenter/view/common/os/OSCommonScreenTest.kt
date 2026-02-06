package br.com.usinasantafe.cmm.presenter.view.common.os

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.domain.usecases.motomec.SetNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.HasNroOS
import br.com.usinasantafe.cmm.domain.usecases.motomec.GetNroOSHeader
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.HeaderMotoMecSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.Args.FLOW_APP_ARG
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.FlowApp
import br.com.usinasantafe.cmm.presenter.view.motomec.common.os.OSCommonScreen
import br.com.usinasantafe.cmm.presenter.view.motomec.common.os.OSCommonViewModel
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class OSCommonScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var hasNroOS: HasNroOS

    @Inject
    lateinit var setNroOS: SetNroOS

    @Inject
    lateinit var getNroOSHeader: GetNroOSHeader

    @Test
    fun check_open() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun verify_check_msg_field_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"ORDEM DE SERVIÇO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun verify_check_msg_web_service_invalid() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("1")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("0")
                .performClick()
            composeTestRule.onNodeWithText("OK")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. OSViewModel.setNroOSHeader -> ICheckNroOS -> IOSRepository.getListByNroOS -> IOSRetrofitDatasource.getListByNroOS -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }


    @Test
    fun check_open_note_work() =
        runTest {

            hiltRule.inject()

            initialRegister()

            setContent(
                flowApp = FlowApp.NOTE_WORK
            )

            composeTestRule.waitUntilTimeout(10_000)

        }


    private fun setContent(
        flowApp: FlowApp = FlowApp.HEADER_INITIAL
    ) {
        composeTestRule.setContent {
            OSCommonScreen(
                viewModel = OSCommonViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            FLOW_APP_ARG to flowApp.ordinal
                        )
                    ),
                    hasNroOS = hasNroOS,
                    setNroOS = setNroOS,
                    getNroOSHeader = getNroOSHeader
                ),
                onNavTurn = {},
                onNavActivityList = {},
                onNavMenuNote = {}
            )
        }

    }


    private suspend fun initialRegister() {

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

        headerMotoMecSharedPreferencesDatasource.save(
            HeaderMotoMecSharedPreferencesModel(
                nroOS = 123456
            )
        )

    }
}

