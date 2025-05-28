package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.REquipPneuDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipPneuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipPneuRoomModel
import javax.inject.Inject

class IREquipPneuRoomDatasource @Inject constructor(
    private val rEquipPneuDao: REquipPneuDao
) : REquipPneuRoomDatasource {
    override suspend fun addAll(list: List<REquipPneuRoomModel>): Result<Boolean> {
        try {
            rEquipPneuDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipPneuRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rEquipPneuDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IREquipPneuRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
