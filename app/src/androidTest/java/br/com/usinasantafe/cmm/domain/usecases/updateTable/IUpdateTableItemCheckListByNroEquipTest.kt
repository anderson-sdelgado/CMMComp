package br.com.usinasantafe.cmm.domain.usecases.updateTable

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.cmm.presenter.model.ResultUpdateModel
import br.com.usinasantafe.cmm.utils.LevelUpdate
import br.com.usinasantafe.cmm.utils.updatePercentage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IUpdateTableItemCheckListByNroEquipTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableItemCheckListByNroEquip

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var itemCheckListDao: ItemCheckListDao

    @Test
    fun verify_return_data_if_success() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultItemCheckListRetrofit)
            )

            BaseUrlModuleTest.url = mockWebServer.url("/").toString()

            hiltRule.inject()

            var pos = 0f

            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    app = "CMM",
                    idBD = 1,
                    nroEquip = 2200,
                    number = 16997417840,
                    version = "1.0",
                    password = "12345",
                    idEquip = 1
                )
            )
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
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_item_check_list",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = itemCheckListDao.listAll()
            assertEquals(
                roomModelList.size,
                2
            )
            val roomModel = roomModelList[0]
            assertEquals(
                roomModel.idItemCheckList,
                1
            )
            assertEquals(
                roomModel.idCheckList,
                101
            )
            assertEquals(
                roomModel.descrItemCheckList,
                "Verificar Nível de Óleo"
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.idItemCheckList,
                2
            )
            assertEquals(
                roomModel2.idCheckList,
                101
            )
            assertEquals(
                roomModel2.descrItemCheckList,
                "Verificar Freios"
            )
        }

    private val resultItemCheckListRetrofit = """
        [
          {"idItemCheckList":1,"idCheckList":101,"descrItemCheckList":"Verificar Nível de Óleo"},
          {"idItemCheckList":2,"idCheckList":101,"descrItemCheckList":"Verificar Freios"}
        ]
    """.trimIndent()

}