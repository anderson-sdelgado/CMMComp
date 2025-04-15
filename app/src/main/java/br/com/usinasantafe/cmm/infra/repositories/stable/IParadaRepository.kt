package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Parada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.ParadaRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ParadaRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ParadaRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IParadaRepository @Inject constructor(
    private val paradaRetrofitDatasource: ParadaRetrofitDatasource, // Injeção da datasource Retrofit
    private val paradaRoomDatasource: ParadaRoomDatasource // Injeção da datasource Room
) : ParadaRepository {

    override suspend fun addAll(list: List<Parada>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = paradaRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IParadaRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IParadaRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = paradaRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IParadaRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<Parada>> {
        try {
            val result = paradaRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IParadaRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IParadaRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
