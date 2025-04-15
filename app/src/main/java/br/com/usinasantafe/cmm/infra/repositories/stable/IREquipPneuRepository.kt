package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipPneuRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipPneuRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipPneuRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IREquipPneuRepository @Inject constructor(
    private val rEquipPneuRetrofitDatasource: REquipPneuRetrofitDatasource, // Injeção da datasource Retrofit
    private val rEquipPneuRoomDatasource: REquipPneuRoomDatasource // Injeção da datasource Room
) : REquipPneuRepository {

    override suspend fun addAll(list: List<REquipPneu>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = rEquipPneuRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IREquipPneuRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IREquipPneuRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rEquipPneuRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IREquipPneuRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<REquipPneu>> {
        try {
            val result = rEquipPneuRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IREquipPneuRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipPneuRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
