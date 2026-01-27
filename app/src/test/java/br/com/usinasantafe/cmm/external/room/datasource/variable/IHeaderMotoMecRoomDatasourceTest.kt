package br.com.usinasantafe.cmm.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.cmm.lib.DatabaseRoom
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.lib.TypeEquip
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.test.assertEquals

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
            val listBefore = headerMotoMecDao.all()
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
                1
            )
            val listAfter = headerMotoMecDao.all()
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
    fun `checkOpen - Check return false if not have header open`() =
        runTest {
            val result = datasource.checkOpen()
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
    fun `checkOpen - Check return true if have header open`() =
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
            val result = datasource.checkOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = headerMotoMecDao.all().size
            assertEquals(
                qtdAfter,
                1
            )
            val entity = headerMotoMecDao.all()[0]
            assertEquals(
                entity.status,
                Status.OPEN
            )
        }

    @Test
    fun `getId - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getId()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getId"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getId()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getId - Check return correct if function execute successfully`() =
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
            val result = datasource.getId()
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
            val listBefore = headerMotoMecDao.all()
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
                Unit
            )
            val listAfter = headerMotoMecDao.all()
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
            val listBefore = headerMotoMecDao.all()
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
                Unit
            )
            val listAfter = headerMotoMecDao.all()
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
    fun `checkSend - Check return false if not have header to send`() =
        runTest {
            val result = datasource.hasSend()
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
    fun `checkSend - Check return true if not have header to send`() =
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
            val result = datasource.hasSend()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = headerMotoMecDao.all().size
            assertEquals(
                qtdAfter,
                1
            )
            val entity = headerMotoMecDao.all()[0]
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
    fun `getStatusCon - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getStatusCon()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getStatusCon"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getStatusCon()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getStatusCon - Check return correct if function execute successfully`() =
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
            val result = datasource.getStatusCon()
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
    fun `listSend - Check return emptyList if not have header to send`() =
        runTest {
            val result = datasource.listSend()
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
    fun `listSend - Check return list if not have header to send`() =
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
            val result = datasource.listSend()
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
    fun `setSent - Check alter data if execute success`() =
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
            val listBefore = headerMotoMecDao.all()
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
                modelBefore.idServ,
                null
            )
            val result = datasource.setSent(
                id = 1,
                idServ = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val listAfter = headerMotoMecDao.all()
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
                modelAfter.idServ,
                1
            )
        }

    @Test
    fun `setSend - Check alter data if execute success`() =
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
            val listBefore = headerMotoMecDao.all()
            assertEquals(
                listBefore.size,
                1
            )
            val modelBefore = listBefore[0]
            assertEquals(
                modelBefore.statusSend,
                StatusSend.SENT
            )
            val result = datasource.setSend(
                id = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                Unit
            )
            val listAfter = headerMotoMecDao.all()
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
    fun `getIdTurn - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getIdTurn()
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
    fun `getIdTurn - Check return correct if function execute successfully`() =
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
            val result = datasource.getIdTurn()
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
    fun `getRegOperator - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getRegOperator()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getRegOperatorOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getRegOperator()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getRegOperator - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
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
            val result = datasource.getRegOperator()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                19859
            )
        }

    @Test
    fun `getFlowComposting - Check return failure if table is empty`() =
        runTest {
            val result = datasource.getFlowComposting()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel.getFlowComposting()\" because \"roomModel\" is null"
            )
        }

    @Test
    fun `getFlowComposting - Check return failure if flowComposting is empty`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
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
            val result = datasource.getFlowComposting()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderMotoMecRoomDatasource.getFlowComposting"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception: flowComposting is null"
            )
        }

    @Test
    fun `getFlowComposting - Check return correct if function execute successfully`() =
        runTest {
            headerMotoMecDao.insert(
                HeaderMotoMecRoomModel(
                    regOperator = 19859,
                    idEquip = 1,
                    typeEquip = TypeEquip.NORMAL,
                    flowComposting = FlowComposting.INPUT,
                    idTurn = 1,
                    nroOS = 123456,
                    idActivity = 1,
                    hourMeterInitial = 10.0,
                    dateHourInitial = Date(1748359002),
                    statusCon = true
                )
            )
            val result = datasource.getFlowComposting()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                FlowComposting.INPUT
            )
        }
}