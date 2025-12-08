package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
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
class IItemCheckListRoomDatasourceTest {

    private lateinit var itemCheckListDao: ItemCheckListDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IItemCheckListRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        itemCheckListDao = db.itemCheckListDao()
        datasource = IItemCheckListRoomDatasource(itemCheckListDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = itemCheckListDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IActivityRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = itemCheckListDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = itemCheckListDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 2,
                        descrItemCheckList = "Teste 2",
                        idCheckList = 10
                    )
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
            val qtdAfter = itemCheckListDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            itemCheckListDao.insertAll(
                listOf(
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 2,
                        descrItemCheckList = "Teste 2",
                        idCheckList = 10
                    )
                )
            )
            val qtdBefore = itemCheckListDao.all().size
            assertEquals(
                qtdBefore,
                2
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = itemCheckListDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdList - Check return list if have data research`() =
        runTest {
            itemCheckListDao.insertAll(
                listOf(
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 2,
                        descrItemCheckList = "Teste 2",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 3,
                        descrItemCheckList = "Teste 3",
                        idCheckList = 20
                    )
                )
            )
            val qtdBefore = itemCheckListDao.all().size
            assertEquals(
                qtdBefore,
                3
            )
            val result = datasource.listByIdCheckList(10)
            assertEquals(
                result.isSuccess,
                true
            )
            val modelList = result.getOrNull()!!
            assertEquals(
                modelList.size,
                2
            )
            val model1 = modelList[0]
            assertEquals(
                model1.idItemCheckList,
                1
            )
            assertEquals(
                model1.descrItemCheckList,
                "Teste 1"
            )
            assertEquals(
                model1.idCheckList,
                10
            )
            val model2 = modelList[1]
            assertEquals(
                model2.idItemCheckList,
                2
            )
            assertEquals(
                model2.descrItemCheckList,
                "Teste 2"
            )
            assertEquals(
                model2.idCheckList,
                10
            )
        }

    @Test
    fun `countByIdList - Check return list if have data research`() =
        runTest {
            itemCheckListDao.insertAll(
                listOf(
                    ItemCheckListRoomModel(
                        idItemCheckList = 1,
                        descrItemCheckList = "Teste 1",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 2,
                        descrItemCheckList = "Teste 2",
                        idCheckList = 10
                    ),
                    ItemCheckListRoomModel(
                        idItemCheckList = 3,
                        descrItemCheckList = "Teste 3",
                        idCheckList = 20
                    )
                )
            )
            val qtdBefore = itemCheckListDao.all().size
            assertEquals(
                qtdBefore,
                3
            )
            val result = datasource.countByIdCheckList(10)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2
            )
        }
}