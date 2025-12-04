package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityFunctionStopToRoomModel
import br.com.usinasantafe.cmm.lib.TypeStop
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
        val result = functionStopRoomDatasource.deleteAll()
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

    override suspend fun listAll(token: String): Result<List<FunctionStop>> {
        try {
            val result = functionStopRetrofitDatasource.listAll(token)
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

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> {
        val result = functionStopRoomDatasource.getIdStopByType(typeStop)
        result.onFailure {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = it
                )
            }
        return result
    }

}