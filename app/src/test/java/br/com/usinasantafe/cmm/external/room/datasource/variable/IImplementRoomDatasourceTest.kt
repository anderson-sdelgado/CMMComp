package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.dao.variable.ImplementDao
import br.com.usinasantafe.cmm.infra.models.room.variable.ImplementRoomModel
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.lib.OptionRespCheckList
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
class IImplementRoomDatasourceTest {

    private lateinit var implementDao: ImplementDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IImplementRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        implementDao = db.implementDao()
        datasource = IImplementRoomDatasource(implementDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = implementDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                ImplementRoomModel(
                    idItem = 1,
                    nroEquip = 10,
                    pos = 1
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
            val listAfter = implementDao.all()
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
                model.nroEquip,
                10
            )
            assertEquals(
                model.pos,
                1
            )
            assertEquals(
                model.id,
                1
            )
        }

}