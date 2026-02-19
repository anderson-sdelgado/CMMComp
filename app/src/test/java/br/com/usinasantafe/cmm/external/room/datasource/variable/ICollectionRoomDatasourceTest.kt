package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.variable.CollectionDao
import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
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
class ICollectionRoomDatasourceTest {

    private lateinit var collectionDao: CollectionDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: ICollectionRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        collectionDao = db.collectionDao()
        datasource = ICollectionRoomDatasource(collectionDao)
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `insert - Check insert data if table is empty`() =
        runTest {
            val result = datasource.insert(
                CollectionRoomModel(
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
            val list = collectionDao.all()
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
            collectionDao.insert(
                CollectionRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = datasource.insert(
                CollectionRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val list = collectionDao.all()
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
            collectionDao.insert(
                CollectionRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                )
            )
            val result = datasource.insert(
                CollectionRoomModel(
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
            val list = collectionDao.all()
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

}