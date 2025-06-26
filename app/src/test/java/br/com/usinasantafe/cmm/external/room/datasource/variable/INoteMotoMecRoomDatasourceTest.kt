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
                    dateHour = Date(1748359002),
                    statusCon = true
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

    @Test
    fun `checkHasByIdHeader - Check return false if not has data`() =
        runTest {
            val result = datasource.checkHasByIdHeader(1)
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
    fun `checkHasByIdHeader - Check return true if has data`() =
        runTest {
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    dateHour = Date(1748359002),
                    statusCon = true
                )
            )
            val result = datasource.checkHasByIdHeader(1)
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
        fun `listByIdHeaderAndSend - Check return emptyList if not has data`() =
            runTest {
                val result = datasource.listByIdHeaderAndSend(1)
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
    fun `listByIdHeaderAndSend - Check return list if has data to send`() =
        runTest {
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    dateHour = Date(1748359002),
                    statusCon = true,
                    statusSend = StatusSend.SENT
                ),
            )
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    idStop = 1,
                    dateHour = Date(1748359002),
                    statusCon = true,
                    statusSend = StatusSend.SEND
                ),
            )
            val result = datasource.listByIdHeaderAndSend(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
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
                2
                )
            assertEquals(
                entity.idStop,
                1
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

    @Test
    fun `setSentNote - Check alter data if execute success`() =
        runTest {
            noteMotoMecDao.insert(
                NoteMotoMecRoomModel(
                    idHeader = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    dateHour = Date(1748359002),
                    statusCon = true,
                    statusSend = StatusSend.SEND
                ),
            )
            val listBefore = noteMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val entityBefore = listBefore[0]
            assertEquals(
                entityBefore.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                entityBefore.idBD,
                null
            )
            val result = datasource.setSentNote(
                id = 1,
                idBD = 1
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
            val entityAfter = listAfter[0]
            assertEquals(
                entityAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                entityAfter.idBD,
                1L
            )
        }

}