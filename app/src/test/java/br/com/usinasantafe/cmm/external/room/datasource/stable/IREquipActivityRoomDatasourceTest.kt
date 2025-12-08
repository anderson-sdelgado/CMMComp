package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IREquipActivityRoomDatasourceTest {

    private lateinit var rEquipActivityDao: REquipActivityDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IREquipActivityRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        rEquipActivityDao = db.rEquipActivityDao()
        datasource = IREquipActivityRoomDatasource(rEquipActivityDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = rEquipActivityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1,
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1,
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IREquipActivityRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = rEquipActivityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = rEquipActivityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1,
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 1,
                        idActivity = 2,
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
            val list = rEquipActivityDao.all()
            assertEquals(
                list.size,
                2
            )
            val entity1 = list[0]
            assertEquals(
                entity1.idREquipActivity,
                1
            )
            assertEquals(
                entity1.idEquip,
                1
            )
            assertEquals(
                entity1.idActivity,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idREquipActivity,
                2
            )
            assertEquals(
                entity2.idEquip,
                1
            )
            assertEquals(
                entity2.idActivity,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1,
                    )
                )
            )
            val qtdBefore = rEquipActivityDao.all().size
            assertEquals(
                qtdBefore,
                1
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
            val qtdAfter = rEquipActivityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdEquip - Check return list empty if not have idEquip researched`() =
        runTest {
            val result = datasource.listByIdEquip(1)
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
    fun `listByIdEquip - Check return list if have idEquip researched`() =
        runTest {
            rEquipActivityDao.insertAll(
                listOf(
                    REquipActivityRoomModel(
                        idREquipActivity = 1,
                        idEquip = 1,
                        idActivity = 1,
                    ),
                    REquipActivityRoomModel(
                        idREquipActivity = 2,
                        idEquip = 2,
                        idActivity = 2,
                    )
                )
            )
            val result = datasource.listByIdEquip(1)
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
                entity.idREquipActivity,
                1
            )
            assertEquals(
                entity.idEquip,
                1
            )
            assertEquals(
                entity.idActivity,
                1
            )
        }

}