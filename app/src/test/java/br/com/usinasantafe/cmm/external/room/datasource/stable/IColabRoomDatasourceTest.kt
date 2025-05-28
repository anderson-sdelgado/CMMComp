package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
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
class IColabRoomDatasourceTest {

    private lateinit var colabDao: ColabDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IColabRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        colabDao = db.colabDao()
        datasource = IColabRoomDatasource(colabDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = colabDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    ),
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IColabRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = colabDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = colabDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    ),
                    ColabRoomModel(
                        regColab = 2,
                        nameColab = "TESTE2",
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
            val qtdAfter = colabDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = colabDao.listAll()
            assertEquals(
                list[0].regColab,
                1
            )
            assertEquals(
                list[0].nameColab,
                "TESTE"
            )
            assertEquals(
                list[1].regColab,
                2
            )
            assertEquals(
                list[1].nameColab,
                "TESTE2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    )
                )
            )
            val qtdBefore = colabDao.listAll().size
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
            val qtdAfter = colabDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `checkReg - Check return true if have reg in database`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    )
                )
            )
            val qtdBefore = colabDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.checkByReg(1)
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
    fun `checkReg - Check return false if not have matric in database`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                        nameColab = "TESTE",
                    )
                )
            )
            val qtdBefore = colabDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.checkByReg(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }
}