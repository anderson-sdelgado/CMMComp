package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityFunctionActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityRepository @Inject constructor(
    private val functionActivityRetrofitDatasource: FunctionActivityRetrofitDatasource,
    private val functionActivityRoomDatasource: FunctionActivityRoomDatasource
): FunctionActivityRepository {
    override suspend fun addAll(list: List<FunctionActivity>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityFunctionActivityToRoomModel() }
            val result = functionActivityRoomDatasource.addAll(roomModelList)
            result.onFailure { return Result.failure(it) }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = functionActivityRoomDatasource.deleteAll()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun listAll(token: String): Result<List<FunctionActivity>> {
        try {
            val result = functionActivityRetrofitDatasource.listAll(token)
            result.onFailure { return Result.failure(it) }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdActivity(idActivity: Int): Result<List<FunctionActivity>> {
        try {
            val result = functionActivityRoomDatasource.listByIdActivity(idActivity)
            result.onFailure { return Result.failure(it) }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}