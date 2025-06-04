package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.NoteMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class INoteMotoMecRoomDatasourceTest {

    private lateinit var noteMotoMecDao: NoteMotoMecDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: INoteMotoMecRoomDatasource


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        noteMotoMecDao = db.noteMotoMecDao()
        datasource = INoteMotoMecRoomDatasource(noteMotoMecDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = noteMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    dateHour = Date(1748359002)
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
            val listAfter = noteMotoMecDao.listAll()
            assertEquals(
                listAfter.size,
                1
            )
            val entity = listAfter[0]
            assertEquals(
                entity.nroOS,
                123456
            )
            assertEquals(
                entity.idActivity,
                1
            )
            assertEquals(
                entity.dateHour.time,
                1748359002
            )
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.idStop,
                null
            )
            assertEquals(
                entity.status,
                Status.CLOSE
            )
            assertEquals(
                entity.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                entity.idHeader,
                1
            )
        }


}