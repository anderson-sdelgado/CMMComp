package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.math.sign

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderMotoMecRoomDatasourceTest {

    private lateinit var headerMotoMecDao: HeaderMotoMecDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IHeaderMotoMecRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        headerMotoMecDao = db.headerMotoMecDao()
        datasource = IHeaderMotoMecRoomDatasource(headerMotoMecDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `save - Check save and data`() =
        runTest {
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    measureInitial = 10.0,
                    dateHourInitial = Date(1748359002)
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
            val listAfter = headerMotoMecDao.listAll()
            assertEquals(
                listAfter.size,
                1
            )
            val entity = listAfter[0]
            assertEquals(
                entity.regOperator,
                123465
            )
            assertEquals(
                entity.idEquip,
                1
            )
            assertEquals(
                entity.idTurn,
                1
            )
            assertEquals(
                entity.nroOS,
                123456
            )
            assertEquals(
                entity.idActivity,
                1
            )
            assertEquals(
                entity.measureInitial,
                10.0,
                0.0
            )
            assertEquals(
                entity.dateHourInitial.time,
                1748359002
            )
            assertEquals(
                entity.id,
                1
            )
        }

    @Test
    fun `checkHeaderOpen - Check return false if not have header open`() =
        runTest {
            val result = datasource.checkHeaderOpen()
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
    fun `checkHeaderOpen - Check return true if have header open`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    measureInitial = 10.0,
                    dateHourInitial = Date(1748359002)
                )
            )
            val result = datasource.checkHeaderOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = headerMotoMecDao.listAll().size
            assertEquals(
                qtdAfter,
                1
            )
            val entity = headerMotoMecDao.listAll()[0]
            assertEquals(
                entity.status,
                Status.OPEN
            )
        }

    @Test
    fun `getIdByHeaderOpen - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getIdByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getIdByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"
            )
        }

    @Test
    fun `getIdByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    measureInitial = 10.0,
                    dateHourInitial = Date(1748359002)
                )
            )
            val result = datasource.getIdByHeaderOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

}