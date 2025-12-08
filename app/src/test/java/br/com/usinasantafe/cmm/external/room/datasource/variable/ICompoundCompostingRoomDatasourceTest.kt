package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.variable.CompoundCompostingDao
import br.com.usinasantafe.cmm.infra.models.room.variable.CompoundCompostingRoomModel
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
class ICompoundCompostingRoomDatasourceTest {

    private lateinit var compoundCompostingDao: CompoundCompostingDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: ICompoundCompostingRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        compoundCompostingDao = db.compoundCompostingDao()
        datasource = ICompoundCompostingRoomDatasource(compoundCompostingDao)
    }

    @After
    fun tearDown() {
        db.close()
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
            compoundCompostingDao.insert(
                CompoundCompostingRoomModel(
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
            compoundCompostingDao.insert(
                CompoundCompostingRoomModel(
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