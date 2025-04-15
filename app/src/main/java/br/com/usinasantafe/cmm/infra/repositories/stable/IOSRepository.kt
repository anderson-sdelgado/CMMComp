package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.OSRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.OSRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.OSRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IOSRepository @Inject constructor(
    private val osRetrofitDatasource: OSRetrofitDatasource, // Injeção da datasource Retrofit
    private val osRoomDatasource: OSRoomDatasource // Injeção da datasource Room
) : OSRepository {

    override suspend fun addAll(list: List<OS>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = osRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IOSRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IOSRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = osRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IOSRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<OS>> {
        try {
            val result = osRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IOSRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IOSRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
