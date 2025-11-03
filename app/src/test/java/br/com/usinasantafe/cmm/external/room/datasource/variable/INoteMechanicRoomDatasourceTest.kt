package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMechanicDao
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMechanicRoomModel
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
                    seq = 1,
                    dateHourFinish = null
                )
            )
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 2,
                    os = 123456,
                    seq = 1,
                    dateHourFinish = null
                )
            )
            noteMechanicDao.insert(
                NoteMechanicRoomModel(
                    idHeader = 1,
                    os = 123456,
                    seq = 1,
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
}