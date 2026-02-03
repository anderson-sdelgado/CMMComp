package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.variable.PerformanceDao
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.lib.DatabaseRoom
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
class IPerformanceRoomDatasourceTest {

    private lateinit var performanceDao: PerformanceDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IPerformanceRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        performanceDao = db.performanceDao()
        datasource = IPerformanceRoomDatasource(performanceDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `insert - Check insert data if table is empty`() =
        runTest {
            val result = datasource.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
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
            val list = performanceDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.value,
                null
            )
        }

    @Test
    fun `insert - Check not insert data if data is repeated`() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = datasource.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
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
            val list = performanceDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.value,
                null
            )
        }

    @Test
    fun `insert - Check insert data if data is different`() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = datasource.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 12345,
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
            val list = performanceDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idHeader,
                1
            )
            assertEquals(
                model1.nroOS,
                123456
            )
            assertEquals(
                model1.value,
                null
            )
            val model2 = list[1]
            assertEquals(
                model2.idHeader,
                1
            )
            assertEquals(
                model2.nroOS,
                12345
            )
            assertEquals(
                model2.value,
                null
            )
        }

    @Test
    fun `listByIdHeader - Check return empty list if table is empty`() =
        runTest {
            val result = datasource.listByIdHeader(1)
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
    fun `listByIdHeader - Check return empty list if table not have data fielded`() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = datasource.listByIdHeader(2)
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
    fun `listByIdHeader - Check return list if table have data fielded`() =
        runTest {
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            performanceDao.insert(
                PerformanceRoomModel(
                    idHeader = 2,
                    nroOS = 123456,
                    value = 10.0
                )
            )
            val result = datasource.listByIdHeader(2)
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
                model.idHeader,
                2
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.value,
                10.0
            )
        }


}