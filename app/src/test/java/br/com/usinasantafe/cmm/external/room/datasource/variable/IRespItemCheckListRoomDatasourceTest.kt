package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemRespCheckListDao
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
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
class IRespItemCheckListRoomDatasourceTest {

    private lateinit var itemRespCheckListDao: ItemRespCheckListDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemRespCheckListRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemRespCheckListDao = db.respItemCheckListDao()
        datasource = IItemRespCheckListRoomDatasource(itemRespCheckListDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = itemRespCheckListDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                ItemRespCheckListRoomModel(
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
            val listAfter = itemRespCheckListDao.all()
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

    @Test
    fun `listByIdHeader - Check return empty list if not have resp of header field`() =
        runTest {
            itemRespCheckListDao.insert(
                ItemRespCheckListRoomModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING,
                    idHeader = 1
                )
            )
            val result = datasource.listByIdHeader(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList()
            )
        }

    @Test
    fun `listByIdHeader - Check return list if have resp of header field`() =
        runTest {
            itemRespCheckListDao.insert(
                ItemRespCheckListRoomModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING,
                    idHeader = 1
                )
            )
            itemRespCheckListDao.insert(
                ItemRespCheckListRoomModel(
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING,
                    idHeader = 2
                )
            )
            val result = datasource.listByIdHeader(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                listOf(
                    ItemRespCheckListRoomModel(
                        id = 2,
                        idItem = 1,
                        option = OptionRespCheckList.ACCORDING,
                        idHeader = 2
                    )
                )
            )
        }

    @Test
    fun `setIdServById - Check alter data if process execute successfully`() =
        runTest {
            itemRespCheckListDao.insert(
                ItemRespCheckListRoomModel(
                    id = 1,
                    idHeader = 1,
                    idItem = 1,
                    option = OptionRespCheckList.ACCORDING
                )
            )
            val listBefore = itemRespCheckListDao.all()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.id,
                1
            )
            assertEquals(
                modelBefore.idHeader,
                1
            )
            assertEquals(
                modelBefore.idItem,
                1
            )
            assertEquals(
                modelBefore.option,
                OptionRespCheckList.ACCORDING
            )
            assertEquals(
                modelBefore.idServ,
                null
            )
            val result = datasource.setIdServById(
                id = 1,
                idServ = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val listAfter = itemRespCheckListDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.id,
                1
            )
            assertEquals(
                modelAfter.idHeader,
                1
            )
            assertEquals(
                modelAfter.idItem,
                1
            )
            assertEquals(
                modelAfter.option,
                OptionRespCheckList.ACCORDING
            )
            assertEquals(
                modelAfter.idServ,
                1
            )
        }
}