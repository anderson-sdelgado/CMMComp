package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.REquipActivityRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.REquipActivityRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel // Import da função de mapeamento Entidade -> Room
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import javax.inject.Inject

class IREquipActivityRepository @Inject constructor(
    private val rEquipActivityRetrofitDatasource: REquipActivityRetrofitDatasource,
    private val rEquipActivityRoomDatasource: REquipActivityRoomDatasource
) : REquipActivityRepository {

    override suspend fun addAll(list: List<REquipActivity>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = rEquipActivityRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IREquipActivityRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IREquipActivityRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = rEquipActivityRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IREquipActivityRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivity>> {
        try {
            val result = rEquipActivityRetrofitDatasource.listByIdEquip(
                token = token,
                idEquip = idEquip
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IREquipActivityRepository.listByIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipActivityRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByIdEquip(idEquip: Int): Result<List<REquipActivity>> {
        try {
            val result = rEquipActivityRoomDatasource.listByIdEquip(idEquip)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IREquipActivityRepository.listByIdEquip",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipActivityRepository.getListByIdEquip",
                message = "-",
                cause = e
            )
        }
    }
}
