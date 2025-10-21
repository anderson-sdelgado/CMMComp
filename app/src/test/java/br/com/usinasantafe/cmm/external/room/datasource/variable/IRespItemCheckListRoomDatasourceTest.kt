package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.RespItemCheckListDao
import br.com.usinasantafe.cmm.infra.models.room.variable.RespItemCheckListRoomModel
import br.com.usinasantafe.cmm.utils.OptionRespCheckList
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
class IRespItemCheckListRoomDatasourceTest {

    private lateinit var respItemCheckListDao: RespItemCheckListDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IRespItemCheckListRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        respItemCheckListDao = db.respItemCheckListDao()
        datasource = IRespItemCheckListRoomDatasource(respItemCheckListDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = respItemCheckListDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                RespItemCheckListRoomModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING,
                    idHeader = 1
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
            val listAfter = respItemCheckListDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.idItem,
                1
            )
            assertEquals(
                model.option,
                OptionRespCheckList.ACCORDING
            )
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.id,
                1
            )
        }
}