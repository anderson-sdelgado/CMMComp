package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.lib.TypeStop
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
class IFunctionStopRoomDatasourceTest {

    private lateinit var functionStopDao: FunctionStopDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IFunctionStopRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        functionStopDao = db.functionStopDao()
        datasource = IFunctionStopRoomDatasource(functionStopDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = functionStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    FunctionStopRoomModel(
                        idFunctionStop = 1,
                        idStop = 1,
                        typeStop = TypeStop.CHECKLIST,
                    ),
                    FunctionStopRoomModel(
                        idFunctionStop = 1,
                        idStop = 1,
                        typeStop = TypeStop.CHECKLIST,
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IFunctionStopRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_function_stop` (`idRFunctionStop`,`idStop`,`typeStop`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_function_stop.idRFunctionStop] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = functionStopDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = functionStopDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    FunctionStopRoomModel(
                        idFunctionStop = 1,
                        idStop = 1,
                        typeStop = TypeStop.CHECKLIST,
                    ),
                    FunctionStopRoomModel(
                        idFunctionStop = 2,
                        idStop = 2,
                        typeStop = TypeStop.IMPLEMENT,
                    ),
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
            val qtdAfter = functionStopDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            functionStopDao.insertAll(
                listOf(
                    FunctionStopRoomModel(
                        idFunctionStop = 1,
                        idStop = 1,
                        typeStop = TypeStop.CHECKLIST,
                    ),
                )
            )
            val qtdBefore = functionStopDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = functionStopDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `getIdStopByType - Check execution correct if not have row fielded`() =
        runTest {
            val result = datasource.getIdStopByType(TypeStop.REEL)
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
    fun `getIdStopByType - Check execution correct if have row fielded`() =
        runTest {
            functionStopDao.insertAll(
                listOf(
                    FunctionStopRoomModel(
                        idFunctionStop = 1,
                        idStop = 1,
                        typeStop = TypeStop.REEL,
                    ),
                )
            )
            val result = datasource.getIdStopByType(TypeStop.REEL)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                1
            )
        }
}