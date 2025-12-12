package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquipSecondary
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
            val qtdBefore = equipDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL,
                    ),
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL,
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
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_equip` (`id`,`nro`,`codClass`,`descrClass`,`typeEquip`) VALUES (?,?,?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_equip.id] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = equipDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = equipDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL,
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 20,
                        codClass = 20,
                        descrClass = "TRATOR 2",
                        typeEquip = TypeEquipSecondary.REEL,
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
            val list = equipDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.nro,
                10
            )
            assertEquals(
                model1.codClass,
                20
            )
            assertEquals(
                model1.descrClass,
                "TRATOR"
            )
            assertEquals(
                model1.typeEquip,
                TypeEquipSecondary.REEL
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                2
            )
            assertEquals(
                model2.nro,
                20
            )
            assertEquals(
                model2.codClass,
                20
                )
            assertEquals(
                model2.descrClass,
                "TRATOR 2"
            )
            assertEquals(
                model2.typeEquip,
                TypeEquipSecondary.REEL
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL,
                    )
                )
            )
            val qtdBefore = equipDao.all().size
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
            val qtdAfter = equipDao.all().size
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
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getNro()\" because \"model\" is null"
            )
        }

    @Test
    fun `getDescrByIdEquip - Check return correct if function execute successfully`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquipSecondary.REEL,
                    )
                )
            )
            val qtdBefore = equipDao.all().size
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

}