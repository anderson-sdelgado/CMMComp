package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import javax.inject.Inject

class IROSActivityRoomDatasource @Inject constructor(
    private val rOSActivityDao: ROSActivityDao
) : ROSActivityRoomDatasource {

    override suspend fun addAll(list: List<ROSActivityRoomModel>): Result<Boolean> {
        try {
            rOSActivityDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rOSActivityDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByIdOS(idOS: Int): Result<List<ROSActivityRoomModel>> {
        try {
            val list = rOSActivityDao.listByIdOS(idOS)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRoomDatasource.listByIdOS",
                message = "-",
                cause = e
            )
        }
    }
}
