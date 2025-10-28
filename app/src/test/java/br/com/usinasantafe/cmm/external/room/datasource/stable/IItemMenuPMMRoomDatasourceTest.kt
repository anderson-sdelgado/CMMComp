package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuPMMDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
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
class IItemMenuPMMRoomDatasourceTest {

    private lateinit var itemMenuPMMDao: ItemMenuPMMDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemMenuPMMRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemMenuPMMDao = db.itemMenuPMMDao()
        datasource = IItemMenuPMMRoomDatasource(itemMenuPMMDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = itemMenuPMMDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemMenuPMMRoomModel(
                        id = 1,
                        title = "Item 1",
                        type = TypeItemMenu.ITEM_NORMAL
                    ),
                    ItemMenuPMMRoomModel(
                        id = 1,
                        title = "Item 1",
                        type = TypeItemMenu.ITEM_NORMAL
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
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_item_menu_pmm` (`id`,`title`,`type`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_item_menu_pmm.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = itemMenuPMMDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = itemMenuPMMDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemMenuPMMRoomModel(
                        id = 1,
                        title = "Item 1",
                        type = TypeItemMenu.ITEM_NORMAL
                    ),
                    ItemMenuPMMRoomModel(
                        id = 2,
                        title = "Item 2",
                        type = TypeItemMenu.ITEM_NORMAL
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
            val qtdAfter = itemMenuPMMDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            itemMenuPMMDao.insertAll(
                listOf(
                    ItemMenuPMMRoomModel(
                        id = 1,
                        title = "Item 1",
                        type = TypeItemMenu.ITEM_NORMAL
                    ),
                )
            )
            val qtdBefore = itemMenuPMMDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = itemMenuPMMDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}