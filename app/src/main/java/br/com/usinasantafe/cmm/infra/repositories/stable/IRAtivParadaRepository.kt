package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.RAtivParadaRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RAtivParadaRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RAtivParadaRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IRAtivParadaRepository @Inject constructor(
    private val rAtivParadaRetrofitDatasource: RAtivParadaRetrofitDatasource, // Injeção da datasource Retrofit
    private val rAtivParadaRoomDatasource: RAtivParadaRoomDatasource // Injeção da datasource Room
) : RAtivParadaRepository {

    override suspend fun addAll(list: List<RAtivParada>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = rAtivParadaRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRAtivParadaRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IRAtivParadaRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rAtivParadaRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IRAtivParadaRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<RAtivParada>> {
        try {
            val result = rAtivParadaRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRAtivParadaRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRAtivParadaRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
