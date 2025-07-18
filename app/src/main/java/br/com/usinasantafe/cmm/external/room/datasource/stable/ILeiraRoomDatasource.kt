package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.domain.errors.resultFailureFinish
import br.com.usinasantafe.cmm.external.room.dao.stable.LeiraDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.LeiraRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.LeiraRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class ILeiraRoomDatasource @Inject constructor(
    private val leiraDao: LeiraDao
) : LeiraRoomDatasource {
    override suspend fun addAll(list: List<LeiraRoomModel>): Result<Boolean> {
        try {
            leiraDao.insertAll(list)
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
            leiraDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailureFinish(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
