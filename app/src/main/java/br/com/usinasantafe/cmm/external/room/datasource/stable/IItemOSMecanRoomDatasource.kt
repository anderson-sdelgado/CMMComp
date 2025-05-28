package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMecanDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMecanRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMecanRoomModel
import javax.inject.Inject

class IItemOSMecanRoomDatasource @Inject constructor(
    private val itemOSMecanDao: ItemOSMecanDao
) : ItemOSMecanRoomDatasource {
    override suspend fun addAll(list: List<ItemOSMecanRoomModel>): Result<Boolean> {
        try {
            itemOSMecanDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemOSMecanRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            itemOSMecanDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemOSMecanRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
