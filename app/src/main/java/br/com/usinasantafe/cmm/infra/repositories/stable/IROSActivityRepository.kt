package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ROSActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ROSActivityRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import javax.inject.Inject

class IROSActivityRepository @Inject constructor(
    private val rOSActivityRetrofitDatasource: ROSActivityRetrofitDatasource,
    private val rOSActivityRoomDatasource: ROSActivityRoomDatasource
) : ROSActivityRepository {

    override suspend fun addAll(list: List<ROSActivity>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = rOSActivityRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IROSActivityRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IROSActivityRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rOSActivityRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IROSActivityRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<ROSActivity>> {
        try {
            val result = rOSActivityRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IROSActivityRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRepository.recoverAll",
                message = "-",
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
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IROSActivityRepository.getListByNroOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRepository.getListByNroOS",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByIdOS(idOS: Int): Result<List<ROSActivity>> {
        try {
            val result = rOSActivityRoomDatasource.listByIdOS(idOS)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IROSActivityRepository.listByIdOS",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSActivityRepository.listByIdOS",
                message = "-",
                cause = e
            )
        }
    }
}
