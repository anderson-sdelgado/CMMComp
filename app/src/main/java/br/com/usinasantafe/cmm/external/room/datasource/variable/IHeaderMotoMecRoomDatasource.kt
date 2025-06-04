package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.HeaderMotoMecDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.HeaderMotoMecRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.Status
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
        try {
            val list = headerMotoMecDao.listByStatus(Status.OPEN)
            return if (list.isEmpty()) Result.success(false) else Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.checkHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdByHeaderOpen(): Result<Int> {
        try {
            val list = headerMotoMecDao.listByStatus(Status.OPEN)
            val roomModel = list[0]
            return Result.success(roomModel.id!!)
        } catch (e: Exception) {
            return resultFailure(
                context = "IHeaderMotoMecRoomDatasource.getIdByHeaderOpen",
                message = "-",
                cause = e
            )
        }
    }

}