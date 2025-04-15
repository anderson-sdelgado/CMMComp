package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Frente
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FrenteRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.FrenteRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FrenteRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IFrenteRepository @Inject constructor(
    private val frenteRetrofitDatasource: FrenteRetrofitDatasource, // Injeção da datasource Retrofit
    private val frenteRoomDatasource: FrenteRoomDatasource // Injeção da datasource Room
) : FrenteRepository {

    override suspend fun addAll(list: List<Frente>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = frenteRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IFrenteRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IFrenteRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = frenteRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IFrenteRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<Frente>> {
        try {
            val result = frenteRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IFrenteRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IFrenteRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
