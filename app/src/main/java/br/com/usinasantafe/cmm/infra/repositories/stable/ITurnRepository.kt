package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.TurnRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.TurnoRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.TurnRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityTurnToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ITurnRepository @Inject constructor(
    private val turnoRetrofitDatasource: TurnoRetrofitDatasource,
    private val turnRoomDatasource: TurnRoomDatasource
) : TurnRepository {

    override suspend fun addAll(list: List<Turn>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityTurnToRoomModel() }
            val result = turnRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = turnRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(
        token: String
    ): Result<List<Turn>> {
        try {
            val result = turnoRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>> {
        try {
            val result = turnRoomDatasource.listByCodTurnEquip(codTurnEquip)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getNroTurnByIdTurn(idTurn: Int): Result<Int> {
        val result = turnRoomDatasource.getNroTurnByIdTurn(idTurn)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }
}
