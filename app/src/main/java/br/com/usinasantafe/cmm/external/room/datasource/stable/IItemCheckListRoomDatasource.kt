package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import javax.inject.Inject

class IItemCheckListRoomDatasource @Inject constructor(
    private val itemCheckListDao: ItemCheckListDao
) : ItemCheckListRoomDatasource {
    override suspend fun addAll(list: List<ItemCheckListRoomModel>): Result<Boolean> {
        try {
            itemCheckListDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemCheckListRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            itemCheckListDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IItemCheckListRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
