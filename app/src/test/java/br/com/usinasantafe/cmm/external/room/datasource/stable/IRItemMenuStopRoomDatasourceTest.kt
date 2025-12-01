package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.RItemMenuStopDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
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
class IRItemMenuStopRoomDatasourceTest {

    private lateinit var rItemMenuStopDao: RItemMenuStopDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IRItemMenuStopRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        rItemMenuStopDao = db.rItemMenuStopDao()
        datasource = IRItemMenuStopRoomDatasource(rItemMenuStopDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = rItemMenuStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    RItemMenuStopRoomModel(
                        id = 1,
                        idFunction = 1,
                        idApp = 1,
                        idStop = 1
                    ),
                    RItemMenuStopRoomModel(
                        id = 1,
                        idFunction = 1,
                        idApp = 1,
                        idStop = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRItemMenuStopRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_r_item_menu_stop` (`id`,`idFunction`,`idApp`,`idStop`) VALUES (?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_r_item_menu_stop.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = rItemMenuStopDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = rItemMenuStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    RItemMenuStopRoomModel(
                        id = 1,
                        idFunction = 1,
                        idApp = 1,
                        idStop = 1
                    ),
                    RItemMenuStopRoomModel(
                        id = 2,
                        idFunction = 2,
                        idApp = 2,
                        idStop = 2
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
            val list = rItemMenuStopDao.all()
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
                model1.idFunction,
                1
            )
            assertEquals(
                model1.idApp,
                1
            )
            assertEquals(
                model1.idStop,
                1
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.idFunction,
                2
            )
            assertEquals(
                model2.idApp,
                2
            )
            assertEquals(
                model2.idStop,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            rItemMenuStopDao.insertAll(
                listOf(
                    RItemMenuStopRoomModel(
                        id = 1,
                        idFunction = 1,
                        idApp = 1,
                        idStop = 1
                    )
                )
            )
            val qtdBefore = rItemMenuStopDao.all().size
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
                true
            )
            val qtdAfter = rItemMenuStopDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}