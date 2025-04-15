package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turno
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnoRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnoRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import javax.inject.Inject

class ITurnoRepository @Inject constructor(
    private val turnoRetrofitDatasource: TurnoRetrofitDatasource,
    private val turnoRoomDatasource: TurnoRoomDatasource
) : TurnoRepository {

    override suspend fun addAll(list: List<Turno>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = turnoRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITurnoRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "ITurnoRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = turnoRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "ITurnoRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun recoverAll(
        token: String
    ): Result<List<Turno>> {
        try {
            val result = turnoRetrofitDatasource.recoverAll(token)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "ITurnoRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "ITurnoRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }
}
