package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.external.room.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import br.com.usinasantafe.cmm.utils.TypeEquip
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
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
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
                entity.hourMeterInitial,
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
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
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
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getIdByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
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

    @Test
    fun `setHourMeterFinish - Check return failure if not have header open`() =
        runTest {
            val result = datasource.setHourMeterFinish(10.0)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.setHourMeterFinish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setHourMeterFinish(java.lang.Double)\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `setHourMeterFinish - Check alter data if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.hourMeterFinish,
                null
            )
            val result = datasource.setHourMeterFinish(10.0)
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
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.hourMeterFinish!!,
                10.0,
                0.0
            )
        }

    @Test
    fun `finish - Check return failure if not have header open`() =
        runTest {
            val result = datasource.finish()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.finish"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.setStatus(br.com.usinasantafe.cmm.utils.Status)\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `finish  - Check data alter if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    hourMeterFinish = 20.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.status,
                Status.OPEN
            )
            val result = datasource.finish()
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
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.status,
                Status.FINISH
            )
        }
    
    @Test
    fun `checkHeaderSend - Check return false if not have header to send`() =
        runTest {
            val result = datasource.checkHeaderSend()
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
    fun `checkHeaderSend - Check return true if not have header to send`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    hourMeterFinish = 20.0,
                    dateHourInitial = Date(1748359002),
                    dateHourFinish = Date(1748359002),
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val result = datasource.checkHeaderSend()
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
                entity.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                entity.hourMeterFinish!!,
                20.0,
                0.0
            )
            assertEquals(
                entity.status,
                Status.CLOSE
            )
        }

    @Test
    fun `getStatusConByHeaderOpen - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getStatusConByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getStatusConByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getStatusCon()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getStatusConByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val result = datasource.getStatusConByHeaderOpen()
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
    fun `listHeaderSend - Check return emptyList if not have header to send`() =
        runTest {
            val result = datasource.listHeaderSend()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<HeaderMotoMecRoomModel>()
            )
        }

    @Test
    fun `listHeaderSend - Check return list if not have header to send`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 1,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    hourMeterFinish = 20.0,
                    dateHourInitial = Date(1748359002),
                    dateHourFinish = Date(1748359002),
                    status = Status.CLOSE,
                    statusCon = true
                )
            )
            val result = datasource.listHeaderSend()
            assertEquals(
                result.isSuccess,
                true
            )
            val modelList = result.getOrNull()!!
            assertEquals(
                modelList.size,
                1
            )
            val entity = modelList[0]
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
                1
            )
            assertEquals(
                entity.idActivity,
                1
            )
            assertEquals(
                entity.hourMeterInitial,
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
            assertEquals(
                entity.statusCon,
                true
            )
            assertEquals(
                entity.dateHourFinish!!.time,
                1748359002
            )
            assertEquals(
                entity.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                entity.hourMeterFinish!!,
                20.0,
                0.0
            )
            assertEquals(
                entity.status,
                Status.CLOSE
            )
        }

    @Test
    fun `setSentHeader - Check alter data if execute success`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.statusSend,
                StatusSend.SEND
            )
            assertEquals(
                modelBefore.idBD,
                null
            )
            val result = datasource.setSentHeader(
                id = 1,
                idBD = 1
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
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.statusSend,
                StatusSend.SENT
            )
            assertEquals(
                modelAfter.idBD,
                1L
            )
        }

    @Test
    fun `setSendHeader - Check alter data if execute success`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true,
                    statusSend = StatusSend.SENT
                )
            )
            val listBefore = headerMotoMecDao.listAll()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.statusSend,
                StatusSend.SENT
            )
            val result = datasource.setSendHeader(
                id = 1
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
            val modelAfter = listAfter[0]
            assertEquals(
                modelAfter.statusSend,
                StatusSend.SEND
            )
        }

    @Test
    fun `getIdTurnByHeaderOpen - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getIdTurnByHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getIdTurn()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getIdTurnByHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 123465,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val result = datasource.getIdTurnByHeaderOpen()
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