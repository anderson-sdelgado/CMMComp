package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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
        activityDao = db.atividadeDao()
        datasource = IActivityRoomDatasource(activityDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = activityDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
                    ),
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
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
            val qtdAfter = activityDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = activityDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "TEST",
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
            val qtdAfter = activityDao.listAll().size
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
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TESTE",
                    ),
                )
            )
            val qtdBefore = activityDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = activityDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdList - Check return list empty if not have data research`() =
        runTest {
            val result = datasource.listByIdList(listOf(1))
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
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "ATIVIDADE 1",
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "ATIVIDADE 2",
                    ),
                    ActivityRoomModel(
                        idActivity = 3,
                        codActivity = 30,
                        descrActivity = "ATIVIDADE 3",
                    ),
                    ActivityRoomModel(
                        idActivity = 4,
                        codActivity = 40,
                        descrActivity = "ATIVIDADE 3",
                    ),
                )
            )
            val qtdAll = activityDao.listAll().size
            assertEquals(
                qtdAll,
                4
            )
            val result = datasource.listByIdList(listOf(1, 2, 3))
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
                entity1.idActivity,
                1
            )
            assertEquals(
                entity1.codActivity,
                10
            )
            assertEquals(
                entity1.descrActivity,
                "ATIVIDADE 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idActivity,
                2
            )
            assertEquals(
                entity2.codActivity,
                20
            )
            assertEquals(
                entity2.descrActivity,
                "ATIVIDADE 2"
            )
            val entity3 = list[2]
            assertEquals(
                entity3.idActivity,
                3
            )
            assertEquals(
                entity3.codActivity,
                30
            )
            assertEquals(
                entity3.descrActivity,
                "ATIVIDADE 3"
            )
        }

}