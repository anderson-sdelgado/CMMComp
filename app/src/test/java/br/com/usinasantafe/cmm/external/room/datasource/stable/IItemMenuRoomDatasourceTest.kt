package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu
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
        itemMenuDao = db.itemMenuPMMDao()
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
                true
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

//    @Test
//    fun `listByTypeList - Check return empty list if not have data`() =
//        runTest {
//            val result = datasource.listByTypeList(
//                listOf(
//                )
//            )
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            val qtd = result.getOrNull()!!.size
//            assertEquals(
//                qtd,
//                0
//            )
//        }
//
//    @Test
//    fun `listByTypeList - Check return correct list if have data fielded`() =
//        runTest {
//            itemMenuDao.insertAll(
//                listOf(
//                    ItemMenuRoomModel(
//                        id = 1,
//                        descr = "Item 1",
//                        idType = 1,
//                        pos = 1,
//                        idFunction = 1,
//                        idApp = 1
//                    ),
//                )
//            )
//            itemMenuDao.insertAll(
//                listOf(
//                    ItemMenuRoomModel(
//                        id = 2,
//                        descr = "Item 2",
//                        idType = 1,
//                        pos = 2,
//                        idFunction = 2,
//                        idApp = 1
//                    ),
//                )
//            )
//            itemMenuDao.insertAll(
//                listOf(
//                    ItemMenuRoomModel(
//                        id = 3,
//                        descr = "Item 3",
//                        idType = 1,
//                        pos = 3,
//                        idFunction = 3,
//                        idApp = 1
//                    ),
//                )
//            )
//            itemMenuDao.insertAll(
//                listOf(
//                    ItemMenuRoomModel(
//                        id = 4,
//                        descr = "Item 4",
//                        idType = 1,
//                        pos = 4,
//                        idFunction = 4,
//                        idApp = 1
//                    ),
//                )
//            )
//
//            val result = datasource.listByTypeList(
//                listOf(
//                    FunctionItemMenu.ITEM_NORMAL,
//                    FunctionItemMenu.FINISH_HEADER
//                )
//            )
//            assertEquals(
//                result.isSuccess,
//                true
//            )
//            val list = result.getOrNull()!!
//            assertEquals(
//                list.size,
//                3
//            )
//            val entity1 = list[0]
//            assertEquals(
//                entity1.id,
//                1
//            )
//            assertEquals(
//                entity1.descr,
//                "Item 1"
//            )
//            assertEquals(
//                entity1.idType,
//                1
//            )
//            assertEquals(
//                entity1.pos,
//                1
//            )
//            assertEquals(
//                entity1.idFunction,
//                1
//            )
//            assertEquals(
//                entity1.idApp,
//                1
//            )
//            val entity2 = list[1]
//            assertEquals(
//                entity2.id,
//                2
//            )
//            assertEquals(
//                entity2.descr,
//                "Item 2"
//            )
//            assertEquals(
//                entity2.idType,
//                1
//            )
//            assertEquals(
//                entity2.pos,
//                2
//            )
//            assertEquals(
//                entity2.idFunction,
//                2
//            )
//            assertEquals(
//                entity2.idApp,
//                1
//            )
//            val entity3 = list[2]
//            assertEquals(
//                entity3.id,
//                3
//            )
//            assertEquals(
//                entity3.descr,
//                "Item 3"
//            )
//            assertEquals(
//                entity3.idType,
//                1
//            )
//            assertEquals(
//                entity3.pos,
//                3
//            )
//            assertEquals(
//                entity3.idFunction,
//                3
//            )
//            assertEquals(
//                entity3.idApp,
//                1
//            )
//            val entity4 = list[3]
//            assertEquals(
//                entity4.id,
//                4
//            )
//            assertEquals(
//                entity4.descr,
//                "Item 4"
//            )
//            assertEquals(
//                entity4.idType,
//                1
//            )
//            assertEquals(
//                entity4.pos,
//                4
//            )
//            assertEquals(
//                entity4.idFunction,
//                4
//            )
//            assertEquals(
//                entity4.idApp,
//                1
//            )
//        }

}