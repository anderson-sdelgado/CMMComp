package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.PropriedadeDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.PropriedadeRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.PropriedadeRoomModel
import javax.inject.Inject

class IPropriedadeRoomDatasource @Inject constructor(
    private val propriedadeDao: PropriedadeDao
) : PropriedadeRoomDatasource {
    override suspend fun addAll(list: List<PropriedadeRoomModel>): Result<Boolean> {
        try {
            propriedadeDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IPropriedadeRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            propriedadeDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IPropriedadeRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
