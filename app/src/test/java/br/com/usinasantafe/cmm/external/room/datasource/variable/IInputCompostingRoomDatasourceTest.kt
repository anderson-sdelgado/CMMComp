package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.variable.InputCompostingDao
import br.com.usinasantafe.cmm.infra.models.room.variable.InputCompostingRoomModel
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.lib.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IInputCompostingRoomDatasourceTest {

    private lateinit var inputCompostingDao: InputCompostingDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IInputCompostingRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        inputCompostingDao = db.inputCompostingDao()
        datasource = IInputCompostingRoomDatasource(inputCompostingDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `hasSentLoad - Check return false when list is empty`() =
        runTest {
            val result = datasource.hasSentLoad()
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
    fun `hasSentLoad - Check return true when list is not empty`() =
        runTest {
            inputCompostingDao.insert(
                InputCompostingRoomModel(
                    statusSend = StatusSend.SENT
                )
            )
            val result = datasource.hasSentLoad()
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
    fun `hasWill - Check return false if not have open input composting`() =
        runTest {
            val result = datasource.hasWill()
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
    fun `hasWill - Check return false if idWill is null`() =
        runTest {
            inputCompostingDao.insert(
                InputCompostingRoomModel(
                    statusSend = StatusSend.SENT
                )
            )
            val result = datasource.hasWill()
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
    fun `hasWill - Check return true if idWill is not null`() =
        runTest {
            inputCompostingDao.insert(
                InputCompostingRoomModel(
                    idWill = 2,
                    statusSend = StatusSend.SENT
                )
            )
            val result = datasource.hasWill()
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