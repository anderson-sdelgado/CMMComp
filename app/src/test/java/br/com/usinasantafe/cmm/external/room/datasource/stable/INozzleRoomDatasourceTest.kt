package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.stable.NozzleDao
import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import br.com.usinasantafe.cmm.lib.DatabaseRoom
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
class INozzleRoomDatasourceTest {

    private lateinit var nozzleDao: NozzleDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: INozzleRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        nozzleDao = db.nozzleDao()
        datasource = INozzleRoomDatasource(nozzleDao)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = nozzleDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INozzleRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_nozzle` (`id`,`cod`,`descr`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_nozzle.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = nozzleDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = nozzleDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    NozzleRoomModel(
                        id = 2,
                        cod = 20,
                        descr = "TEST",
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
            val qtdAfter = nozzleDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            nozzleDao.insertAll(
                listOf(
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                )
            )
            val qtdBefore = nozzleDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = nozzleDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listAll - Check return empty list if table is empty`() =
        runTest {
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun `listAll - Check return list ordered if table have data`() =
        runTest {
            nozzleDao.insertAll(
                listOf(
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                    NozzleRoomModel(
                        id = 2,
                        cod = 5,
                        descr = "TESTE 2",
                    ),
                    NozzleRoomModel(
                        id = 3,
                        cod = 15,
                        descr = "TESTE 3",
                    ),
                )
            )
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    NozzleRoomModel(
                        id = 2,
                        cod = 5,
                        descr = "TESTE 2",
                    ),
                    NozzleRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                    NozzleRoomModel(
                        id = 3,
                        cod = 15,
                        descr = "TESTE 3",
                    ),
                )
            )
        }



}