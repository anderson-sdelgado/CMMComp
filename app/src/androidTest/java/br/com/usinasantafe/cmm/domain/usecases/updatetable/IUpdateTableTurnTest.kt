package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.domain.entities.view.ResultUpdate
import br.com.usinasantafe.cmm.external.room.dao.stable.TurnDao
import br.com.usinasantafe.cmm.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.cmm.infra.models.sharedpreferences.ConfigSharedPreferencesModel
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
class IUpdateTableTurnTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableTurn

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var turnDao: TurnDao

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {
            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultTurnoRetrofit)
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
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_turno do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_turno",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_turno",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = turnDao.listAll()
            assertEquals(
                roomModelList.size,
                1
            )
            val roomModel = roomModelList[0]
            assertEquals(
                roomModel.idTurn,
                1
            )
            assertEquals(
                roomModel.codTurnEquip,
                1
            )
            assertEquals(
                roomModel.nroTurn,
                1
            )
            assertEquals(
                roomModel.descrTurn,
                "Turno 1"
            )
        }

}


val resultTurnoRetrofit = """
    [{"idTurn":1,"codTurnEquip":1,"nroTurn":1,"descrTurn":"Turno 1"}]
""".trimIndent()