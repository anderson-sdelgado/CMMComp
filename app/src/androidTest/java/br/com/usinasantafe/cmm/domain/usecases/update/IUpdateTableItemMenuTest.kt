package br.com.usinasantafe.cmm.domain.usecases.update

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.utils.Errors
import br.com.usinasantafe.cmm.utils.LevelUpdate
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
class IUpdateTableItemMenuTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableItemMenu

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var itemMenuDao: ItemMenuDao

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
                    tableUpdate = "tb_item_menu",
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
                    failure = "IUpdateTableItemMenu -> IGetToken -> java.lang.NullPointerException",
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
                    tableUpdate = "tb_item_menu",
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
                    failure = "IUpdateTableItemMenu -> IItemMenuRepository.listAll -> IItemMenuRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$",
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
                    tableUpdate = "tb_item_menu",
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
                    failure = "IUpdateTableItemMenu -> IItemMenuRepository.listAll -> IItemMenuRetrofitDatasource.listAll -> java.lang.NullPointerException",
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
                MockResponse().setBody(resultItemMenuRetrofit)
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
                    tableUpdate = "tb_item_menu",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_menu",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_menu",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = itemMenuDao.all()
            assertEquals(
                roomModelList.size,
                2
            )
            val roomModel1 = roomModelList[0]
            assertEquals(
                roomModel1.id,
                1
            )
            assertEquals(
                roomModel1.descr,
                "ITEM 1"
            )
            assertEquals(
                roomModel1.idType,
                1
            )
            assertEquals(
                roomModel1.pos,
                1
            )
            assertEquals(
                roomModel1.idFunction,
                1
            )
            assertEquals(
                roomModel1.idApp,
                1
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.id,
                2
            )
            assertEquals(
                roomModel2.descr,
                "ITEM 2"
            )
            assertEquals(
                roomModel2.idType,
                1
            )
            assertEquals(
                roomModel2.pos,
                2
            )
            assertEquals(
                roomModel2.idFunction,
                2
            )
            assertEquals(
                roomModel2.idApp,
                1
            )
        }

    private val resultItemMenuRetrofit = """
        [
            {
                    "id": 1,
                    "descr": "ITEM 1",
                    "idType": 1,
                    "pos": 1,
                    "idFunction": 1,
                    "idApp": 1
                },
                {
                    "id": 2,
                    "descr": "ITEM 2",
                    "idType": 1,
                    "pos": 2,
                    "idFunction": 2,
                    "idApp": 1
                }
        ]
    """.trimIndent()

    private suspend fun initialRegister() {
        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                app = "CMM",
                idServ = 1,
                nroEquip = 2200,
                number = 16997417840,
                version = "1.0",
                password = "12345",
                idEquip = 1
            )
        )
    }
}