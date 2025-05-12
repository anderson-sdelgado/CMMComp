package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.OSDao
import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
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
class IOSRoomDatasourceTest {

    private lateinit var osDao: OSDao
    private lateinit var db: DatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        osDao = db.osDao()
    }

    @After
    fun after() {
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
            val datasource = IOSRoomDatasource(osDao)
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
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
            val datasource = IOSRoomDatasource(osDao)
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        idOS = 1,
                        nroOS = 12345,
                        idLibOS = 10,
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    ),
                    OSRoomModel(
                        idOS = 2,
                        nroOS = 67890,
                        idLibOS = 11,
                        idProprAgr = 21,
                        areaProgrOS = 100.0,
                        tipoOS = 2,
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
                entity1.idProprAgr,
                20
            )
            assertEquals(
                entity1.areaProgrOS,
                50.5,
                0.0
            )
            assertEquals(
                entity1.tipoOS,
                1
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
                entity2.idProprAgr,
                21
            )
            assertEquals(
                entity2.areaProgrOS,
                100.0,
                0.0
            )
            assertEquals(
                entity2.tipoOS,
                2
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
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IOSRoomDatasource(osDao)
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
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IOSRoomDatasource(osDao)
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
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IOSRoomDatasource(osDao)
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
                        idProprAgr = 20,
                        areaProgrOS = 50.5,
                        tipoOS = 1,
                        idEquip = 30
                    )
                )
            )
            val qtdBefore = osDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IOSRoomDatasource(osDao)
            val result = datasource.add(
                OSRoomModel(
                    idOS = 2,
                    nroOS = 67890,
                    idLibOS = 11,
                    idProprAgr = 21,
                    areaProgrOS = 100.0,
                    tipoOS = 2,
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
                model1.idProprAgr,
                20
            )
            assertEquals(
                model1.areaProgrOS,
                50.5,
                0.0
            )
            assertEquals(
                model1.tipoOS,
                1
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
                model2.idProprAgr,
                21
            )
            assertEquals(
                model2.areaProgrOS,
                100.0,
                0.0
            )
            assertEquals(
                model2.tipoOS,
                2
            )
            assertEquals(
                model2.idEquip,
                31
            )
        }
}