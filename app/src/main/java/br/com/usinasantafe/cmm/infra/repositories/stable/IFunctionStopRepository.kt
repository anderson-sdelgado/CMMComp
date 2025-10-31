package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityFunctionStopToRoomModel
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionStopRepository @Inject constructor(
    private val functionStopRetrofitDatasource: FunctionStopRetrofitDatasource,
    private val functionStopRoomDatasource: FunctionStopRoomDatasource
): FunctionStopRepository {
    override suspend fun addAll(list: List<FunctionStop>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityFunctionStopToRoomModel() }
            val result = functionStopRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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
        val result = functionStopRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<FunctionStop>> {
        try {
            val result = functionStopRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
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

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> {
        val result = functionStopRoomDatasource.getIdStopByType(typeStop)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}