package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FunctionActivityRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityFunctionActivityToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityRepository @Inject constructor(
    private val functionActivityRetrofitDatasource: FunctionActivityRetrofitDatasource,
    private val functionActivityRoomDatasource: FunctionActivityRoomDatasource
): FunctionActivityRepository {

    override suspend fun addAll(list: List<FunctionActivity>): EmptyResult {
        return runCatching {
            val roomModelList = list.map { it.entityFunctionActivityToRoomModel() }
            functionActivityRoomDatasource.addAll(roomModelList).getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun deleteAll(): EmptyResult {
        return runCatching {
            functionActivityRoomDatasource.deleteAll().getOrThrow()
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listAll(token: String): Result<List<FunctionActivity>> {
        return runCatching {
            val modelList = functionActivityRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun listById(idActivity: Int): Result<List<FunctionActivity>> {
        return runCatching {
            val modelList = functionActivityRoomDatasource.listById(idActivity).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }

    override suspend fun hasByIdAndType(
        idActivity: Int,
        typeActivity: TypeActivity
    ): Result<Boolean> {
        return runCatching {
            functionActivityRoomDatasource.hasByIdAndType(idActivity, typeActivity).getOrThrow()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { resultFailure(context = getClassAndMethod(), cause = it) }
        )
    }
}