package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderCheckListDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel
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
class IHeaderCheckListRoomDatasourceTest {

    private lateinit var headerCheckListDao: HeaderCheckListDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IHeaderCheckListRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        headerCheckListDao = db.headerCheckListDao()
        datasource = IHeaderCheckListRoomDatasource(headerCheckListDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = headerCheckListDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                HeaderCheckListRoomModel(
                    nroTurn = 1,
                    regOperator = 1,
                    nroEquip = 1,
                    dateHour = Date(1760711032)
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
            val listAfter = headerCheckListDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.nroTurn,
                1
            )
            assertEquals(
                model.regOperator,
                1
            )
            assertEquals(
                model.nroEquip,
                1
            )
            assertEquals(
                model.dateHour,
                Date(1760711032)
            )
        }
}