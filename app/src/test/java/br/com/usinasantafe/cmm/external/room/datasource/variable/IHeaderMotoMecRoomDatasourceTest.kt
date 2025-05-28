package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

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
            val qtdBefore = headerMotoMecDao.listAll().size
            assertEquals(
                qtdBefore,
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
            val qtdAfter = headerMotoMecDao.listAll().size
            assertEquals(
                qtdAfter,
                1
            )
            val entity = headerMotoMecDao.listAll()[0]
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

}