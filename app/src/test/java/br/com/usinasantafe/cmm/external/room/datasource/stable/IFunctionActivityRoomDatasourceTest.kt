package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.utils.TypeActivity
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
class IFunctionFunctionActivityRoomDatasourceTest {

    private lateinit var functionActivityDao: FunctionActivityDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IFunctionActivityRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        functionActivityDao = db.functionActivityDao()
        datasource = IFunctionActivityRoomDatasource(functionActivityDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = functionActivityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionActivityRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_function_activity` (`idRFunctionActivity`,`idActivity`,`typeActivity`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_function_activity.idRFunctionActivity] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = functionActivityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = functionActivityDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                    FunctionActivityRoomModel(
                        idFunctionActivity = 2,
                        idActivity = 2,
                        typeActivity = TypeActivity.TRANSHIPMENT,
                    ),
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
            val qtdAfter = functionActivityDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                )
            )
            val qtdBefore = functionActivityDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = functionActivityDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `listByIdActivity - Check return emptyList if not have row`() =
        runTest {
            val result = datasource.listByIdActivity(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val qtd = result.getOrNull()!!.size
            assertEquals(
                qtd,
                0
            )
        }

    @Test
    fun `listByIdActivity - Check return list if have row with idActivity correct`() =
        runTest {
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 1,
                        idActivity = 1,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                )
            )
            functionActivityDao.insertAll(
                listOf(
                    FunctionActivityRoomModel(
                        idFunctionActivity = 2,
                        idActivity = 2,
                        typeActivity = TypeActivity.PERFORMANCE,
                    ),
                )
            )
            val result = datasource.listByIdActivity(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idFunctionActivity,
                1
            )
            assertEquals(
                model.idActivity,
                1
            )
            assertEquals(
                model.typeActivity,
                TypeActivity.PERFORMANCE
            )
        }
}