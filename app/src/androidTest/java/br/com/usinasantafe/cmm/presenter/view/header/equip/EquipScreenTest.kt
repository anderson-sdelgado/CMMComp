package br.com.usinasantafe.cmm.presenter.view.header.equip

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class EquipScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. EquipViewModel.getDescr -> IGetDescrEquip -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_return_data_correct_if_have_data() =
        runTest {

            hiltRule.inject()

            initialRegister()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithText("2200 - TRATOR").assertIsDisplayed()

            composeTestRule.waitUntilTimeout(3_000)

        }

    private fun setContent() {
        composeTestRule.setContent {
            EquipHeaderScreen(
                onNavOperator = {},
                onNavTurnList = {}
            )
        }
    }

    private suspend fun initialRegister() {

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

    }
}