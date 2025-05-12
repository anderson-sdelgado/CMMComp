package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.EquipDao
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

    override suspend fun getByIdEquip(id: Int): Result<EquipRoomModel> {
        try {
            val model = equipDao.getByIdEquip(id)
            return Result.success(model)
        } catch (e: Exception){
            return resultFailure(
                context = "IEquipRoomDatasource.getByIdEquip",
                message = "-",
                cause = e
            )
        }
    }

}