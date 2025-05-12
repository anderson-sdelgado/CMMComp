package br.com.usinasantafe.cmm.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.TurnDao
import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ITurnRoomDatasourceTest {

    private lateinit var turnDao: TurnDao
    private lateinit var db: DatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        turnDao = db.turnoDao()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = turnDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = ITurnRoomDatasource(turnDao)
            val result = datasource.addAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    ),
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ITurnoRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = turnDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = turnDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = ITurnRoomDatasource(turnDao)
            val result = datasource.addAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    ),
                    TurnRoomModel(
                        idTurn = 2,
                        codTurnEquip = 101,
                        nroTurn = 2,
                        descrTurn = "TURNO 2"
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
            val qtdAfter = turnDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = turnDao.listAll()
            assertEquals(
                list[0].idTurn,
                1
            )
            assertEquals(
                list[0].codTurnEquip,
                101
            )
            assertEquals(
                list[0].nroTurn,
                1
            )
            assertEquals(
                list[0].descrTurn,
                "TURNO 1"
            )
            assertEquals(
                list[1].idTurn,
                2
            )
            assertEquals(
                list[1].codTurnEquip,
                101
            )
            assertEquals(
                list[1].nroTurn,
                2
                )
            assertEquals(
                list[1].descrTurn,
                "TURNO 2"
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            turnDao.insertAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    )
                )
            )
            val qtdBefore = turnDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = ITurnRoomDatasource(turnDao)
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = turnDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `getListByCodTurnEquip - Check return list empty if have no row in field`() =
        runTest {
            turnDao.insertAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    )
                )
            )
            val qtdBefore = turnDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = ITurnRoomDatasource(turnDao)
            val result = datasource.getListByCodTurnEquip(102)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<TurnRoomModel>()
            )
        }

    @Test
    fun `getListByCodTurnEquip - Check return list if have row in field`() =
        runTest {
            turnDao.insertAll(
                listOf(
                    TurnRoomModel(
                        idTurn = 1,
                        codTurnEquip = 101,
                        nroTurn = 1,
                        descrTurn = "TURNO 1"
                    )
                )
            )
            val qtdBefore = turnDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = ITurnRoomDatasource(turnDao)
            val result = datasource.getListByCodTurnEquip(101)
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
                model.idTurn,
                1
            )
            assertEquals(
                model.codTurnEquip,
                101
            )
            assertEquals(
                model.nroTurn,
                1
            )
            assertEquals(
                model.descrTurn,
                "TURNO 1"
            )
        }

}