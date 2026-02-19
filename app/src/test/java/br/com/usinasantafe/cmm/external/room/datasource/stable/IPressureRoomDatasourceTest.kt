package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.stable.PressureDao
import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
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
class IPressureRoomDatasourceTest {

    private lateinit var pressureDao: PressureDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IPressureRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        pressureDao = db.pressureDao()
        datasource = IPressureRoomDatasource(pressureDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = pressureDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPressureRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_pressure` (`id`,`idNozzle`,`value`,`speed`) VALUES (?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_pressure.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = pressureDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = pressureDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
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
            val qtdAfter = pressureDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                )
            )
            val qtdBefore = pressureDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = pressureDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdNozzle - Check return list empty if not have data in table`() =
        runTest {
            val result = datasource.listByIdNozzle(1)
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
    fun `listByIdNozzle - Check return list empty if not have data fielded in table`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    )
                )
            )
            val result = datasource.listByIdNozzle(1)
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
    fun `listByIdNozzle - Check return list if have data fielded in table`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    )
                )
            )
            val result = datasource.listByIdNozzle(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    )
                )
            )
        }

    @Test
    fun `listByIdNozzleAndValuePressure - Check return list empty if not have data in table`() =
        runTest {
            val result = datasource.listByIdNozzleAndValuePressure(1, 10.0)
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
    fun `listByIdNozzleAndValuePressure - Check return list empty if have idNozzle fielded but not value in table`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    ),
                )
            )
            val result = datasource.listByIdNozzleAndValuePressure(1, 20.0)
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
    fun `listByIdNozzleAndValuePressure - Check return list empty if have value fielded but not idNozzle in table`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                )
            )
            val result = datasource.listByIdNozzleAndValuePressure(1, 20.0)
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
    fun `listByIdNozzleAndValuePressure - Check return list if have value fielded and idNozzle fielded in table`() =
        runTest {
            pressureDao.insertAll(
                listOf(
                    PressureRoomModel(
                        id = 1,
                        idNozzle = 2,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 2,
                        idNozzle = 1,
                        value = 10.0,
                        speed = 1
                    ),
                    PressureRoomModel(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    ),
                    PressureRoomModel(
                        id = 4,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 20
                    )
                )
            )
            val result = datasource.listByIdNozzleAndValuePressure(1, 20.0)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    PressureRoomModel(
                        id = 3,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 15
                    ),
                    PressureRoomModel(
                        id = 4,
                        idNozzle = 1,
                        value = 20.0,
                        speed = 20
                    )
                )
            )
        }

}