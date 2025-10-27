package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Bocal
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.BocalRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.BocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.BocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityBocalToRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IBocalRepository @Inject constructor(
    private val bocalRetrofitDatasource: BocalRetrofitDatasource,
    private val bocalRoomDatasource: BocalRoomDatasource
) : BocalRepository {

    override suspend fun addAll(list: List<Bocal>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityBocalToRoomModel() }
            val result = bocalRoomDatasource.addAll(roomModelList)
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
        val result = bocalRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Bocal>> {
        try {
            val result = bocalRetrofitDatasource.listAll(token)
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
}
