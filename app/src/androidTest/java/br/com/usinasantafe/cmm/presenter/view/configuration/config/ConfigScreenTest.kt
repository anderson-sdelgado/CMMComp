package br.com.usinasantafe.cmm.presenter.view.configuration.config

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.cmm.HiltTestActivity
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.lib.WEB_ALL_ACTIVITY
import br.com.usinasantafe.cmm.lib.WEB_ALL_COLAB
import br.com.usinasantafe.cmm.lib.WEB_ALL_FUNCTION_ACTIVITY
import br.com.usinasantafe.cmm.lib.WEB_ALL_FUNCTION_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_ITEM_MENU
import br.com.usinasantafe.cmm.lib.WEB_ALL_R_ACTIVITY_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_R_ITEM_MENU_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_STOP
import br.com.usinasantafe.cmm.lib.WEB_ALL_TURN
import br.com.usinasantafe.cmm.lib.WEB_EQUIP_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_SAVE_TOKEN
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var itemCheckListDao: ItemCheckListDao

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var stopDao: StopDao

    @Inject
    lateinit var turnDao: TurnDao

    @Inject
    lateinit var functionActivityDao: FunctionActivityDao

    @Inject
    lateinit var functionStopDao: FunctionStopDao

    @Inject
    lateinit var itemMenuDao: ItemMenuDao

    @Inject
    lateinit var rItemMenuStopDao: RItemMenuStopDao

    private val resultTokenFailure = """{"idServ":1a,"idEquip":1}""".trimIndent()

    private val resultToken = """{"idServ":1,"idEquip":1}""".trimIndent()

    private val resultActivityFailure = """
        [
            {"idActivity":1a,"codActivity":10,"descrActivity":"Test"},
            {"idActivity":2,"codActivity":20,"descrActivity":"Test2"}
        ]
    """.trimIndent()

    private val resultActivityRepeated = """
        [
            {"idActivity":1,"codActivity":10,"descrActivity":"Test"},
            {"idActivity":1,"codActivity":10,"descrActivity":"Test"}
        ]
    """.trimIndent()

    private val resultActivity = """
        [
            {"idActivity":1,"codActivity":10,"descrActivity":"Test"},
            {"idActivity":2,"codActivity":20,"descrActivity":"Test2"}
        ]
    """.trimIndent()

    private val resultColabFailure = """
        [
            {"regColab":19759a,"nameColab":"ANDERSON DA SILVA DELGADO"},
            {"regColab":18017,"nameColab":"RONALDO"}
        ]
    """.trimIndent()

    private val resultColabRepeated = """
        [
            {"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"},
            {"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"}
        ]
    """.trimIndent()

    private val resultColab = """
        [
            {"regColab":19759,"nameColab":"ANDERSON DA SILVA DELGADO"},
            {"regColab":18017,"nameColab":"RONALDO"}
        ]
    """.trimIndent()

    private val resultEquipFailure = """
        [
          {"id":"1a","nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1},
          {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1}
        ]
    """.trimIndent()

    private val resultEquipRepeated = """
        [
          {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1},
          {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1}
        ]
    """.trimIndent()

    private val resultEquip = """
        [
          {"id":1,"nro":1000001,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1},
          {"id":2,"nro":1000002,"codClass":2,"descrClass":"Classe 2","codTurnEquip":2,"idCheckList":1,"typeEquip":1,"hourMeter":100.0,"classify":1}
        ]
    """.trimIndent()

    private val resultFunctionActivityFailure = """
        [
          {"idFunctionActivity":"1a","idActivity":1,"typeActivity":1},
          {"idFunctionActivity":2,"idActivity":2,"typeActivity":2}
        ]
    """.trimIndent()

    private val resultFunctionActivityRepeated = """
        [
          {"idFunctionActivity":1,"idActivity":1,"typeActivity":1},
          {"idFunctionActivity":1,"idActivity":1,"typeActivity":1}
        ]
    """.trimIndent()

    private val resultFunctionActivity = """
        [
          {"idFunctionActivity":1,"idActivity":1,"typeActivity":1},
          {"idFunctionActivity":2,"idActivity":2,"typeActivity":2}
        ]
    """.trimIndent()

    private val resultFunctionStopFailure = """
        [
          {"idFunctionStop":"1a","idStop":1,"typeStop":1},
          {"idFunctionStop":2,"idStop":2,"typeStop":2}
        ]
    """.trimIndent()

    private val resultFunctionStopRepeated = """
        [
          {"idFunctionStop":1,"idStop":1,"typeStop":1},
          {"idFunctionStop":1,"idStop":1,"typeStop":1}
        ]
    """.trimIndent()

    private val resultFunctionStop = """
        [
          {"idFunctionStop":1,"idStop":1,"typeStop":1},
          {"idFunctionStop":2,"idStop":2,"typeStop":2}
        ]
    """.trimIndent()

    private val resultItemCheckListFailure = """
        [
          {"idItemCheckList":"1a","idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
          {"idItemCheckList":2,"idCheckList":101,"descrItemCheckList":"Verificar Freios"}
        ]
    """.trimIndent()

    private val resultItemCheckListRepeated = """
        [
          {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
          {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"}
        ]
    """.trimIndent()

    private val resultItemCheckList = """
        [
          {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
          {"idItemCheckList":2,"idCheckList":101,"descrItemCheckList":"Verificar Freios"}
        ]
    """.trimIndent()

    private val resultItemMenuFailure = """
        [
          {"id":"1a","descr":"ITEM 1","idType": 1,"pos": 1,"idFunction": 1,"idApp": 1},
          {"id":2,"descr":"ITEM 2","idType": 1,"pos": 2,"idFunction": 2,"idApp": 1}
        ]
    """.trimIndent()

    private val resultItemMenuRepeated = """
        [
          {"id":1,"descr":"ITEM 1","idType": 1,"pos": 1,"idFunction": 1,"idApp": 1},
          {"id":1,"descr":"ITEM 1","idType": 1,"pos": 2,"idFunction": 2,"idApp": 1}
        ]
    """.trimIndent()

    private val resultItemMenu = """
        [
          {"id":1,"descr":"ITEM 1","idType": 1,"pos": 1,"idFunction": 1,"idApp": 1},
          {"id":2,"descr":"ITEM 2","idType": 1,"pos": 2,"idFunction": 2,"idApp": 1}
        ]
    """.trimIndent()

    private val resultRActivityStopFailure = """
        [
            {"idActivity":"101a","idStop":301},
            {"idActivity":102,"idStop":303}
        ]
    """.trimIndent()

    private val resultRActivityStop = """
        [
            {"idActivity":101,"idStop":301},
            {"idActivity":102,"idStop":303}
        ]
    """.trimIndent()

    private val resultREquipActivityFailure = """
        [
          {"idEquip":"30a","idActivity":10},
          {"idEquip":40,"idActivity":10}
        ]
    """.trimIndent()

    private val resultREquipActivity = """
        [
          {"idREquipActivity":1,"idEquip":30,"idActivity":10},
          {"idREquipActivity":2,"idEquip":40,"idActivity":10}
        ]
    """.trimIndent()

    private val resultStopFailure = """
        [
            {"idStop":"1a","codStop":10,"descrStop":"PARADA PARA ALMOCO"},
            {"idStop":2,"codStop":20,"descrStop":"CHUVA"}
        ]
    """.trimIndent()

    private val resultStopRepeated = """
        [
            {"idStop":1,"codStop":10,"descrStop":"PARADA PARA ALMOCO"},
            {"idStop":1,"codStop":10,"descrStop":"PARADA PARA ALMOCO"}
        ]
    """.trimIndent()

    private val resultStop = """
        [
            {"idStop":1,"codStop":10,"descrStop":"PARADA PARA ALMOCO"},
            {"idStop":2,"codStop":20,"descrStop":"CHUVA"}
        ]
    """.trimIndent()

    private val resultTurnFailure = """
        [
          {"idTurn":1a,"codTurnEquip":1,nroTurn":1,"descrTurn":"Turno 1"},
          {"idTurn":2,"codTurnEquip":2,"nroTurn":2,"descrTurn":"Turno 2"}
        ]
    """.trimIndent()

    private val resultTurnRepeated = """
        [
          {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"},
          {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"}
        ]
    """.trimIndent()

    private val resultTurn = """
        [
          {"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"},
          {"idTurn":2,"codTurnEquip":2,"nroTurn":2,"descrTurn":"Turno 2"}
        ]
    """.trimIndent()

    private val resultRItemMenuStopFailure = """
        [
            {"id": "1a","idFunction": 1,"idApp": 1,"idStop": 1},
            {"id": 2,"idFunction": 2,"idApp": 2,"idStop": 2}
        ]
    """.trimIndent()

    private val resultRItemMenuStopRepeated = """
        [
            {"id": 1,"idFunction": 1,"idApp": 1,"idStop": 1},
            {"id": 1,"idFunction": 1,"idApp": 1,"idStop": 1}
        ]
    """.trimIndent()

    private val resultRItemMenuStop = """
        [
            {"id": 1,"idFunction": 1,"idApp": 1,"idStop": 1},
            {"id": 2,"idFunction": 2,"idApp": 2,"idStop": 2}
        ]
    """.trimIndent()

    private val dispatcherTokenFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultTokenFailure)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherToken: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherActivityFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivityFailure)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherActivityRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivityRepeated)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherActivity: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColabFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabFailure)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColabRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabRepeated)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColab: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherEquipFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquipFailure)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherEquipRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquipRepeated)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherEquip: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody("")
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionActivityFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivityFailure)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionActivityRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivityRepeated)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionActivity: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody("")
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionStopFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStopFailure)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionStopRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStopRepeated)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherFunctionStop: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemCheckListFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckListFailure)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemCheckListRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckListRepeated)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemCheckList: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody("")
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemMenuFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenuFailure)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemMenuRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenuRepeated)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherItemMenu: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody("")
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherRActivityStopFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStopFailure)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherRActivityStop: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody("")
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherREquipActivityFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivityFailure)
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherREquipActivity: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherRItemMenuStopFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStopFailure)
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherRItemMenuStopRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStopRepeated)
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherRItemMenuStop: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody("")
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherStopFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStopFailure)
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherStopRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStopRepeated)
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherStop: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                "/$WEB_ALL_TURN" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherTurnFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurnFailure)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherTurnRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurnRepeated)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSuccess: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_ACTIVITY" -> MockResponse().setBody(resultActivity)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColab)
                "/$WEB_EQUIP_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultEquip)
                "/$WEB_ALL_FUNCTION_ACTIVITY" -> MockResponse().setBody(resultFunctionActivity)
                "/$WEB_ALL_FUNCTION_STOP" -> MockResponse().setBody(resultFunctionStop)
                "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(resultItemCheckList)
                "/$WEB_ALL_ITEM_MENU"  -> MockResponse().setBody(resultItemMenu)
                "/$WEB_ALL_R_ACTIVITY_STOP" -> MockResponse().setBody(resultRActivityStop)
                "/$WEB_R_EQUIP_ACTIVITY_LIST_BY_ID_EQUIP" -> MockResponse().setBody(resultREquipActivity)
                "/$WEB_ALL_R_ITEM_MENU_STOP" -> MockResponse().setBody(resultRItemMenuStop)
                "/$WEB_ALL_STOP" -> MockResponse().setBody(resultStop)
                "/$WEB_ALL_TURN" -> MockResponse().setBody(resultTurn)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun verify_check_open_screen_config_and_service_without_connection() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            val result = configSharedPreferencesDatasource.has()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_token_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherTokenFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 11 path \$.idServ")

            val result = configSharedPreferencesDatasource.has()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_token_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherToken
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableActivity -> IActivityRepository.listAll -> IActivityRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            val result = configSharedPreferencesDatasource.has()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    nroEquip = 2200,
                    password = "12345",
                    idEquip = 1,
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "ECM",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_activity_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherActivityFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableActivity -> IActivityRepository.listAll -> IActivityRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 19 path \$[0].idActivity")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_activity_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherActivityRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableActivity -> IActivityRepository.addAll -> IActivityRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_activity.idActivity (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_activity_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherActivity
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(1)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherColabFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 17 path \$[0].regColab")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherColabRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.addAll -> IColabRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.regColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherColab
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableEquipByIdEquip -> IEquipRepository.listByIdEquip -> IEquipRetrofitDatasource.listByIdEquip -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(2)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_equip_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherEquipFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableEquipByIdEquip -> IEquipRepository.listByIdEquip -> IEquipRetrofitDatasource.listByIdEquip -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_equip_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherEquipRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableEquipByIdEquip -> IEquipRepository.addAll -> IEquipRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_equip.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_equip_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherEquip
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionActivity -> IFunctionActivityRepository.listAll -> IFunctionActivityRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(3)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_activity_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionActivityFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionActivity -> IFunctionActivityRepository.listAll -> IFunctionActivityRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_activity_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionActivityRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionActivity -> IFunctionActivityRepository.addAll -> IFunctionActivityRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_function_activity.idFunctionActivity (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_activity_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionActivity
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionStop -> IFunctionStopRepository.listAll -> IFunctionStopRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(4)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_stop_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionStopFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionStop -> IFunctionStopRepository.listAll -> IFunctionStopRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_stop_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionStopRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableFunctionStop -> IFunctionStopRepository.addAll -> IFunctionStopRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_function_stop.idFunctionStop (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_function_stop_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherFunctionStop
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.listByNroEquip -> IItemCheckListRetrofitDatasource.listByNroEquip -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(5)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_check_list_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemCheckListFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.listByNroEquip -> IItemCheckListRetrofitDatasource.listByNroEquip -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_check_list_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemCheckListRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemCheckListByNroEquip -> IItemCheckListRepository.addAll -> IItemCheckListRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_item_check_list.idItemCheckList (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_check_list_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemCheckList
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemMenu -> IItemMenuRepository.listAll -> IItemMenuRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(6)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_menu_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemMenuFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemMenu -> IItemMenuRepository.listAll -> IItemMenuRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_menu_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemMenuRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableItemMenu -> IItemMenuRepository.addAll -> IItemMenuRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_item_menu.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_item_menu_pmm_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherItemMenu
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableRActivityStop -> IRActivityStopRepository.listAll -> IRActivityStopRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(7)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_activity_stop_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherRActivityStopFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableRActivityStop -> IRActivityStopRepository.listAll -> IRActivityStopRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"101a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_activity_stop_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherRActivityStop
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableREquipActivityByIdEquip -> IREquipActivityRepository.listByIdEquip -> IREquipActivityRetrofitDatasource.listByIdEquip -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(8)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_equip_activity_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherREquipActivityFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableREquipActivityByIdEquip -> IREquipActivityRepository.listByIdEquip -> IREquipActivityRetrofitDatasource.listByIdEquip -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 23 path $[0].idREquipActivity")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_equip_activity_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherREquipActivity
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableRItemMenuStop -> IRItemMenuStopRepository.listAll -> IRItemMenuStopRetrofitDatasource.listAll -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

            asserts(9)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_item_menu_stop_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherRItemMenuStopFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableRItemMenuStop -> IRItemMenuStopRepository.listAll -> IRItemMenuStopRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_item_menu_stop_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherRItemMenuStopRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableRItemMenuStop -> IRItemMenuStopRepository.addAll -> IRItemMenuStopRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_r_item_menu_stop.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_r_item_menu_stop_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherRItemMenuStop
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableStop -> IStopRepository.listAll -> IStopRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(10)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_stop_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherStopFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableStop -> IStopRepository.listAll -> IStopRetrofitDatasource.listAll -> com.google.gson.JsonSyntaxException: java.lang.NumberFormatException: For input string: \"1a\"")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_stop_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherStopRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableStop -> IStopRepository.addAll -> IStopRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_stop.idStop (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_stop_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherStop
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableTurn -> ITurnRepository.listAll -> ITurnRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout()

            asserts(11)

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_turn_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherTurnFailure
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableTurn -> ITurnRepository.listAll -> ITurnRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 13 path \$[0].idTurn")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_turn_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherTurnRepeated
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableTurn -> ITurnRepository.addAll -> ITurnRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_turn.idTurn (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_of_success_if_web_service_return_all_data_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccess
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_NRO_EQUIP_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("2200")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CONFIGURAÇÃO REALIZADA COM SUCESSO!")

            composeTestRule.waitUntilTimeout()

            asserts(12)

            composeTestRule.waitUntilTimeout()

        }

    private fun setContent() {
        composeTestRule.setContent {
            ConfigScreen (
                onNavInitialMenu = {},
            )
        }
    }

    private suspend fun asserts(level: Int) {

        val activityRoomModelList = activityDao.all()
        assertEquals(
            activityRoomModelList.size,
            2
        )
        val activityRoomModel1 = activityRoomModelList[0]
        assertEquals(
            activityRoomModel1.idActivity,
            1
        )
        assertEquals(
            activityRoomModel1.codActivity,
            10
        )
        assertEquals(
            activityRoomModel1.descrActivity,
            "Test"
        )
        val activityRoomModel2 = activityRoomModelList[1]
        assertEquals(
            activityRoomModel2.idActivity,
            2
        )
        assertEquals(
            activityRoomModel2.codActivity,
            20
        )
        assertEquals(
            activityRoomModel2.descrActivity,
            "Test2"
        )

        if(level == 1) return

        val colabRoomModelList = colabDao.all()
        assertEquals(
            colabRoomModelList.size,
            2
        )
        val colabRoomModel1 = colabRoomModelList[0]
        assertEquals(
            colabRoomModel1.regColab,
            18017
        )
        assertEquals(
            colabRoomModel1.nameColab,
            "RONALDO"
        )
        val colabRoomModel2 = colabRoomModelList[1]
        assertEquals(
            colabRoomModel2.regColab,
            19759
        )
        assertEquals(
            colabRoomModel2.nameColab,
            "ANDERSON DA SILVA DELGADO"
        )

        if(level == 2) return

        val equipRoomModelList = equipDao.all()
        assertEquals(
            equipRoomModelList.size,
            2
        )
        val equipRoomModel1 = equipRoomModelList[0]
        assertEquals(
            equipRoomModel1.id,
            1
        )
        assertEquals(
            equipRoomModel1.nro,
            1000001
        )
        assertEquals(
            equipRoomModel1.codClass,
            1
        )
        assertEquals(
            equipRoomModel1.descrClass,
            "Classe 1"
        )
        assertEquals(
            equipRoomModel1.codTurnEquip,
            1
        )
        assertEquals(
            equipRoomModel1.idCheckList,
            1
        )
        assertEquals(
            equipRoomModel1.typeEquip,
            TypeEquip.NORMAL
        )
        assertEquals(
            equipRoomModel1.hourMeter,
            100.0
        )
        assertEquals(
            equipRoomModel1.classify,
            1
        )
        val equipRoomModel2 = equipRoomModelList[1]
        assertEquals(
            equipRoomModel2.id,
            2
        )
        assertEquals(
            equipRoomModel2.nro,
            1000002
        )
        assertEquals(
            equipRoomModel2.codClass,
            2
        )
        assertEquals(
            equipRoomModel2.descrClass,
            "Classe 2"
        )
        assertEquals(
            equipRoomModel2.codTurnEquip,
            2
        )
        assertEquals(
            equipRoomModel2.idCheckList,
            1
        )
        assertEquals(
            equipRoomModel2.typeEquip,
            TypeEquip.NORMAL
        )
        assertEquals(
            equipRoomModel2.hourMeter,
            100.0
        )
        assertEquals(
            equipRoomModel2.classify,
            1
        )

        if(level == 3) return

        val functionActivityRoomModelList = functionActivityDao.all()
        assertEquals(
            functionActivityRoomModelList.size,
            2
        )
        val functionActivityRoomModel1 = functionActivityRoomModelList[0]
        assertEquals(
            functionActivityRoomModel1.idFunctionActivity,
            1
        )
        assertEquals(
            functionActivityRoomModel1.idActivity,
            1
        )
        assertEquals(
            functionActivityRoomModel1.typeActivity,
            TypeActivity.PERFORMANCE
        )
        val functionActivityRoomModel2 = functionActivityRoomModelList[1]
        assertEquals(
            functionActivityRoomModel2.idFunctionActivity,
            2
        )
        assertEquals(
            functionActivityRoomModel2.idActivity,
            2
        )
        assertEquals(
            functionActivityRoomModel2.typeActivity,
            TypeActivity.TRANSHIPMENT
        )

        if(level == 4) return

        val functionStopRoomModelList = functionStopDao.all()
        assertEquals(
            functionStopRoomModelList.size,
            2
        )
        val functionStopRoomModel1 = functionStopRoomModelList[0]
        assertEquals(
            functionStopRoomModel1.idFunctionStop,
            1
        )
        assertEquals(
            functionStopRoomModel1.idStop,
            1
        )
        assertEquals(
            functionStopRoomModel1.typeStop,
            TypeStop.CHECKLIST
        )
        val functionStopRoomModel2 = functionStopRoomModelList[1]
        assertEquals(
            functionStopRoomModel2.idFunctionStop,
            2
        )
        assertEquals(
            functionStopRoomModel2.idStop,
            2
        )
        assertEquals(
            functionStopRoomModel2.typeStop,
            TypeStop.IMPLEMENT
        )

        if(level == 5) return

        val itemCheckListRoomModelList = itemCheckListDao.all()
        assertEquals(
            itemCheckListRoomModelList.size,
            2
        )
        val itemCheckListRoomModel1 = itemCheckListRoomModelList[0]
        assertEquals(
            itemCheckListRoomModel1.idItemCheckList,
            1
        )
        assertEquals(
            itemCheckListRoomModel1.idCheckList,
            101
        )
        assertEquals(
            itemCheckListRoomModel1.descrItemCheckList,
            "Verificar Nível de Óleo"
        )
        val itemCheckListRoomModel2 = itemCheckListRoomModelList[1]
        assertEquals(
            itemCheckListRoomModel2.idItemCheckList,
            2
        )
        assertEquals(
            itemCheckListRoomModel2.idCheckList,
            101
        )
        assertEquals(
            itemCheckListRoomModel2.descrItemCheckList,
            "Verificar Freios"
        )

        if(level == 6) return

        val itemMenuPMMRoomModelList = itemMenuDao.all()
        assertEquals(
            itemMenuPMMRoomModelList.size,
            2
        )
        val itemMenuPMMRoomModel1 = itemMenuPMMRoomModelList[0]
        assertEquals(
            itemMenuPMMRoomModel1.id,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.descr,
            "ITEM 1"
        )
        assertEquals(
            itemMenuPMMRoomModel1.idType,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.pos,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.idFunction,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel1.idApp,
            1
        )
        val itemMenuPMMRoomModel2 = itemMenuPMMRoomModelList[1]
        assertEquals(
            itemMenuPMMRoomModel2.id,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.descr,
            "ITEM 2"
        )
        assertEquals(
            itemMenuPMMRoomModel2.idType,
            1
        )
        assertEquals(
            itemMenuPMMRoomModel2.pos,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.idFunction,
            2
        )
        assertEquals(
            itemMenuPMMRoomModel2.idApp,
            1
        )

        if(level == 7) return

        val rActivityStopRoomModelList = rActivityStopDao.all()
        assertEquals(
            rActivityStopRoomModelList.size,
            2
        )
        val rActivityStopRoomModel1 = rActivityStopRoomModelList[0]
        assertEquals(
            rActivityStopRoomModel1.idRActivityStop,
            1
        )
        assertEquals(
            rActivityStopRoomModel1.idActivity,
            101
        )
        assertEquals(
            rActivityStopRoomModel1.idStop,
            301
        )
        val rActivityStopRoomModel2 = rActivityStopRoomModelList[1]
        assertEquals(
            rActivityStopRoomModel2.idRActivityStop,
            2
        )
        assertEquals(
            rActivityStopRoomModel2.idActivity,
            102
        )
        assertEquals(
            rActivityStopRoomModel2.idStop,
            303
        )

        if(level == 8) return

        val rEquipActivityRoomModelList = rEquipActivityDao.all()
        assertEquals(
            rEquipActivityRoomModelList.size,
            2
        )
        val rEquipActivityRoomModel1 = rEquipActivityRoomModelList[0]
        assertEquals(
            rEquipActivityRoomModel1.idREquipActivity,
            1
        )
        assertEquals(
            rEquipActivityRoomModel1.idEquip,
            30
        )
        assertEquals(
            rEquipActivityRoomModel1.idActivity,
            10
        )
        val rEquipActivityRoomModel2 = rEquipActivityRoomModelList[1]
        assertEquals(
            rEquipActivityRoomModel2.idREquipActivity,
            2
        )
        assertEquals(
            rEquipActivityRoomModel2.idEquip,
            40
        )
        assertEquals(
            rEquipActivityRoomModel2.idActivity,
            10
        )

        if(level == 9) return

        val rItemMenuStopRoomModelList = rItemMenuStopDao.all()
        assertEquals(
            rItemMenuStopRoomModelList.size,
            2
        )
        val rItemMenuStopRoomModel1 = rItemMenuStopRoomModelList[0]
        assertEquals(
            rItemMenuStopRoomModel1.id,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idFunction,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idApp,
            1
        )
        assertEquals(
            rItemMenuStopRoomModel1.idStop,
            1
        )
        val rItemMenuStopRoomModel2 = rItemMenuStopRoomModelList[1]
        assertEquals(
            rItemMenuStopRoomModel2.id,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idFunction,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idApp,
            2
        )
        assertEquals(
            rItemMenuStopRoomModel2.idStop,
            2
        )

        if(level == 10) return

        val stopRoomModelList = stopDao.all()
        assertEquals(
            stopRoomModelList.size,
            2
        )
        val stopRoomModel1 = stopRoomModelList[0]
        assertEquals(
            stopRoomModel1.idStop,
            1
        )
        assertEquals(
            stopRoomModel1.codStop,
            10
        )
        assertEquals(
            stopRoomModel1.descrStop,
            "PARADA PARA ALMOCO"
        )
        val stopRoomModel2 = stopRoomModelList[1]
        assertEquals(
            stopRoomModel2.idStop,
            2
        )
        assertEquals(
            stopRoomModel2.codStop,
            20
        )
        assertEquals(
            stopRoomModel2.descrStop,
            "CHUVA"
        )

        if(level == 11) return

        val turnRoomModelList = turnDao.all()
        assertEquals(
            turnRoomModelList.size,
            2
        )
        val turnRoomModel1 = turnRoomModelList[0]
        assertEquals(
            turnRoomModel1.idTurn,
            1
        )
        assertEquals(
            turnRoomModel1.codTurnEquip,
            1
        )
        assertEquals(
            turnRoomModel1.nroTurn,
            1
        )
        assertEquals(
            turnRoomModel1.descrTurn,
            "Turno 1"
        )
        val turnRoomModel2 = turnRoomModelList[1]
        assertEquals(
            turnRoomModel2.idTurn,
            2
        )
        assertEquals(
            turnRoomModel2.codTurnEquip,
            2
        )
        assertEquals(
            turnRoomModel2.nroTurn,
            2
        )
        assertEquals(
            turnRoomModel2.descrTurn,
            "Turno 2"
        )

        if(level == 12) return

    }

}