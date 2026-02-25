package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.stable.ServiceDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
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
class IServiceRoomDatasourceTest {

    private lateinit var serviceDao: ServiceDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IServiceRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        serviceDao = db.serviceDao()
        datasource = IServiceRoomDatasource(serviceDao)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = serviceDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ServiceRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    ServiceRoomModel(
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
                "IServiceRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_service` (`id`,`cod`,`descr`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_service.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = serviceDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ServiceRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    ServiceRoomModel(
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
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            serviceDao.insertAll(
                listOf(
                    ServiceRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                )
            )
            val qtdBefore = serviceDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = serviceDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}