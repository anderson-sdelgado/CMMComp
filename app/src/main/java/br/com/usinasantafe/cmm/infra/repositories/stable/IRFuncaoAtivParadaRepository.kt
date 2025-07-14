package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.FunctionActivityStopRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.RFuncaoAtivParadaRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityStopRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import javax.inject.Inject

class IRFuncaoAtivParadaRepository @Inject constructor(
    private val rFuncaoAtivParadaRetrofitDatasource: RFuncaoAtivParadaRetrofitDatasource, // Injeção da datasource Retrofit
    private val functionActivityStopRoomDatasource: FunctionActivityStopRoomDatasource // Injeção da datasource Room
) : FunctionActivityStopRepository {

    override suspend fun addAll(list: List<RFuncaoAtivParada>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = functionActivityStopRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRFuncaoAtivParadaRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IRFuncaoAtivParadaRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = functionActivityStopRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IRFuncaoAtivParadaRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<RFuncaoAtivParada>> {
        try {
            val result = rFuncaoAtivParadaRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IRFuncaoAtivParadaRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRFuncaoAtivParadaRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
