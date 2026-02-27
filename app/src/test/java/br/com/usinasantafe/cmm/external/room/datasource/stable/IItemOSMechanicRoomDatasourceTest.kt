package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMechanicDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IItemOSMechanicRoomDatasourceTest {

    private lateinit var itemOSMechanicDao: ItemOSMechanicDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemOSMechanicRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemOSMechanicDao = db.itemOSMechanicDao()
        datasource = IItemOSMechanicRoomDatasource(itemOSMechanicDao)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = itemOSMechanicDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        nroOS = 1,
                        seqItem = 1,
                        idServ = 1,
                        idComp = 1
                    ),
                    ItemOSMechanicRoomModel(
                        id = 1,
                        nroOS = 1,
                        seqItem = 1,
                        idServ = 1,
                        idComp = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IItemOSMechanicRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_item_os_mechanic` (`id`,`nroOS`,`seqItem`,`idServ`,`idComp`) VALUES (?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_item_os_mechanic.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = itemOSMechanicDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = itemOSMechanicDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        nroOS = 1,
                        seqItem = 1,
                        idServ = 1,
                        idComp = 1
                    ),
                    ItemOSMechanicRoomModel(
                        id = 2,
                        nroOS = 2,
                        seqItem = 2,
                        idServ = 2,
                        idComp = 2
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
            val list = itemOSMechanicDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.nroOS,
                1
            )
            assertEquals(
                model1.seqItem,
                1
            )
            assertEquals(
                model1.idServ,
                1
            )
            assertEquals(
                model1.idComp,
                1
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.nroOS,
                2
            )
            assertEquals(
                model2.seqItem,
                2
            )
            assertEquals(
                model2.idServ,
                2
            )
            assertEquals(
                model2.idComp,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            itemOSMechanicDao.insertAll(
                listOf(
                    ItemOSMechanicRoomModel(
                        id = 1,
                        nroOS = 1,
                        seqItem = 1,
                        idServ = 1,
                        idComp = 1
                    )
                )
            )
            val qtdBefore = itemOSMechanicDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val qtdAfter = itemOSMechanicDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}