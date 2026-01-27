package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.StopDao
import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
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
class IStopRoomDatasourceTest {

    private lateinit var stopDao: StopDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IStopRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        stopDao = db.stopDao()
        datasource = IStopRoomDatasource(stopDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = stopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "Parada 1"
                    ),
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "Parada 1"
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
            val qtdBefore = stopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "Parada 1"
                    ),
                    StopRoomModel(
                        idStop = 2,
                        codStop = 20,
                        descrStop = "Parada 2"
                    )
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
            val list = stopDao.all()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idStop,
                1
            )
            assertEquals(
                entity1.codStop,
                10
            )
            assertEquals(
                entity1.descrStop,
                "Parada 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idStop,
                2
            )
            assertEquals(
                entity2.codStop,
                20
            )
            assertEquals(
                entity2.descrStop,
                "Parada 2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            stopDao.insertAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "Parada 1"
                    )
                )
            )
            val qtdBefore = stopDao.all().size
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
            val qtdAfter = stopDao.all().size
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
            stopDao.insertAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "PARADA 1"
                    ),
                    StopRoomModel(
                        idStop = 2,
                        codStop = 20,
                        descrStop = "PARADA 2"
                    ),
                    StopRoomModel(
                        idStop = 3,
                        codStop = 30,
                        descrStop = "PARADA 3"
                    ),
                    StopRoomModel(
                        idStop = 4,
                        codStop = 40,
                        descrStop = "PARADA 4"
                    )
                )
            )
            val qtdAll = stopDao.all().size
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
                entity1.idStop,
                1
            )
            assertEquals(
                entity1.codStop,
                10
            )
            assertEquals(
                entity1.descrStop,
                "PARADA 1"
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idStop,
                2
            )
            assertEquals(
                entity2.codStop,
                20
            )
            assertEquals(
                entity2.descrStop,
                "PARADA 2"
            )
            val entity3 = list[2]
            assertEquals(
                entity3.idStop,
                3
            )
            assertEquals(
                entity3.codStop,
                30
            )
            assertEquals(
                entity3.descrStop,
                "PARADA 3"
            )
        }

    @Test
    fun `getById - Check return model`() =
        runTest {
            stopDao.insertAll(
                listOf(
                    StopRoomModel(
                        idStop = 1,
                        codStop = 10,
                        descrStop = "PARADA 1"
                    ),
                    StopRoomModel(
                        idStop = 2,
                        codStop = 20,
                        descrStop = "PARADA 2"
                    ),
                    StopRoomModel(
                        idStop = 3,
                        codStop = 30,
                        descrStop = "PARADA 3"
                    ),
                    StopRoomModel(
                        idStop = 4,
                        codStop = 40,
                        descrStop = "PARADA 4"
                    )
                )
            )
            val result = datasource.getById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.idStop,
                1
            )
            assertEquals(
                model.codStop,
                10
            )
            assertEquals(
                model.descrStop,
                "PARADA 1"
            )
        }

}