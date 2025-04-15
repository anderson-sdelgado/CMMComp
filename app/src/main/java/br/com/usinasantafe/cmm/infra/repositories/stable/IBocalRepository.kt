package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Bocal
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.BocalRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.BocalRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.BocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import javax.inject.Inject

class IBocalRepository @Inject constructor(
    private val bocalRetrofitDatasource: BocalRetrofitDatasource,
    private val bocalRoomDatasource: BocalRoomDatasource
) : BocalRepository {

    override suspend fun addAll(list: List<Bocal>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = bocalRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IBocalRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IBocalRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = bocalRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IBocalRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<Bocal>> {
        try {
            val result = bocalRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IBocalRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IBocalRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
