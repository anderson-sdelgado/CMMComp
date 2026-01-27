package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.lib.ECM
import br.com.usinasantafe.cmm.lib.FERTIGATION
import br.com.usinasantafe.cmm.lib.IMPLEMENT
import br.com.usinasantafe.cmm.lib.ITEM_NORMAL
import br.com.usinasantafe.cmm.lib.MECHANICAL
import br.com.usinasantafe.cmm.lib.PERFORMANCE
import br.com.usinasantafe.cmm.lib.REEL
import br.com.usinasantafe.cmm.lib.TIRE
import br.com.usinasantafe.cmm.lib.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.dataMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IItemMenuRoomDatasourceTest {

    private lateinit var itemMenuDao: ItemMenuDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemMenuRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemMenuDao = db.itemMenuDao()
        datasource = IItemMenuRoomDatasource(itemMenuDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = itemMenuDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemMenuRoomModel(
                        id = 1,
                        descr = "Item 1",
                        idType = 1,
                        pos = 1,
                        idFunction = 1,
                        idApp = 1
                    ),
                    ItemMenuRoomModel(
                        id = 1,
                        descr = "Item 1",
                        idType = 1,
                        pos = 2,
                        idFunction = 2,
                        idApp = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemMenuPMMRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_item_menu` (`id`,`descr`,`idType`,`pos`,`idFunction`,`idApp`) VALUES (?,?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_item_menu.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = itemMenuDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = itemMenuDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemMenuRoomModel(
                        id = 1,
                        descr = "Item 1",
                        idType = 1,
                        pos = 1,
                        idFunction = 1,
                        idApp = 1
                    ),
                    ItemMenuRoomModel(
                        id = 2,
                        descr = "Item 2",
                        idType = 1,
                        pos = 2,
                        idFunction = 2,
                        idApp = 1
                    ),
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val qtdAfter = itemMenuDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            itemMenuDao.insertAll(
                listOf(
                    ItemMenuRoomModel(
                        id = 1,
                        descr = "Item 1",
                        idType = 1,
                        pos = 1,
                        idFunction = 1,
                        idApp = 1
                    ),
                )
            )
            val qtdBefore = itemMenuDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = itemMenuDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByTypeList - Check return empty list if not have data`() =
        runTest {
            val result = datasource.listByTypeList()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtd = result.getOrNull()!!.size
            assertEquals(
                qtd,
                0
            )
        }

    @Test
    fun `listByTypeList - Check return correct list in field with idType list and idApp and basic - PMM`() =
        runTest {
            val gson = Gson()
            val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
            val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
            itemMenuDao.insertAll(
                itemMenuRoomModel
            )
            val result = datasource.listByTypeList(
                listOf(
                    1 to ITEM_NORMAL,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.descr,
                "TRABALHANDO"
            )
            assertEquals(
                entity1.idType,
                1
            )
            assertEquals(
                entity1.pos,
                1
            )
            assertEquals(
                entity1.idFunction,
                1
            )
            assertEquals(
                entity1.idApp,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.descr,
                "PARADO"
            )
            assertEquals(
                entity2.idType,
                1
            )
            assertEquals(
                entity2.pos,
                2
            )
            assertEquals(
                entity2.idFunction,
                2
            )
            assertEquals(
                entity2.idApp,
                1
            )
        }

    @Test
    fun `listByTypeList - Check return correct list in field with idType list and idApp and all - PMM`() =
        runTest {
            val gson = Gson()
            val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
            val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
            itemMenuDao.insertAll(
                itemMenuRoomModel
            )
            val result = datasource.listByTypeList(
                listOf(
                    1 to ITEM_NORMAL,
                    2 to PERFORMANCE,
                    3 to TRANSHIPMENT,
                    4 to IMPLEMENT,
                    5 to FERTIGATION,
                    6 to MECHANICAL,
                    7 to TIRE,
                    8 to REEL,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                11
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.descr,
                "TRABALHANDO"
            )
            assertEquals(
                entity1.idType,
                1
            )
            assertEquals(
                entity1.pos,
                1
            )
            assertEquals(
                entity1.idFunction,
                1
            )
            assertEquals(
                entity1.idApp,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.descr,
                "PARADO"
            )
            assertEquals(
                entity2.idType,
                1
            )
            assertEquals(
                entity2.pos,
                2
            )
            assertEquals(
                entity2.idFunction,
                2
            )
            assertEquals(
                entity2.idApp,
                1
            )
            val entity3 = list[2]
            assertEquals(
                entity3.id,
                3
            )
            assertEquals(
                entity3.descr,
                "RENDIMENTO"
            )
            assertEquals(
                entity3.idType,
                2
            )
            assertEquals(
                entity3.pos,
                3
            )
            assertEquals(
                entity3.idFunction,
                3
            )
            assertEquals(
                entity3.idApp,
                1
            )
            val entity4 = list[3]
            assertEquals(
                entity4.id,
                4
            )
            assertEquals(
                entity4.descr,
                "NOVO TRANSBORDO"
            )
            assertEquals(
                entity4.idType,
                3
            )
            assertEquals(
                entity4.pos,
                4
            )
            assertEquals(
                entity4.idFunction,
                4
            )
            assertEquals(
                entity4.idApp,
                1
            )
            val entity5 = list[4]
            assertEquals(
                entity5.id,
                5
            )
            assertEquals(
                entity5.descr,
                "TROCAR IMPLEMENTO"
            )
            assertEquals(
                entity5.idType,
                4
            )
            assertEquals(
                entity5.pos,
                5
            )
            assertEquals(
                entity5.idFunction,
                5
            )
            assertEquals(
                entity5.idApp,
                1
            )
            val entity6 = list[5]
            assertEquals(
                entity6.id,
                6
            )
            assertEquals(
                entity6.descr,
                "RECOLHIMENTO MANGUEIRA"
            )
            assertEquals(
                entity6.idType,
                5
            )
            assertEquals(
                entity6.pos,
                6
            )
            assertEquals(
                entity6.idFunction,
                6
            )
            assertEquals(
                entity6.idApp,
                1
            )
            val entity7 = list[6]
            assertEquals(
                entity7.id,
                7
            )
            assertEquals(
                entity7.descr,
                "APONTAR MANUTENÇÃO"
            )
            assertEquals(
                entity7.idType,
                6
            )
            assertEquals(
                entity7.pos,
                7
            )
            assertEquals(
                entity7.idFunction,
                7
            )
            assertEquals(
                entity7.idApp,
                1
            )
            val entity8 = list[7]
            assertEquals(
                entity8.id,
                8
            )
            assertEquals(
                entity8.descr,
                "FINALIZAR MANUTENÇÃO"
            )
            assertEquals(
                entity8.idType,
                6
            )
            assertEquals(
                entity8.pos,
                8
            )
            assertEquals(
                entity8.idFunction,
                8
            )
            assertEquals(
                entity8.idApp,
                1
            )
            val entity9 = list[8]
            assertEquals(
                entity9.id,
                9
            )
            assertEquals(
                entity9.descr,
                "CALIBRAGEM DE PNEU"
            )
            assertEquals(
                entity9.idType,
                7
            )
            assertEquals(
                entity9.pos,
                9
            )
            assertEquals(
                entity9.idFunction,
                9
            )
            assertEquals(
                entity9.idApp,
                1
            )
            val entity10 = list[9]
            assertEquals(
                entity10.id,
                10
            )
            assertEquals(
                entity10.descr,
                "TROCA DE PNEU"
            )
            assertEquals(
                entity10.idType,
                7
            )
            assertEquals(
                entity10.pos,
                10
            )
            assertEquals(
                entity10.idFunction,
                10
            )
            assertEquals(
                entity10.idApp,
                1
            )
            val entity11 = list[10]
            assertEquals(
                entity11.id,
                11
            )
            assertEquals(
                entity11.descr,
                "APONTAR CARRETEL"
            )
            assertEquals(
                entity11.idType,
                8
            )
            assertEquals(
                entity11.pos,
                11
            )
            assertEquals(
                entity11.idFunction,
                11
            )
            assertEquals(
                entity11.idApp,
                1
            )
        }

    @Test
    fun `listByTypeList - Check return correct list in idApp ECM`() =
        runTest {
            val gson = Gson()
            val itemTypeItemMenu = object : TypeToken<List<ItemMenuRoomModel>>() {}.type
            val itemMenuRoomModel = gson.fromJson<List<ItemMenuRoomModel>>(dataMenu, itemTypeItemMenu)
            itemMenuDao.insertAll(
                itemMenuRoomModel
            )
            val result = datasource.listByTypeList(
                app = 2 to ECM
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                15
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                16
            )
            assertEquals(
                entity1.descr,
                "SAIDA DA USINA"
            )
            assertEquals(
                entity1.idType,
                2
            )
            assertEquals(
                entity1.pos,
                1
            )
            assertEquals(
                entity1.idFunction,
                3
            )
            assertEquals(
                entity1.idApp,
                2
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                17
            )
            assertEquals(
                entity2.descr,
                "CHEGADA AO CAMPO"
            )
            assertEquals(
                entity2.idType,
                3
            )
            assertEquals(
                entity2.pos,
                2
            )
            assertEquals(
                entity2.idFunction,
                4
            )
            assertEquals(
                entity2.idApp,
                2
            )
            val entity3 = list[2]
            assertEquals(
                entity3.id,
                18
            )
            assertEquals(
                entity3.descr,
                "DESENGATE CAMPO"
            )
            assertEquals(
                entity3.idType,
                8
            )
            assertEquals(
                entity3.pos,
                3
            )
            assertEquals(
                entity3.idFunction,
                5
            )
            assertEquals(
                entity3.idApp,
                2
            )
            val entity4 = list[3]
            assertEquals(
                entity4.id,
                19
            )
            assertEquals(
                entity4.descr,
                "ENGATE CAMPO"
            )
            assertEquals(
                entity4.idType,
                9
            )
            assertEquals(
                entity4.pos,
                4
            )
            assertEquals(
                entity4.idFunction,
                6
            )
            assertEquals(
                entity4.idApp,
                2
            )
            val entity5 = list[4]
            assertEquals(
                entity5.id,
                20
            )
            assertEquals(
                entity5.descr,
                "AGUARDANDO CARREGAMENTO"
            )
            assertEquals(
                entity5.idType,
                1
            )
            assertEquals(
                entity5.pos,
                5
            )
            assertEquals(
                entity5.idFunction,
                7
            )
            assertEquals(
                entity5.idApp,
                2
            )
            val entity6 = list[5]
            assertEquals(
                entity6.id,
                21
            )
            assertEquals(
                entity6.descr,
                "CARREGAMENTO"
            )
            assertEquals(
                entity6.idType,
                1
            )
            assertEquals(
                entity6.pos,
                6
            )
            assertEquals(
                entity6.idFunction,
                8
            )
            assertEquals(
                entity6.idApp,
                2
            )
            val entity7 = list[6]
            assertEquals(
                entity7.id,
                22
            )
            assertEquals(
                entity7.descr,
                "CERTIFICADO"
            )
            assertEquals(
                entity7.idType,
                4
            )
            assertEquals(
                entity7.pos,
                7
            )
            assertEquals(
                entity7.idFunction,
                9
            )
            assertEquals(
                entity7.idApp,
                2
            )
            val entity8 = list[7]
            assertEquals(
                entity8.id,
                23
            )
            assertEquals(
                entity8.descr,
                "PESAGEM NA BALANCA"
            )
            assertEquals(
                entity8.idType,
                6
            )
            assertEquals(
                entity8.pos,
                9
            )
            assertEquals(
                entity8.idFunction,
                10
            )
            assertEquals(
                entity8.idApp,
                2
            )
            val entity9 = list[8]
            assertEquals(
                entity9.id,
                24
            )
            assertEquals(
                entity9.descr,
                "DESENGATE PATIO"
            )
            assertEquals(
                entity9.idType,
                19
            )
            assertEquals(
                entity9.pos,
                10
            )
            assertEquals(
                entity9.idFunction,
                11
            )
            assertEquals(
                entity9.idApp,
                2
            )
            val entity10 = list[9]
            assertEquals(
                entity10.id,
                25
            )
            assertEquals(
                entity10.descr,
                "ENGATE PATIO"
            )
            assertEquals(
                entity10.idType,
                20
            )
            assertEquals(
                entity10.pos,
                11
            )
            assertEquals(
                entity10.idFunction,
                12
            )
            assertEquals(
                entity10.idApp,
                2
            )
            val entity11 = list[10]
            assertEquals(
                entity11.id,
                26
            )
            assertEquals(
                entity11.descr,
                "PARADA NO PATIO"
            )
            assertEquals(
                entity11.idType,
                11
            )
            assertEquals(
                entity11.pos,
                12
            )
            assertEquals(
                entity11.idFunction,
                13
            )
            assertEquals(
                entity11.idApp,
                2
            )
            val entity12 = list[11]
            assertEquals(
                entity12.id,
                27
            )
            assertEquals(
                entity12.descr,
                "DESCARREGAMENTO HILO"
            )
            assertEquals(
                entity12.idType,
                1
            )
            assertEquals(
                entity12.pos,
                13
            )
            assertEquals(
                entity12.idFunction,
                14
            )
            assertEquals(
                entity12.idApp,
                2
            )
            val entity13 = list[12]
            assertEquals(
                entity13.id,
                28
            )
            assertEquals(
                entity13.descr,
                "FIM DE DESCARGA"
            )
            assertEquals(
                entity13.idType,
                12
            )
            assertEquals(
                entity13.pos,
                14
            )
            assertEquals(
                entity13.idFunction,
                15
            )
            assertEquals(
                entity13.idApp,
                2
            )
            val entity14 = list[13]
            assertEquals(
                entity14.id,
                29
            )
            assertEquals(
                entity14.descr,
                "AGUARDANDO ALOCACAO"
            )
            assertEquals(
                entity14.idType,
                1
            )
            assertEquals(
                entity14.pos,
                15
            )
            assertEquals(
                entity14.idFunction,
                16
            )
            assertEquals(
                entity14.idApp,
                2
            )
            val entity15 = list[14]
            assertEquals(
                entity15.id,
                30
            )
            assertEquals(
                entity15.descr,
                "RETORNO PRA USINA"
            )
            assertEquals(
                entity15.idType,
                5
            )
            assertEquals(
                entity15.pos,
                16
            )
            assertEquals(
                entity15.idFunction,
                17
            )
            assertEquals(
                entity15.idApp,
                2
            )
        }

}