package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.BocalDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.BocalRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.BocalRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IBocalRoomDatasource @Inject constructor(
    private val bocalDao: BocalDao
) : BocalRoomDatasource {
    override suspend fun addAll(list: List<BocalRoomModel>): Result<Boolean> {
        try {
            bocalDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            bocalDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
