package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import javax.inject.Inject

class IColabRoomDatasource @Inject constructor(
    private val colabDao: ColabDao
) : ColabRoomDatasource {
    override suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean> {
        try {
            colabDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            colabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun checkByReg(reg: Int): Result<Boolean> {
        try {
            val result = colabDao.checkReg(reg) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = "IColabRoomDatasource.checkReg",
                message = "-",
                cause = e
            )
        }
    }
}
