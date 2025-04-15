package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.ROSAtivDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSAtivRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSAtivRoomModel
import javax.inject.Inject

class IROSAtivRoomDatasource @Inject constructor(
    private val rosAtivDao: ROSAtivDao
) : ROSAtivRoomDatasource {
    override suspend fun addAll(list: List<ROSAtivRoomModel>): Result<Boolean> {
        try {
            rosAtivDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSAtivRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rosAtivDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IROSAtivRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
