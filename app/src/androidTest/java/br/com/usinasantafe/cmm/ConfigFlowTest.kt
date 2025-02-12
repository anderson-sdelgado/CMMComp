package br.com.usinasantafe.cmm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

//@UninstallModules(CommonModule::class)
@HiltAndroidTest
class ConfigFlowTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

//    @Module
//    @InstallIn(SingletonComponent::class)
//    interface TestModule {
//
//        @Binds
//        @Singleton
//        fun bindGetStatusSendMock(usecase: IGetStatusSendTest): GetStatusSend
//    }

    @Test
    fun config_initial() = runTest {

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.waitUntilTimeout(10_000)

    }

}