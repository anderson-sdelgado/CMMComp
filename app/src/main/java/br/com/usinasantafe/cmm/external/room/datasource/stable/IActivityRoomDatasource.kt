package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRoomDatasource @Inject constructor(
    private val activityDao: ActivityDao
) : ActivityRoomDatasource {
    override suspend fun addAll(list: List<ActivityRoomModel>): EmptyResult {
        try {
            activityDao.insertAll(list)
            return Result.success(Unit)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): EmptyResult {
        try {
            activityDao.deleteAll()
            return Result.success(Unit)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdList(idList: List<Int>): Result<List<ActivityRoomModel>> {
        try {
            val list = activityDao.listByIdList(idList)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getById(id: Int): Result<ActivityRoomModel> {
        try {
            val model = activityDao.getById(id)
            return Result.success(model)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}