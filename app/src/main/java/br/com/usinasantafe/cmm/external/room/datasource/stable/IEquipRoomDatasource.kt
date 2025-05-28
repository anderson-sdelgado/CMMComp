package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.EquipDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import javax.inject.Inject

class IEquipRoomDatasource @Inject constructor(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun getDescrByIdEquip(id: Int): Result<String> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success("${model.nroEquip} - ${model.descrClass}")
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getDescrByIdEquip",
                message = "-",
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

    override suspend fun getMeasureByIdEquip(id: Int): Result<Double> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model.measurement)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getMeasureByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun updateMeasureByIdEquip(
        measure: Double,
        idEquip: Int
    ): Result<Boolean> {
        try {
            val model = equipDao.getByIdEquip(idEquip)
            model.measurement = measure
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

}