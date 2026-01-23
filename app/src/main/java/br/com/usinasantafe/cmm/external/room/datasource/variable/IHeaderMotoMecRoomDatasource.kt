package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.Status
import br.com.usinasantafe.cmm.lib.StatusSend
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IHeaderMotoMecRoomDatasource @Inject constructor(
    private val headerMotoMecDao: HeaderMotoMecDao
) : HeaderMotoMecRoomDatasource {

    override suspend fun save(
        headerMotoMecRoomModel: HeaderMotoMecRoomModel
    ): Result<Long> {
        try {
            val id = headerMotoMecDao.insert(headerMotoMecRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getOpen(): Result<HeaderMotoMecRoomModel> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(headerMotoMecDao.countByStatus(Status.OPEN) > 0)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getId(): Result<Int> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.id!!)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setHourMeterFinish(hourMeter: Double): EmptyResult {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.hourMeterFinish = hourMeter
            headerMotoMecDao.update(roomModel)
            return Result.success(Unit)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun finish(): EmptyResult {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.status = Status.FINISH
            roomModel.statusSend = StatusSend.SEND
            roomModel.dateHourFinish = Date()
            headerMotoMecDao.update(roomModel)
            return Result.success(Unit)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(headerMotoMecDao.listByStatusSend(StatusSend.SEND).isNotEmpty())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listSend(): Result<List<HeaderMotoMecRoomModel>> {
        return try {
            Result.success(headerMotoMecDao.listByStatusSend(StatusSend.SEND))
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getStatusCon(): Result<Boolean> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.statusCon)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setSent(
        id: Int,
        idServ: Int
    ): Result<Boolean> {
        try {
            val model = headerMotoMecDao.get(id)
            model.idServ = idServ
            model.statusSend = StatusSend.SENT
            headerMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        try {
            val model = headerMotoMecDao.get(id)
            model.statusSend = StatusSend.SEND
            headerMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdTurn(): Result<Int> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.idTurn)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getRegOperator(): Result<Int> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.regOperator)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getFlowComposting(): Result<FlowComposting> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            if(roomModel.flowComposting == null){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = Exception("flowComposting is null")
                )
            }
            return Result.success(roomModel.flowComposting)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }


}