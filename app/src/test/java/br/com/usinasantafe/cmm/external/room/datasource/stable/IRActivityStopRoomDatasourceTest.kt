package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
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
class IRActivityStopRoomDatasourceTest {

    private lateinit var rActivityStopDao: RActivityStopDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IRActivityStopRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        rActivityStopDao = db.rActivityStopDao()
        datasource = IRActivityStopRoomDatasource(rActivityStopDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = rActivityStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 10,
                        idStop = 100
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 10,
                        idStop = 100
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IRActivityStopRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = rActivityStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 10,
                        idStop = 100
                    ),
                    RActivityStopRoomModel(
                        idRActivityStop = 2,
                        idActivity = 20,
                        idStop = 200
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
            val list = rActivityStopDao.all()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idRActivityStop,
                1
            )
            assertEquals(
                entity1.idActivity,
                10
            )
            assertEquals(
                entity1.idStop,
                100
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idRActivityStop,
                2
            )
            assertEquals(
                entity2.idActivity,
                20
            )
            assertEquals(
                entity2.idStop,
                200
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            rActivityStopDao.insertAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 10,
                        idStop = 100
                    )
                )
            )
            val qtdBefore = rActivityStopDao.all().size
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
            val qtdAfter = rActivityStopDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdActivity - Check return list empty if not have IdActivity researched`() =
        runTest {
            val result = datasource.listByIdActivity(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!.size,
                0
            )
        }

    @Test
    fun `listByIdActivity - Check return list if have idActivity researched`() =
        runTest {
            rActivityStopDao.insertAll(
                listOf(
                    RActivityStopRoomModel(
                        idRActivityStop = 1,
                        idActivity = 10,
                        idStop = 100
                        ),
                    RActivityStopRoomModel(
                        idRActivityStop = 2,
                        idActivity = 20,
                        idStop = 200
                    )
                )
            )
            val result = datasource.listByIdActivity(10)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.idRActivityStop,
                1
            )
            assertEquals(
                entity.idActivity,
                10
            )
            assertEquals(
                entity.idStop,
                100
            )
        }
}