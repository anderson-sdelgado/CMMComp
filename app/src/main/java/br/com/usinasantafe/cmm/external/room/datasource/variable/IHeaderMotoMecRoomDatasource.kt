package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import java.util.Date
import javax.inject.Inject

class IHeaderMotoMecRoomDatasource @Inject constructor(
    private val headerMotoMecDao: HeaderMotoMecDao
) : HeaderMotoMecRoomDatasource {

    override suspend fun save(
        headerMotoMecRoomModel: HeaderMotoMecRoomModel
    ): Result<Long> =
        result(getClassAndMethod()) {
            headerMotoMecDao.insert(headerMotoMecRoomModel)
        }

    override suspend fun getOpen(): Result<HeaderMotoMecRoomModel> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatus(Status.OPEN)
        }

    override suspend fun checkOpen(): Result<Boolean> =
        result(getClassAndMethod()) {
            headerMotoMecDao.countByStatus(Status.OPEN) > 0
        }

    override suspend fun getId(): Result<Int> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatus(Status.OPEN).id!!
        }

    override suspend fun setHourMeterFinish(hourMeter: Double): EmptyResult =
        result(getClassAndMethod()) {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.hourMeterFinish = hourMeter
            headerMotoMecDao.update(roomModel)
        }

    override suspend fun finish(): EmptyResult =
        result(getClassAndMethod()) {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.status = Status.FINISH
            roomModel.statusSend = StatusSend.SEND
            roomModel.dateHourFinish = Date()
            headerMotoMecDao.update(roomModel)
        }

    override suspend fun hasSend(): Result<Boolean> =
        result(getClassAndMethod()) {
            headerMotoMecDao.listByStatusSend(StatusSend.SEND).isNotEmpty()
        }

    override suspend fun listSend(): Result<List<HeaderMotoMecRoomModel>> =
        result(getClassAndMethod()) {
            headerMotoMecDao.listByStatusSend(StatusSend.SEND)
        }

    override suspend fun getStatusCon(): Result<Boolean> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatus(Status.OPEN).statusCon
        }

    override suspend fun setSent(
        id: Int,
        idServ: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.get(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            headerMotoMecDao.update(model)
        }

    override suspend fun setSend(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.get(id)
            model.statusSend = StatusSend.SEND
            headerMotoMecDao.update(model)
        }

    override suspend fun getIdTurn(): Result<Int> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatus(Status.OPEN).idTurn
        }

    override suspend fun getRegOperator(): Result<Int> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatus(Status.OPEN).regOperator
        }

    override suspend fun getFlowComposting(): Result<FlowComposting> =
        result(getClassAndMethod()) {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.flowComposting ?: throw Exception("flowComposting is null")
        }

}