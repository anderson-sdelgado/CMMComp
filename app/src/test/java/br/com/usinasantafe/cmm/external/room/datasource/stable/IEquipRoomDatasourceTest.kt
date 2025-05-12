package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.EquipDao
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IEquipRoomDatasourceTest {

    private lateinit var equipDao: EquipDao
    private lateinit var db: DatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        equipDao = db.equipDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = equipDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IEquipRoomDatasource(equipDao)
            val result = datasource.addAll(
                listOf(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
                    ),
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = equipDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = equipDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IEquipRoomDatasource(equipDao)
            val result = datasource.addAll(
                listOf(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
                    ),
                    EquipRoomModel(
                        idEquip = 2,
                        nroEquip = 20,
                        codClass = 20,
                        descrClass = "TRATOR 2",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
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
            val qtdAfter = equipDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = equipDao.listAll()
            assertEquals(
                list[0].idEquip,
                1
            )
            assertEquals(
                list[0].nroEquip,
                10
            )
            assertEquals(
                list[0].codClass,
                20
            )
            assertEquals(
                list[0].descrClass,
                "TRATOR"
            )
            assertEquals(
                list[0].hourmeter,
                0.0,
                0.0
            )
            assertEquals(
                list[0].measurement,
                0.0,
                0.0
            )
            assertEquals(
                list[0].flagApontMecan,
                true
            )
            assertEquals(
                list[0].flagApontPneu,
                true
            )
            assertEquals(
                list[1].idEquip,
                2
            )
            assertEquals(
                list[1].nroEquip,
                20
            )
            assertEquals(
                list[1].codClass,
                20
                )
            assertEquals(
                list[1].descrClass,
                "TRATOR 2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
                    )
                )
            )
            val qtdBefore = equipDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IEquipRoomDatasource(equipDao)
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
                )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = equipDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `getById - Check return null if not have data in table`() =
        runTest {
            val datasource = IEquipRoomDatasource(equipDao)
            val result = datasource.getByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                null
            )
        }

    @Test
    fun `getById - Check execution correct`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        idEquip = 1,
                        nroEquip = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        codTurnEquip = 1,
                        idCheckList = 1,
                        typeFert = 1,
                        hourmeter = 0.0,
                        measurement = 0.0,
                        type = 1,
                        classify = 1,
                        flagApontMecan = true,
                        flagApontPneu = true
                    )
                )
            )
            val datasource = IEquipRoomDatasource(equipDao)
            val result = datasource.getByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
                )
            assertEquals(
                result.getOrNull()!!.idEquip,
                1
            )
            assertEquals(
                result.getOrNull()!!.nroEquip,
                10
            )
            assertEquals(
                result.getOrNull()!!.codClass,
                20
            )
            assertEquals(
                result.getOrNull()!!.descrClass,
                "TRATOR"
            )
        }
}