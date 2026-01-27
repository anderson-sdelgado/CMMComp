package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.result
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRoomDatasource @Inject constructor(
    private val activityDao: ActivityDao
) : ActivityRoomDatasource {

    override suspend fun addAll(list: List<ActivityRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            activityDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            activityDao.deleteAll()
        }

    override suspend fun listByIdList(idList: List<Int>): Result<List<ActivityRoomModel>> =
        result(getClassAndMethod()) {
            activityDao.listByIdList(idList)
        }

    override suspend fun getById(id: Int): Result<ActivityRoomModel> =
        result(getClassAndMethod()) {
            activityDao.getById(id)
        }

}