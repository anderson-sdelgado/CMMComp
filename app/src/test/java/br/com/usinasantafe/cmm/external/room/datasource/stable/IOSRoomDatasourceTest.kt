package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.OSDao
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
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
class IOSRoomDatasourceTest {

    private lateinit var osDao: OSDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IOSRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        osDao = db.osDao()
        datasource = IOSRoomDatasource(osDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = osDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 67890,
                        idLibOS = 11,
                        idPropAgr = 21,
                        areaOS = 100.0,
                        idEquip = 31
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
            val list = osDao.listAll()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idOS,
                1
            )
            assertEquals(
                entity1.nroOS,
                12345
            )
            assertEquals(
                entity1.idLibOS,
                10
            )
            assertEquals(
                entity1.idPropAgr,
                20
            )
            assertEquals(
                entity1.areaOS,
                50.5,
                0.0
            )
            assertEquals(
                entity1.idEquip,
                30
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idOS,
                2
            )
            assertEquals(
                entity2.nroOS,
                67890
            )
            assertEquals(
                entity2.idLibOS,
                11
            )
            assertEquals(
                entity2.idPropAgr,
                21
            )
            assertEquals(
                entity2.areaOS,
                100.0,
                0.0
            )
            assertEquals(
                entity2.idEquip,
                31
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
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
            val qtdAfter = osDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `checkNroOS - Check return false if not have nroOS in database`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.checkNroOS(234567)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `checkNroOS - Check return true if have nroOS in database`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.checkNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `add - Check return correct if function execute successfully`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.add(
                OSRoomModel(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idPropAgr = 21,
                    areaOS = 100.0,
                    idEquip = 31
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
            val list = osDao.listAll()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idOS,
                1
            )
            assertEquals(
                model1.nroOS,
                12345
            )
            assertEquals(
                model1.idLibOS,
                10
            )
            assertEquals(
                model1.idPropAgr,
                20
            )
            assertEquals(
                model1.areaOS,
                50.5,
                0.0
            )
            assertEquals(
                model1.idEquip,
                30
            )
            val model2 = list[1]
            assertEquals(
                model2.idOS,
                2
            )
            assertEquals(
                model2.nroOS,
                67890
            )
            assertEquals(
                model2.idLibOS,
                11
            )
            assertEquals(
                model2.idPropAgr,
                21
            )
            assertEquals(
                model2.areaOS,
                100.0,
                0.0
            )
            assertEquals(
                model2.idEquip,
                31
            )
        }

    @Test
    fun `getListByNroOS - Check return list empty if not have data research`() =
        runTest {
            val result = datasource.listByNroOS(12345)
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
    fun `getListByNroOS - Check return list if have data research`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 123456,
                        idLibOS = 10,
                        idPropAgr = 20,
                        areaOS = 50.5,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 456789,
                        idLibOS = 11,
                        idPropAgr = 21,
                        areaOS = 100.0,
                        idEquip = 31
                    )
                )
            )
            val result = datasource.listByNroOS(123456)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idOS,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.idLibOS,
                10
            )
            assertEquals(
                model.idPropAgr,
                20
            )
            assertEquals(
                model.areaOS,
                50.5,
                0.0
            )
            assertEquals(
                model.idEquip,
                30
            )
        }
}