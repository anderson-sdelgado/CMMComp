package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
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
class IROSActivityRoomDatasourceTest {

    private lateinit var rOSActivityDao: ROSActivityDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IROSActivityRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        rOSActivityDao = db.rOSActivityDao()
        datasource = IROSActivityRoomDatasource(rOSActivityDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = rOSActivityDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 10
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 10
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IROSActivityRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = rOSActivityDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 10
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 2,
                        idOS = 2,
                        idActivity = 20
                    )
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
            val list = rOSActivityDao.listAll()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idROSActivity,
                1
            )
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.idActivity,
                10
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idROSActivity,
                2
            )
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.idActivity,
                20
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            rOSActivityDao.insertAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 10
                    )
                )
            )
            val qtdBefore = rOSActivityDao.listAll().size
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
            val qtdAfter = rOSActivityDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `getListByIdOS - Check return list empty if not have data research`() =
        runTest {
            val result = datasource.listByIdOS(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<ROSActivityRoomModel>()
            )
        }

    @Test
    fun `getListByIdOS - Check return list if have data research`() =
        runTest {
            rOSActivityDao.insertAll(
                listOf(
                    ROSActivityRoomModel(
                        idROSActivity = 1,
                        idOS = 1,
                        idActivity = 10
                    ),
                    ROSActivityRoomModel(
                        idROSActivity = 2,
                        idOS = 2,
                        idActivity = 20
                    )
                )
            )
            val qtdAll = rOSActivityDao.listAll().size
            assertEquals(
                qtdAll,
                2
            )
            val result = datasource.listByIdOS(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idROSActivity,
                1
            )
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.idActivity,
                10
            )
        }
}