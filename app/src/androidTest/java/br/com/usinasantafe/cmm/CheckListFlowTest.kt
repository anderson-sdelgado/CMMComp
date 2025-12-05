package br.com.usinasantafe.cmm

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemRespCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.HeaderMotoMecSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ItemRespCheckListSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.MainActivity
import br.com.usinasantafe.cmm.lib.FlagUpdate
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.lib.WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP
import br.com.usinasantafe.cmm.lib.WEB_OS_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.lib.WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS
import br.com.usinasantafe.cmm.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes


@HiltAndroidTest
class CheckListFlowTest {

    companion object {

        private lateinit var mockWebServer: MockWebServer

        private val jsonRetrofitOS = """
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

        private val jsonRetrofitROSActivity = """
            [
                {
                    "idROSActivity":1,
                    "idOS":1,
                    "idActivity":10
                }
            ]
        """.trimIndent()

        private val jsonRetrofitItemCheckList = """
            [
              {"idItemCheckList":1,"idCheckList":1,"descrItemCheckList":"Verificar Nível de Óleo"},
              {"idItemCheckList":2,"idCheckList":1,"descrItemCheckList":"Verificar Freios"},
              {"idItemCheckList":3,"idCheckList":1,"descrItemCheckList":"Verificar Pneu"},
              {"idItemCheckList":4,"idCheckList":1,"descrItemCheckList":"Verificar Banco"},
              {"idItemCheckList":5,"idCheckList":1,"descrItemCheckList":"Verificar Volante"}
            ]
        """.trimIndent()

        @BeforeClass
        @JvmStatic
        fun setupClass() {

            val dispatcherSuccessFlow: Dispatcher = object : Dispatcher() {

                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        "/$WEB_OS_LIST_BY_NRO_OS" -> MockResponse().setBody(jsonRetrofitOS)
                        "/$WEB_R_OS_ACTIVITY_LIST_BY_NRO_OS" -> MockResponse().setBody(jsonRetrofitROSActivity)
                        "/$WEB_CHECK_CHECK_LIST_BY_NRO_EQUIP" -> MockResponse().setBody("""{"qtd":1}""")
                        "/$WEB_ITEM_CHECK_LIST_LIST_BY_NRO_EQUIP" -> MockResponse().setBody(jsonRetrofitItemCheckList)
                        else -> MockResponse().setResponseCode(404)
                    }
                }
            }

            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = dispatcherSuccessFlow
            mockWebServer.start()

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            mockWebServer.shutdown()
        }
    }

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecSharedPreferencesDatasource: HeaderMotoMecSharedPreferencesDatasource

    @Inject
    lateinit var headerMotoMecDao: HeaderMotoMecDao

    @Inject
    lateinit var activityDao: ActivityDao

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var equipDao: EquipDao

    @Inject
    lateinit var rEquipActivityDao: REquipActivityDao

    @Inject
    lateinit var turnDao: TurnDao

    @Inject
    lateinit var headerCheckListSharedPreferencesDatasource: HeaderCheckListSharedPreferencesDatasource

    @Inject
    lateinit var itemRespCheckListSharedPreferencesDatasource: ItemRespCheckListSharedPreferencesDatasource

    @Inject
    lateinit var headerCheckListDao: HeaderCheckListDao

    @Inject
    lateinit var itemRespCheckListDao: ItemRespCheckListDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun flow() = runTest(
        timeout = 2.minutes
    ) {

        initialRegister()

        Log.d("TestDebug", "Position 1")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 2")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        Log.d("TestDebug", "Position 3")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("7")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("9")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 6")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("2200 - TRATOR")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 9")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("TURNO 1")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("TURNO 1")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("1")
            .performClick()
        composeTestRule.onNodeWithText("2")
            .performClick()
        composeTestRule.onNodeWithText("3")
            .performClick()
        composeTestRule.onNodeWithText("4")
            .performClick()
        composeTestRule.onNodeWithText("5")
            .performClick()
        composeTestRule.onNodeWithText("6")
            .performClick()
        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 16")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("ATIVIDADE")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("ATIVIDADE 1")
            .performClick()

        Log.d("TestDebug", "Position 17")

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

        Log.d("TestDebug", "Position 20")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CHECKLIST")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("ATENÇÃO! HÁ NOVAS ATUALIZAÇÕES PARA QUESTÕES DE CHECKLIST. DESEJA ATUALIZÁ-LAS?")
            .assertIsDisplayed()

        Log.d("TestDebug", "Position 21")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("SIM")
            .performClick()

        Log.d("TestDebug", "Position 22")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        Log.d("TestDebug", "Position 23")

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CONFORME (OK)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("NÃO CONFORME (REPARO)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        val resultGetHeader = headerCheckListSharedPreferencesDatasource.get()
        assertEquals(
            resultGetHeader.isSuccess,
            true
        )
        val header = resultGetHeader.getOrNull()!!
        assertEquals(
            header.regOperator,
            19759
        )
        assertEquals(
            header.nroTurn,
            1
        )
        assertEquals(
            header.nroEquip,
            2200
        )

        val resultListResp = itemRespCheckListSharedPreferencesDatasource.list()
        assertEquals(
            resultListResp.isSuccess,
            true
        )
        val listResp = resultListResp.getOrNull()!!
        assertEquals(
            listResp.size,
            2
        )
        val respItemModel1 = listResp[0]
        assertEquals(
            respItemModel1.idItem,
            1
        )
        assertEquals(
            respItemModel1.option,
            OptionRespCheckList.ACCORDING
        )
        val respItemModel2 = listResp[1]
        assertEquals(
            respItemModel2.idItem,
            2
        )
        assertEquals(
            respItemModel2.option,
            OptionRespCheckList.REPAIR
        )

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("RETORNAR")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("NÃO CONFORME (REPARO)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CONFORME (OK)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("NÃO CONFORME (ANALISAR)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        composeTestRule.onNodeWithText("CONFORME (OK)")
            .performClick()

        composeTestRule.waitUntilTimeout()

        val resultGetHeaderFinish = headerCheckListSharedPreferencesDatasource.get()
        assertEquals(
            resultGetHeaderFinish.isSuccess,
            true
        )
        val headerFinish = resultGetHeaderFinish.getOrNull()!!
        assertEquals(
            headerFinish.regOperator,
            19759
        )
        assertEquals(
            headerFinish.nroTurn,
            1
        )
        assertEquals(
            headerFinish.nroEquip,
            2200
        )

        val resultListRespFinish = itemRespCheckListSharedPreferencesDatasource.list()
        assertEquals(
            resultListRespFinish.isSuccess,
            true
        )
        val listRespFinish = resultListRespFinish.getOrNull()!!
        assertEquals(
            listRespFinish.size,
            5
        )
        val respItemModelFinish1 = listRespFinish[0]
        assertEquals(
            respItemModelFinish1.idItem,
            1
        )
        assertEquals(
            respItemModelFinish1.option,
            OptionRespCheckList.ACCORDING
        )
        val respItemModelFinish2 = listRespFinish[1]
        assertEquals(
            respItemModelFinish2.idItem,
            2
        )
        assertEquals(
            respItemModelFinish2.option,
            OptionRespCheckList.REPAIR
        )
        val respItemModelFinish3 = listRespFinish[2]
        assertEquals(
            respItemModelFinish3.idItem,
            3
        )
        assertEquals(
            respItemModelFinish3.option,
            OptionRespCheckList.ACCORDING
        )
        val respItemModelFinish4 = listRespFinish[3]
        assertEquals(
            respItemModelFinish4.idItem,
            4
        )
        assertEquals(
            respItemModelFinish4.option,
            OptionRespCheckList.ANALYZE
        )
        val respItemModelFinish5 = listRespFinish[4]
        assertEquals(
            respItemModelFinish5.idItem,
            5
        )
        assertEquals(
            respItemModelFinish5.option,
            OptionRespCheckList.ACCORDING
        )

        val headerListRoom = headerCheckListDao.all()
        assertEquals(
            headerListRoom.size,
            1
        )
        val headerRoom = headerListRoom[0]
        assertEquals(
            headerRoom.regOperator,
            19759
        )
        assertEquals(
            headerRoom.nroTurn,
            1
        )
        assertEquals(
            headerRoom.nroEquip,
            2200
        )
        assertEquals(
            headerRoom.statusSend,
            StatusSend.SEND
        )

        val respListRoom = itemRespCheckListDao.all()
        assertEquals(
            respListRoom.size,
            5
        )
        val respRoom1 = respListRoom[0]
        assertEquals(
            respRoom1.id,
            1
        )
        assertEquals(
            respRoom1.idItem,
            1
        )
        assertEquals(
            respRoom1.option,
            OptionRespCheckList.ACCORDING
        )
        val respRoom2 = respListRoom[1]
        assertEquals(
            respRoom2.id,
            2
        )
        assertEquals(
            respRoom2.idItem,
            2
        )
        assertEquals(
            respRoom2.option,
            OptionRespCheckList.REPAIR
        )
        val respRoom3 = respListRoom[2]
        assertEquals(
            respRoom3.id,
            3
        )
        assertEquals(
            respRoom3.idItem,
            3
        )
        assertEquals(
            respRoom3.option,
            OptionRespCheckList.ACCORDING
        )
        val respRoom4 = respListRoom[3]
        assertEquals(
            respRoom4.id,
            4
        )
        assertEquals(
            respRoom4.idItem,
            4
        )
        assertEquals(
            respRoom4.option,
            OptionRespCheckList.ANALYZE
        )
        val respRoom5 = respListRoom[4]
        assertEquals(
            respRoom5.id,
            5
        )
        assertEquals(
            respRoom5.idItem,
            5
        )
        assertEquals(
            respRoom5.option,
            OptionRespCheckList.ACCORDING
        )

        composeTestRule.waitUntilTimeout(3_000)

    }

    private suspend fun initialRegister() {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                nroEquip = 2200,
                password = "12345",
                idEquip = 30,
                checkMotoMec = true,
                idServ = 1,
                version = "1.0",
                app = "PMM",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        activityDao.insertAll(
            listOf(
                ActivityRoomModel(
                    idActivity = 10,
                    codActivity = 20,
                    descrActivity = "ATIVIDADE 1"
                ),
                ActivityRoomModel(
                    idActivity = 20,
                    codActivity = 30,
                    descrActivity = "ATIVIDADE 2"
                )
            )
        )

        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    regColab = 19759,
                    nameColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )

        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    id = 30,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 100.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )
        )

        rEquipActivityDao.insertAll(
            listOf(
                REquipActivityRoomModel(
                    idEquip = 30,
                    idActivity = 10
                ),
                REquipActivityRoomModel(
                    idEquip = 30,
                    idActivity = 20
                )
            )
        )

        turnDao.insertAll(
            listOf(
                TurnRoomModel(
                    idTurn = 1,
                    codTurnEquip = 1,
                    nroTurn = 1,
                    descrTurn = "TURNO 1"
                )
            )
        )

    }
}
