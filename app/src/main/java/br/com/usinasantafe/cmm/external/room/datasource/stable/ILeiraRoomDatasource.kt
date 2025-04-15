package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.LeiraDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.LeiraRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.LeiraRoomModel
import javax.inject.Inject

class ILeiraRoomDatasource @Inject constructor(
    private val leiraDao: LeiraDao
) : LeiraRoomDatasource {
    override suspend fun addAll(list: List<LeiraRoomModel>): Result<Boolean> {
        try {
            leiraDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILeiraRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            leiraDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "ILeiraRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
