package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import javax.inject.Inject

class IRActivityStopRoomDatasource @Inject constructor(
    private val rActivityStopDao: RActivityStopDao
) : RActivityStopRoomDatasource {

    override suspend fun addAll(list: List<RActivityStopRoomModel>): Result<Boolean> {
        try {
            rActivityStopDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRActivityStopRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rActivityStopDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRActivityStopRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByIdActivity(idActivity: Int): Result<List<RActivityStopRoomModel>> {
        try {
            val list = rActivityStopDao.listByIdActivity(idActivity)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRActivityStopRoomDatasource.listByIdActivity",
                message = "-",
                cause = e
            )
        }
    }
}
