package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.REquipAtivDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipAtivRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipAtivRoomModel
import javax.inject.Inject

class IREquipAtivRoomDatasource @Inject constructor(
    private val rEquipAtivDao: REquipAtivDao
) : REquipAtivRoomDatasource {
    override suspend fun addAll(list: List<REquipAtivRoomModel>): Result<Boolean> {
        try {
            rEquipAtivDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipAtivRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rEquipAtivDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipAtivRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
