package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IActivityRepository @Inject constructor(
    private val activityRetrofitDatasource: ActivityRetrofitDatasource,
    private val activityRoomDatasource: ActivityRoomDatasource
) : ActivityRepository {

    override suspend fun addAll(list: List<Activity>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityActivityToRoomModel() }
            val result = activityRoomDatasource.addAll(roomModelList)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = activityRoomDatasource.deleteAll()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Activity>> {
        try {
            val result = activityRetrofitDatasource.listAll(token)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdList(idList: List<Int>): Result<List<Activity>> {
        try {
            val result = activityRoomDatasource.listByIdList(idList)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            val roomModelList = result.getOrNull()!!
            val entityList = roomModelList.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getById(id: Int): Result<Activity> {
        try {
            val result = activityRoomDatasource.getById(id)
            result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
            return Result.success(result.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}