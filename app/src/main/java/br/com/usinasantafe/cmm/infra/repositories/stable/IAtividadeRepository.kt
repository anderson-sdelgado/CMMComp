package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Atividade
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.AtividadeRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.AtividadeRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.AtividadeRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import javax.inject.Inject

class IAtividadeRepository @Inject constructor(
    private val atividadeRetrofitDatasource: AtividadeRetrofitDatasource,
    private val atividadeRoomDatasource: AtividadeRoomDatasource
) : AtividadeRepository {

    override suspend fun addAll(list: List<Atividade>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = atividadeRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IAtividadeRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IAtividadeRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = atividadeRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IAtividadeRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<Atividade>> {
        try {
            val result = atividadeRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IAtividadeRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IAtividadeRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

}