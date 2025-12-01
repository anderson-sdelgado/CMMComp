package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
import br.com.usinasantafe.cmm.utils.Status
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
class INoteMechanicRoomDatasourceTest {

    private lateinit var noteMechanicDao: NoteMechanicDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: INoteMechanicRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        noteMechanicDao = db.noteMechanicDao()
        datasource = INoteMechanicRoomDatasource(noteMechanicDao)
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
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 1,
                    os = 123456,
                    item = 1,
                    dateHourFinish = null
                )
            )
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 2,
                    os = 123456,
                    item = 1,
                    dateHourFinish = null
                )
            )
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 1,
                    os = 123456,
                    item = 1,
                    dateHourFinish = Date()
                )
            )
            val list = noteMechanicDao.all()
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
    fun `setFinishNote - Check alter date`() =
        runTest {
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 1,
                    os = 123456,
                    item = 1,
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
                true
            )
            val list = noteMechanicDao.all()
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
                model.os,
                123456
            )
            assertEquals(
                model.item,
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
}