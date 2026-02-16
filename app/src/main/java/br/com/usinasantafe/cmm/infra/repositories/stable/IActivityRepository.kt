package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IActivityRepository @Inject constructor(
    private val activityRetrofitDatasource: ActivityRetrofitDatasource,
    private val activityRoomDatasource: ActivityRoomDatasource
) : ActivityRepository {

    override suspend fun addAll(list: List<Activity>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityToRoomModel() }
            activityRoomDatasource.addAll(roomModelList).getOrThrow()
        }


    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            activityRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(
        token: String
    ): Result<List<Activity>> =
        call(getClassAndMethod()) {
            val modelList = activityRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByIdList(idList: List<Int>): Result<List<Activity>> =
        call(getClassAndMethod()) {
            val roomModelList = activityRoomDatasource.listByIdList(idList).getOrThrow()
            roomModelList.map { it.roomModelToEntity() }
        }

    override suspend fun getById(id: Int): Result<Activity> =
        call(getClassAndMethod()) {
            activityRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }

}