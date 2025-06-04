package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
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
    private lateinit var datasource: IEquipRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        equipDao = db.equipDao()
        datasource = IEquipRoomDatasource(equipDao)
    }

    @After
    fun tearDown() {
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
                        measure = 0.0,
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
                        measure = 0.0,
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
                        measure = 0.0,
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
                        measure = 0.0,
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
                list[0].measure,
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
                        measure = 0.0,
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
    fun `getDescrByIdEquip - Check return failure if not have data`() =
        runTest {
            val result = datasource.getDescrByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRoomDatasource.getDescrByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getDescrClass()\" because \"model\" is null"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return correct if function execute successfully`() =
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
                        measure = 0.0,
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
            val result = datasource.getDescrByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "10 - TRATOR"
            )
        }

    @Test
    fun `getCodTurnEquipByIdEquip- Check return failure if not have data`() =
        runTest {
            val result = datasource.getCodTurnEquipByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRoomDatasource.getCodTurnEquipByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getCodTurnEquip()\" because \"model\" is null"
            )
        }

    @Test
    fun `getCodTurnEquipByIdEquip - Check return correct if function execute successfully`() =
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
                        measure = 0.0,
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
            val result = datasource.getCodTurnEquipByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `getMeasureByIdEquip - Check return failure if not have data`() =
        runTest {
            val result = datasource.getMeasureByIdEquip(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRoomDatasource.getMeasureByIdEquip"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getMeasurement()\" because \"model\" is null"
            )
        }

    @Test
    fun `getMeasureByIdEquip - Check return correct if function execute successfully`() =
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
                        measure = 10000.0,
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
            val result = datasource.getMeasureByIdEquip(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                10000.0,
                0.0
            )
        }

    @Test
    fun `updateMeasureByIdEquip - Check alter data`() =
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
                        measure = 10000.0,
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
            val modelBefore =    equipDao.getByIdEquip(1)
            assertEquals(
                modelBefore.measure,
                10000.0,
                0.0
            )
            val result = datasource.updateMeasureByIdEquip(
                measure = 20000.0,
                idEquip = 1
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
                1
            )
            val modelAfter = equipDao.getByIdEquip(1)
            assertEquals(
                modelAfter.measure,
                20000.0,
                0.0
            )
        }

}