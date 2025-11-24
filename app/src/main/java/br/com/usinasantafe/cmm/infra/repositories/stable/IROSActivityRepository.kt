package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityROSActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IROSActivityRepository @Inject constructor(
    private val rOSActivityRetrofitDatasource: ROSActivityRetrofitDatasource,
    private val rOSActivityRoomDatasource: ROSActivityRoomDatasource
) : ROSActivityRepository {

    override suspend fun addAll(list: List<ROSActivity>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityROSActivityToRoomModel() }
            val result = rOSActivityRoomDatasource.addAll(roomModelList)
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
        val result = rOSActivityRoomDatasource.deleteAll()
        result.onFailure { return Result.failure(it) }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<ROSActivity>> {
        try {
            val result = rOSActivityRetrofitDatasource.listAll(token)
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

    override suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivity>> {
        try {
            val result = rOSActivityRetrofitDatasource.listByNroOS(
                token = token,
                nroOS = nroOS
            )
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

    override suspend fun listByIdOS(idOS: Int): Result<List<ROSActivity>> {
        try {
            val result = rOSActivityRoomDatasource.listByIdOS(idOS)
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
