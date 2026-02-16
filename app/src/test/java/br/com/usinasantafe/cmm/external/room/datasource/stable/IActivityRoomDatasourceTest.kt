package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IActivityRoomDatasourceTest {

    private lateinit var activityDao: ActivityDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IActivityRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        activityDao = db.activityDao()
        datasource = IActivityRoomDatasource(activityDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = activityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    ActivityRoomModel(
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
                "IActivityRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = activityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = activityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TEST",
                    ),
                    ActivityRoomModel(
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
            val qtdAfter = activityDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "TESTE",
                    ),
                )
            )
            val qtdBefore = activityDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = activityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdList - Check return list empty if not have data research`() =
        runTest {
            val result = datasource.listByIdList(
                listOf(1)
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun `listByIdList - Check return list if have data research`() =
        runTest {
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "ATIVIDADE 1",
                    ),
                    ActivityRoomModel(
                        id = 2,
                        cod = 20,
                        descr = "ATIVIDADE 2",
                    ),
                    ActivityRoomModel(
                        id = 3,
                        cod = 30,
                        descr = "ATIVIDADE 3",
                    ),
                    ActivityRoomModel(
                        id = 4,
                        cod = 40,
                        descr = "ATIVIDADE 3",
                    ),
                )
            )
            val qtdAll = activityDao.all().size
            assertEquals(
                qtdAll,
                4
            )
            val result = datasource.listByIdList(
                listOf(1, 2, 3)
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                3
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.cod,
                10
            )
            assertEquals(
                entity1.descr,
                "ATIVIDADE 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.cod,
                20
            )
            assertEquals(
                entity2.descr,
                "ATIVIDADE 2"
            )
            val entity3 = list[2]
            assertEquals(
                entity3.id,
                3
            )
            assertEquals(
                entity3.cod,
                30
            )
            assertEquals(
                entity3.descr,
                "ATIVIDADE 3"
            )
        }

    @Test
    fun `getById - Check return model`() =
        runTest {
            activityDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        id = 1,
                        cod = 10,
                        descr = "ATIVIDADE 1",
                    ),
                    ActivityRoomModel(
                        id = 2,
                        cod = 20,
                        descr = "ATIVIDADE 2",
                    ),
                    ActivityRoomModel(
                        id = 3,
                        cod = 30,
                        descr = "ATIVIDADE 3",
                    ),
                    ActivityRoomModel(
                        id = 4,
                        cod = 40,
                        descr = "ATIVIDADE 3",
                    ),
                )
            )
            val result = datasource.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.cod,
                10
            )
            assertEquals(
                model.descr,
                "ATIVIDADE 1"
            )
        }
}