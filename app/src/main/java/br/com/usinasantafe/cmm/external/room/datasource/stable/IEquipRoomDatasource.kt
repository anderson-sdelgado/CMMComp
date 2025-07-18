package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IEquipRoomDatasource @Inject constructor(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getDescrByIdEquip(id: Int): Result<String> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success("${model.nroEquip} - ${model.descrClass}")
        } catch (e: Exception){
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getCodTurnEquipByIdEquip(id: Int): Result<Int> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model.codTurnEquip)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getCodTurnEquipByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getHourMeterByIdEquip(id: Int): Result<Double> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model.hourMeter)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getMeasureByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun updateHourMeterByIdEquip(
        hourMeter: Double,
        idEquip: Int
    ): Result<Boolean> {
        try {
            val model = equipDao.getByIdEquip(idEquip)
            model.hourMeter = hourMeter
            equipDao.update(model)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.updateMeasureByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getTypeFertByIdEquip(id: Int): Result<Int> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model.typeFert)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getTypeFertByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getIdCheckListByIdEquip(id: Int): Result<Int> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model.idCheckList)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getIdCheckListByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

}