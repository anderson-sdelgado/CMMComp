package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.ServicoDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ServicoRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ServicoRoomModel
import javax.inject.Inject

class IServicoRoomDatasource @Inject constructor(
    private val servicoDao: ServicoDao
) : ServicoRoomDatasource {
    override suspend fun addAll(list: List<ServicoRoomModel>): Result<Boolean> {
        try {
            servicoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IServicoRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            servicoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IServicoRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
