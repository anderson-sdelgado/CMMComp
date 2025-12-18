package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquip
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
                        typeEquip = TypeEquip.REEL_FERT,
                    ),
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT,
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
                        typeEquip = TypeEquip.REEL_FERT,
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 20,
                        codClass = 20,
                        descrClass = "TRATOR 2",
                        typeEquip = TypeEquip.REEL_FERT,
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
                TypeEquip.REEL_FERT
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
                TypeEquip.REEL_FERT
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
                        typeEquip = TypeEquip.REEL_FERT,
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
    fun `getDescrById - Check return failure if not have data`() =
        runTest {
            val result = datasource.getDescrById(1)
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
    fun `getDescrById - Check return correct if function execute successfully`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT,
                    )
                )
            )
            val qtdBefore = equipDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.getDescrById(1)
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
    fun `hasByNroAndTypeEquip - Check return false if not have data`() =
        runTest {
            val result = datasource.hasByNroAndType(
                nroEquip = 10,
                typeEquip = TypeEquip.TRANSHIPMENT
            )
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
    fun `hasByNroAndType - Check return false if have data but not typeEquip is equals`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 10,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    )
                )
            )
            val result = datasource.hasByNroAndType(
                nroEquip = 10,
                typeEquip = TypeEquip.TRANSHIPMENT
            )
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
    fun `hasByNroAndType - Check return false if have data but not nroEquip is equals`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.TRANSHIPMENT
                    )
                )
            )
            val result = datasource.hasByNroAndType(
                nroEquip = 10,
                typeEquip = TypeEquip.TRANSHIPMENT
            )
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
    fun `hasByNroEquipAndTypeEquip - Check return true if have data equals data field`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 1,
                        nro = 2200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT
                    ),
                    EquipRoomModel(
                        id = 2,
                        nro = 200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.TRANSHIPMENT
                    )
                )
            )
            val result = datasource.hasByNroAndType(
                nroEquip = 200,
                typeEquip = TypeEquip.TRANSHIPMENT
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `getIdByNro - Check return failure if not have data`() =
        runTest {
            val result = datasource.getIdByNro(2200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IEquipRoomDatasource.getIdByNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel.getId()\" because \"model\" is null"
            )
        }

    @Test
    fun `getIdByNro - Check return correct if function execute successfully`() =
        runTest {
            equipDao.insertAll(
                listOf(
                    EquipRoomModel(
                        id = 20,
                        nro = 2200,
                        codClass = 20,
                        descrClass = "TRATOR",
                        typeEquip = TypeEquip.REEL_FERT,
                    )
                )
            )
            val qtdBefore = equipDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.getIdByNro(2200)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                20
            )
        }

}