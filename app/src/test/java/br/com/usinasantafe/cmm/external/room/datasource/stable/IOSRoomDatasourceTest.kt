package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
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
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    ),
                    OSRoomModel(
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
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
            val qtdAfter = osDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 67890,
                        idRelease = 11,
                        idPropAgr = 21,
                        area = 100.0
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
            val list = osDao.all()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.id,
                1
            )
            assertEquals(
                entity1.nro,
                12345
            )
            assertEquals(
                entity1.idRelease,
                10
            )
            assertEquals(
                entity1.idPropAgr,
                20
            )
            assertEquals(
                entity1.area!!,
                50.5,
                0.0
            )
            val entity2 = list[1]
            assertEquals(
                entity2.id,
                2
            )
            assertEquals(
                entity2.nro,
                67890
            )
            assertEquals(
                entity2.idRelease,
                11
            )
            assertEquals(
                entity2.idPropAgr,
                21
            )
            assertEquals(
                entity2.area!!,
                100.0,
                0.0
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    )
                )
            )
            val qtdBefore = osDao.all().size
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
            val qtdAfter = osDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `checkNro - Check return false if not have nroOS in database`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    )
                )
            )
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.hasByNro(234567)
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
    fun `checkNro - Check return true if have nroOS in database`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 123456,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    )
                )
            )
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.hasByNro(123456)
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
                        id = 1,
                        nro = 12345,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    )
                )
            )
            val qtdBefore = osDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.add(
                OSRoomModel(
                    id = 2,
                    nro = 67890,
                    idRelease = 11,
                    idPropAgr = 21,
                    area = 100.0
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
            val list = osDao.all()
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
                model1.nro,
                12345
            )
            assertEquals(
                model1.idRelease,
                10
            )
            assertEquals(
                model1.idPropAgr,
                20
            )
            assertEquals(
                model1.area!!,
                50.5,
                0.0
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.nro,
                67890
            )
            assertEquals(
                model2.idRelease,
                11
            )
            assertEquals(
                model2.idPropAgr,
                21
            )
            assertEquals(
                model2.area!!,
                100.0,
                0.0
            )
        }

    @Test
    fun `getByNro - Check return list empty if not have data research`() =
        runTest {
            val result = datasource.getByNro(12345)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `getByNro - Check return list if have data research`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 123456,
                        idRelease = 10,
                        idPropAgr = 20,
                        area = 50.5
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 456789,
                        idRelease = 11,
                        idPropAgr = 21,
                        area = 100.0
                    )
                )
            )
            val result = datasource.getByNro(123456)
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
                model.nro,
                123456
            )
            assertEquals(
                model.idRelease,
                10
            )
            assertEquals(
                model.idPropAgr,
                20
            )
            assertEquals(
                model.area!!,
                50.5,
                0.0
            )
        }

    @Test
    fun `hasByNroAndIdRelease - Check return false if not have data research`() =
        runTest {
            val result = datasource.hasByNroAndIdRelease(
                nroOS = 12345,
                idRelease = 10
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }

    @Test
    fun `hasByNroAndIdRelease - Check return false if nro is different`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 10,
                    ),
                )
            )
            val result = datasource.hasByNroAndIdRelease(
                nroOS = 123456,
                idRelease = 10
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }

    @Test
    fun `hasByNroAndIdRelease - Check return false if idRelease is different`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 10,
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 123456,
                        idRelease = 20,
                    ),
                )
            )
            val result = datasource.hasByNroAndIdRelease(
                nroOS = 123456,
                idRelease = 10
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                false
            )
        }

    @Test
    fun `hasByNroAndIdRelease - Check return true if have data research`() =
        runTest {
            osDao.insertAll(
                listOf(
                    OSRoomModel(
                        id = 1,
                        nro = 456789,
                        idRelease = 10,
                    ),
                    OSRoomModel(
                        id = 2,
                        nro = 123456,
                        idRelease = 20,
                    ),
                    OSRoomModel(
                        id = 3,
                        nro = 123456,
                        idRelease = 10,
                    ),
                )
            )
            val result = datasource.hasByNroAndIdRelease(
                nroOS = 123456,
                idRelease = 10
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                true
            )
        }

}