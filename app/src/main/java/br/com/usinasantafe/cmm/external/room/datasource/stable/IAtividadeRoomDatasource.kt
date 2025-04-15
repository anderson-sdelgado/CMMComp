package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.AtividadeDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.AtividadeRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.AtividadeRoomModel
import javax.inject.Inject

class IAtividadeRoomDatasource @Inject constructor(
    private val atividadeDao: AtividadeDao
) : AtividadeRoomDatasource {
    override suspend fun addAll(list: List<AtividadeRoomModel>): Result<Boolean> {
        try {
            atividadeDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IAtividadeRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            atividadeDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IAtividadeRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}