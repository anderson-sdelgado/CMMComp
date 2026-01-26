package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionStopRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityFunctionStopToRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionStopRepository @Inject constructor(
    private val functionStopRetrofitDatasource: FunctionStopRetrofitDatasource,
    private val functionStopRoomDatasource: FunctionStopRoomDatasource
): FunctionStopRepository {

    override suspend fun addAll(list: List<FunctionStop>): EmptyResult {
        return runCatching {
            val roomModelList = list.map { it.entityFunctionStopToRoomModel() }
            functionStopRoomDatasource.addAll(roomModelList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun deleteAll(): EmptyResult {
        return runCatching {
            functionStopRoomDatasource.deleteAll().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listAll(token: String): Result<List<FunctionStop>> {
        return runCatching {
            val modelList = functionStopRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> {
        return runCatching {
            functionStopRoomDatasource.getIdStopByType(typeStop).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

}