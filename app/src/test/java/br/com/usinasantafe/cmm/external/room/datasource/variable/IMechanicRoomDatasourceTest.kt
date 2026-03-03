package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.MechanicDao
import br.com.usinasantafe.cmm.infra.models.room.variable.MechanicRoomModel
import br.com.usinasantafe.cmm.lib.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.intArrayOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IMechanicRoomDatasourceTest {

    private lateinit var mechanicDao: MechanicDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IMechanicRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        mechanicDao = db.noteMechanicDao()
        datasource = IMechanicRoomDatasource(mechanicDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `checkNoteOpenByIdHeader - Check return false if not have data in table room`() =
        runTest {
            val result = datasource.checkNoteOpenByIdHeader(1)
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
    fun `checkNoteOpenByIdHeader - Check return true if have data fielded in table room`() =
        runTest {
            mechanicDao.insert(
                MechanicRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    seqItem = 1,
                    dateHourFinish = null
                )
            )
            mechanicDao.insert(
                MechanicRoomModel(
                    idHeader = 2,
                    nroOS = 123456,
                    seqItem = 1,
                    dateHourFinish = null
                )
            )
            mechanicDao.insert(
                MechanicRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    seqItem = 1,
                    dateHourFinish = Date()
                )
            )
            val list = mechanicDao.all()
            assertEquals(
                list.size,
                3
            )
            val result = datasource.checkNoteOpenByIdHeader(1)
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
    fun `setFinishNote - Check return failure if not have data in table room`() =
        runTest {
            val result = datasource.setFinishNote()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "INoteMechanicRoomDatasource.setFinishNote"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel.setDateHourFinish(java.util.Date)\" because \"model\" is null"
            )
        }

    @Test
    fun `setFinishNote - Check alter date finish`() =
        runTest {
            mechanicDao.insert(
                MechanicRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    seqItem = 1,
                    dateHourFinish = null
                )
            )
            val result = datasource.setFinishNote()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val list = mechanicDao.all()
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
                model.seqItem,
                1
            )
            assertNotNull(
                model.dateHourFinish
            )
            assertEquals(
                model.status,
                Status.FINISH
            )
        }

    @Test
    fun `save - Check insert data`() =
        runTest {
            val listBefore = mechanicDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                MechanicRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    seqItem = 2
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val listAfter = mechanicDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.seqItem,
                2
            )
        }
}