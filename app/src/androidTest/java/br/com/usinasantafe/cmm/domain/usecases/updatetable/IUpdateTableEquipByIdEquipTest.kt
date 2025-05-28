package br.com.usinasantafe.cmm.domain.usecases.updatetable

import br.com.usinasantafe.cmm.di.external.BaseUrlModuleTest
import br.com.usinasantafe.cmm.domain.entities.ResultUpdate
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
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
class IUpdateTableEquipByIdEquipTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: UpdateTableEquipByIdEquip

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var equipDao: EquipDao

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {

            val mockWebServer = MockWebServer()
            mockWebServer.start()
            mockWebServer.enqueue(
                MockResponse().setBody(resultEquipRetrofit)
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
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = updatePercentage(++pos, 1f, 16f)
                )
            )
            val roomModelList = equipDao.listAll()
            assertEquals(
                roomModelList.size,
                1
            )
            val roomModel = roomModelList[0]
            assertEquals(
                roomModel.idEquip,
                1
            )
            assertEquals(
                roomModel.nroEquip,
                10
            )
            assertEquals(
                roomModel.codClass,
                1
            )
            assertEquals(
                roomModel.descrClass,
                "Classe 1"
            )
            assertEquals(
                roomModel.hourmeter,
                100.0,
                0.0
            )
            assertEquals(
                roomModel.measurement,
                200.0,
                0.0
            )
            assertEquals(
                roomModel.flagApontMecan,
                true
            )
            assertEquals(
                roomModel.flagApontPneu,
                true
            )
        }

    private val resultEquipRetrofit = """
        [
          {"idEquip":1,"nroEquip":10,"codClass":1,"descrClass":"Classe 1","codTurnEquip":1,"idCheckList":1,"typeFert":1,"hourmeter":100.0,"measurement":200.0,"type":1,"classify":1,"flagApontMecan":1,"flagApontPneu":1}
        ]
    """.trimIndent()

}
