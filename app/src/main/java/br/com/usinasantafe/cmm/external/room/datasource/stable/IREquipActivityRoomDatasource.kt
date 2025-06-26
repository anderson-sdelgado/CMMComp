package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import javax.inject.Inject

class IREquipActivityRoomDatasource @Inject constructor(
    private val rEquipActivityDao: REquipActivityDao
) : REquipActivityRoomDatasource {
    override suspend fun addAll(list: List<REquipActivityRoomModel>): Result<Boolean> {
        try {
            rEquipActivityDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipActivityRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rEquipActivityDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipActivityRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun listByIdEquip(idEquip: Int): Result<List<REquipActivityRoomModel>> {
        try {
            val list = rEquipActivityDao.listByIdEquip(idEquip)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipActivityRoomDatasource.getListByIdEquip",
                message = "-",
                cause = e
            )
        }
    }
}
