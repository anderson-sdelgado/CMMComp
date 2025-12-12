package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.EquipSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.EquipSharedPreferencesModel
import br.com.usinasantafe.cmm.lib.Errors
import br.com.usinasantafe.cmm.lib.LevelUpdate
import br.com.usinasantafe.cmm.lib.TypeEquipMain
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
class IUpdateTableRActivityStopTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableRActivityStop

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipSharedPreferencesDatasource: EquipSharedPreferencesDatasource

    @Inject
    lateinit var rActivityStopDao: RActivityStopDao

    @Test
    fun check_return_failure_if_not_have_data_config_internal() =
        runTest {

            hiltRule.inject()

            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IGetToken -> IConfigRepository.get -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun check_return_failure_if_not_return_web_service() =
        runTest {

            hiltRule.inject()

            initialRegister()

            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.listAll -> IRActivityStopRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun check_return_failure_if_token_is_invalid() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister()

            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.listAll -> IRActivityStopRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun check_return_failure_if_have_error_404() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister()

            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(1f, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableRActivityStop -> IRActivityStopRepository.listAll -> IRActivityStopRetrofitDatasource.listAll -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun verify_return_data_if_success() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultRActivityStopRetrofit)
            )
            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            var pos = 0f

            initialRegister()

            val result = usecase(
                sizeAll = 16f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                list.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_r_activity_stop",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = rActivityStopDao.all()
            assertEquals(
                roomModelList.size,
                1
            )
            val roomModel = roomModelList[0]
            assertEquals(
                roomModel.idRActivityStop,
                1
            )
            assertEquals(
                roomModel.idActivity,
                101
            )
            assertEquals(
                roomModel.idStop,
                301
            )
        }

    private val resultRActivityStopRetrofit = """
        [{"idActivity":101,"idStop":301}]
    """.trimIndent()

    private suspend fun initialRegister() {
        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                app = "CMM",
                idServ = 1,
                number = 16997417840,
                version = "1.0",
                password = "12345",
                checkMotoMec = true
            )
        )
        equipSharedPreferencesDatasource.save(
            EquipSharedPreferencesModel(
                id = 10,
                nro = 2200,
                codClass = 1,
                descrClass = "TRATOR",
                codTurnEquip = 1,
                idCheckList = 1,
                typeEquip = TypeEquipMain.NORMAL,
                hourMeter = 5000.0,
                classify = 1,
                flagMechanic = true,
                flagTire = true
            )
        )
    }
}