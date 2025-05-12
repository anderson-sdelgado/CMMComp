package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.ActivityDao
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
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
class IActivityRoomDatasourceTest {

    private lateinit var atividadeDao: ActivityDao
    private lateinit var db: DatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        atividadeDao = db.atividadeDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check failure addAll if have row repeated`() =
        runTest {
            val qtdBefore = atividadeDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IActivityRoomDatasource(atividadeDao)
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
                    ),
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
                    ),
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
            val qtdAfter = atividadeDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `Check success addAll if have row is correct`() =
        runTest {
            val qtdBefore = atividadeDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IActivityRoomDatasource(atividadeDao)
            val result = datasource.addAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TEST",
                    ),
                    ActivityRoomModel(
                        idActivity = 2,
                        codActivity = 20,
                        descrActivity = "TEST",
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
            val qtdAfter = atividadeDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `Check execution correct deleteAll`() =
        runTest {
            atividadeDao.insertAll(
                listOf(
                    ActivityRoomModel(
                        idActivity = 1,
                        codActivity = 10,
                        descrActivity = "TESTE",
                    ),
                )
            )
            val qtdBefore = atividadeDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IActivityRoomDatasource(atividadeDao)
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = atividadeDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}