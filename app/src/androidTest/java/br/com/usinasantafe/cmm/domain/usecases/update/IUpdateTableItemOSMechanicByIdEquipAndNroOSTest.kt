package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMechanicDao
import br.com.usinasantafe.cmm.external.sharedpreferences.datasource.IMechanicSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.MechanicSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeEquip
import br.com.usinasantafe.cmm.utils.UpdateStatusState
import br.com.usinasantafe.cmm.utils.updatePercentage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class IUpdateTableItemOSMechanicByIdEquipAndNroOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableItemOSMechanicByIdEquipAndNroOS

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var mechanicSharedPreferencesDatasource: IMechanicSharedPreferencesDatasource

    @Inject
    lateinit var itemOSMechanicDao: ItemOSMechanicDao

    @Test
    fun check_return_failure_if_not_have_data_in_equip_shared_preferences() =
        runTest {

            hiltRule.inject()

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IGetToken -> IEquipRepository.getNroEquipMain -> IEquipSharedPreferencesDatasource.getNro -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IGetToken -> token -> java.lang.NullPointerException: app is required",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_mechanic_shared_preferences() =
        runTest {

            hiltRule.inject()

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "CMM",
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IMechanicRepository.getNroOS -> IMechanicSharedPreferencesDatasource.getNroOS -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun check_return_failure_if_have_failure_connection() =
        runTest {

            hiltRule.inject()

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "CMM",
                )
            )

            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.listByIdEquipAndNroOS -> IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }


    @Test
    fun check_return_failure_if_return_data_incorrect_web_service() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultItemOSMechanicRetrofitIncorrect)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "CMM",
                )
            )

            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.listByIdEquipAndNroOS -> IItemOSMechanicRetrofitDatasource.listByIdEquipAndNroOS -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 18 path \$[0].nroOS",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun check_return_failure_if_web_service_return_data_repeated() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultItemOSMechanicRetrofitRepeated)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "CMM",
                )
            )

            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                4
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 5f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(3f, 1f, 5f)
                )
            )
            assertEquals(
                list[3],
                UpdateStatusState(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableItemOSMechanicByIdEquipAndNroOS -> IItemOSMechanicRepository.addAll -> IItemOSMechanicRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_item_os_mechanic.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])",
                    currentProgress = 1f,
                    levelUpdate = null
                )
            )
        }

    @Test
    fun check_return_all_emit_if_web_service_return_data_competed() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultItemOSMechanicRetrofit)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            val listBefore = itemOSMechanicDao.all()
            assertEquals(
                listBefore.size,
                0
            )

            equipSharedPreferencesDatasource.save(
                EquipSharedPreferencesModel(
                    id = 10,
                    nro = 2200,
                    codClass = 1,
                    descrClass = "TRATOR",
                    codTurnEquip = 1,
                    idCheckList = 1,
                    typeEquip = TypeEquip.NORMAL,
                    hourMeter = 5000.0,
                    classify = 1,
                    flagMechanic = true,
                    flagTire = true
                )
            )

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    checkMotoMec = true,
                    idServ = 1,
                    version = "1.0",
                    app = "CMM",
                )
            )

            mechanicSharedPreferencesDatasource.save(
                MechanicSharedPreferencesModel(
                    nroOS = 123456
                )
            )

            val list = usecase(5f, 1f).toList()
            assertEquals(
                list.size,
                3
            )
            assertEquals(
                list[0],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(1f, 1f, 5f)
                )
            )
            assertEquals(
                list[1],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(2f, 1f, 5f)
                )
            )
            assertEquals(
                list[2],
                UpdateStatusState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_os_mechanic",
                    currentProgress = updatePercentage(3f, 1f, 5f)
                )
            )
            val listAfter = itemOSMechanicDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model1 = listAfter[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.nroOS,
                10
            )
            assertEquals(
                model1.seqItem,
                1
            )
            assertEquals(
                model1.idServ,
                10
            )
            assertEquals(
                model1.idComp,
                1
            )
        }


    private val resultItemOSMechanicRetrofitIncorrect = """
        [{"id":1,"nroOS":1dsfasdf0,"seqItem":1,,"idServ":10,"idComp":1}]
    """.trimIndent()


    private val resultItemOSMechanicRetrofitRepeated = """
        [
            {"id":1,"nroOS":10,"seqItem":1,"idServ":10,"idComp":1},
            {"id":1,"nroOS":10,"seqItem":1,"idServ":10,"idComp":1}
        ]
    """.trimIndent()


    private val resultItemOSMechanicRetrofit = """
        [
            {"id":1,"nroOS":10,"seqItem":1,"idServ":10,"idComp":1}
        ]
    """.trimIndent()
}