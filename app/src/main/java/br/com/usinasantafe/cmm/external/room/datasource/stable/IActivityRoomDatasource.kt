package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.ActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import javax.inject.Inject

class IActivityRoomDatasource @Inject constructor(
    private val activityDao: ActivityDao
) : ActivityRoomDatasource {
    override suspend fun addAll(list: List<ActivityRoomModel>): Result<Boolean> {
        try {
            activityDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IActivityRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            activityDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IActivityRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}