package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.required
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

    override suspend fun getByIdEquipAndOpen(idEquip: Int): Result<HeaderMotoMecRoomModel> =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getByIdEquipAndOpen(idEquip)
            model::id.required()
            model
        }

    override suspend fun getById(id: Int): Result<HeaderMotoMecRoomModel> =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getById(id)
            model::id.required()
            model
        }

    override suspend fun getIdByIdEquipAndOpen(idEquip: Int): Result<Int> =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getByIdEquipAndOpen(idEquip)
            model::id.required()
        }

    override suspend fun getIdByIdEquipAndNotFinish(idEquip: Int): Result<Int> =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getByIdEquipAndNotFinish(idEquip)
            model::id.required()
        }

    override suspend fun hasByOpenOrClose(): Result<Boolean> =
        result(getClassAndMethod()) {
            headerMotoMecDao.hasByDiffStatus(Status.FINISH)
        }

    override suspend fun getIdByOpen(): Result<Int> =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getByStatusOpen()
            model::id.required()
        }

    override suspend fun setHourMeterFinish(hourMeter: Double): EmptyResult =
        result(getClassAndMethod()) {
            val roomModel = headerMotoMecDao.getByStatusOpen()
            roomModel.hourMeterFinish = hourMeter
            headerMotoMecDao.update(roomModel)
        }

    override suspend fun finish(): EmptyResult =
        result(getClassAndMethod()) {
            val list = headerMotoMecDao.listByNotFinish()
            for(model in list){
                model.status = Status.FINISH
                model.statusSend = StatusSend.SEND
                model.dateHourFinish = Date()
                headerMotoMecDao.update(model)
            }
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
            headerMotoMecDao.getByStatusOpen().statusCon
        }

    override suspend fun setSent(
        id: Int,
        idServ: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getById(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            headerMotoMecDao.update(model)
        }

    override suspend fun setSend(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            val model = headerMotoMecDao.getById(id)
            model.statusSend = StatusSend.SEND
            headerMotoMecDao.update(model)
        }

    override suspend fun getIdTurn(): Result<Int> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatusOpen().idTurn
        }

    override suspend fun getRegOperator(): Result<Int> =
        result(getClassAndMethod()) {
            headerMotoMecDao.getByStatusOpen().regOperator
        }

    override suspend fun getFlowComposting(): Result<FlowComposting> =
        result(getClassAndMethod()) {
            val roomModel = headerMotoMecDao.getByStatusOpen()
            roomModel::flowComposting.required()
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<HeaderMotoMecRoomModel>> =
        result(getClassAndMethod()) {
            headerMotoMecDao.listByIdHeader(idHeader)
        }

    override suspend fun updateOpenToClose(): EmptyResult =
        result(getClassAndMethod()) {
            headerMotoMecDao.updateOpenToClose()
        }

    override suspend fun updateStatusOpenByIdEquip(idEquip: Int): EmptyResult =
        result(getClassAndMethod()) {
            headerMotoMecDao.updateStatusOpenByIdEquip(idEquip)
        }

    override suspend fun updateStatusOpenById(id: Int): EmptyResult =
        result(getClassAndMethod()) {
            headerMotoMecDao.updateStatusOpenById(id)
        }

}