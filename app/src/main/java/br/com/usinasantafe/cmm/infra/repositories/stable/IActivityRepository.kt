package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRepository @Inject constructor(
    private val activityRetrofitDatasource: ActivityRetrofitDatasource,
    private val activityRoomDatasource: ActivityRoomDatasource
) : ActivityRepository {

    override suspend fun addAll(list: List<Activity>): EmptyResult {
        return runCatching {
            val roomModelList = list.map { it.entityActivityToRoomModel() }
            activityRoomDatasource.addAll(roomModelList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun deleteAll(): EmptyResult {
        return runCatching {
            activityRoomDatasource.deleteAll().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Activity>> {
        return runCatching {
            val modelList = activityRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listByIdList(idList: List<Int>): Result<List<Activity>> {
        return runCatching {
            val roomModelList = activityRoomDatasource.listByIdList(idList).getOrThrow()
            roomModelList.map { it.roomModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getById(id: Int): Result<Activity> {
        return runCatching {
            activityRoomDatasource.getById(id).getOrThrow().roomModelToEntity()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}