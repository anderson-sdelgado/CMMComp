package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemOSMecanDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemOSMecanRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMecanRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemOSMecanRoomDatasource @Inject constructor(
    private val itemOSMecanDao: ItemOSMecanDao
) : ItemOSMecanRoomDatasource {
    override suspend fun addAll(list: List<ItemOSMecanRoomModel>): Result<Boolean> {
        try {
            itemOSMecanDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            itemOSMecanDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
