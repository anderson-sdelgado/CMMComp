package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip
import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityToRoomModel
import javax.inject.Inject

class IEquipRepository @Inject constructor(
    private val equipRetrofitDatasource: EquipRetrofitDatasource,
    private val equipRoomDatasource: EquipRoomDatasource
): EquipRepository {

    override suspend fun addAll(list: List<Equip>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = equipRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.addAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRepository.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = equipRoomDatasource.deleteAll()
        if (result.isFailure) {
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.deleteAll",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getListByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<Equip>> {
        try {
            val result = equipRetrofitDatasource.getListByIdEquip(
                token = token,
                idEquip = idEquip
            )
            if (result.isFailure) {
                val e = result.exceptionOrNull()!!
                return resultFailure(
                    context = "IEquipRepository.recoverAll",
                    message = e.message,
                    cause = e.cause
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = "IEquipRepository.recoverAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String> {
        val result = equipRoomDatasource.getDescrByIdEquip(idEquip)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.getDescrByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getCodTurnEquipByIdEquip(
        idEquip: Int
    ): Result<Int> {
        val result = equipRoomDatasource.getCodTurnEquipByIdEquip(idEquip)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.getCodTurnEquipByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getHourMeterByIdEquip(idEquip: Int): Result<Double> {
        val result = equipRoomDatasource.getHourMeterByIdEquip(idEquip)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.getMeasureByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun updateHourMeterByIdEquip(
        hourMeter: Double,
        idEquip: Int
    ): Result<Boolean> {
        val result = equipRoomDatasource.updateHourMeterByIdEquip(
            hourMeter = hourMeter,
            idEquip = idEquip
        )
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.updateMeasureByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getTypeFertByIdEquip(idEquip: Int): Result<Int> {
        val result = equipRoomDatasource.getTypeFertByIdEquip(idEquip)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.getTypeFertByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

    override suspend fun getIdCheckListByIdEquip(idEquip: Int): Result<Int> {
        val result = equipRoomDatasource.getIdCheckListByIdEquip(idEquip)
        if(result.isFailure){
            val e = result.exceptionOrNull()!!
            return resultFailure(
                context = "IEquipRepository.getIdCheckListByIdEquip",
                message = e.message,
                cause = e.cause
            )
        }
        return result
    }

}