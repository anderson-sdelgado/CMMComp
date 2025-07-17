package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
import br.com.usinasantafe.cmm.utils.StatusSend
import java.util.Date
import javax.inject.Inject

class IHeaderMotoMecRoomDatasource @Inject constructor(
    private val headerMotoMecDao: HeaderMotoMecDao
) : HeaderMotoMecRoomDatasource {

    override suspend fun save(
        headerMotoMecRoomModel: HeaderMotoMecRoomModel
    ): Result<Boolean> {
        try {
            headerMotoMecDao.insert(headerMotoMecRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.insert",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkHeaderOpen(): Result<Boolean> {
        return try {
            Result.success(headerMotoMecDao.countByStatus(Status.OPEN) > 0)
        } catch (e: Exception) {
            resultFailure(
                context = "IHeaderMotoMecRoomDatasource.checkHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.id!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.getIdByHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setHourMeterFinish(hourMeter: Double): Result<Boolean> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.hourMeterFinish = hourMeter
            headerMotoMecDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.setHourMeterFinish",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun finish(): Result<Boolean> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            roomModel.status = Status.FINISH
            roomModel.statusSend = StatusSend.SEND
            roomModel.dateHourFinish = Date()
            headerMotoMecDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.finish",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkHeaderSend(): Result<Boolean> {
        return try {
            Result.success(headerMotoMecDao.listByStatusSend(StatusSend.SEND).isNotEmpty())
        } catch (e: Exception) {
            resultFailure(
                context = "IHeaderMotoMecRoomDatasource.checkHeaderSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listHeaderSend(): Result<List<HeaderMotoMecRoomModel>> {
        return try {
            Result.success(headerMotoMecDao.listByStatusSend(StatusSend.SEND))
        } catch (e: Exception) {
            resultFailure(
                context = "IHeaderMotoMecRoomDatasource.checkHeaderSend",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getStatusConByHeaderOpen(): Result<Boolean> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.statusCon)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.getStatusConByHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSentHeader(
        id: Int,
        idBD: Long
    ): Result<Boolean> {
        try {
            val model = headerMotoMecDao.get(id)
            model.idBD = idBD
            model.statusSend = StatusSend.SENT
            headerMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.setSentHeader",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun setSendHeader(id: Int): Result<Boolean> {
        try {
            val model = headerMotoMecDao.get(id)
            model.statusSend = StatusSend.SEND
            headerMotoMecDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.setSentHeader",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdTurnByHeaderOpen(): Result<Int> {
        try {
            val roomModel = headerMotoMecDao.getByStatus(Status.OPEN)
            return Result.success(roomModel.idTurn)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.getIdTurnByHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getRegOperatorOpen(): Result<Int> {
        TODO("Not yet implemented")
    }

}