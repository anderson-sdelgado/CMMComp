package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.RAtivParadaDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RAtivParadaRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RAtivParadaRoomModel
import javax.inject.Inject

class IRAtivParadaRoomDatasource @Inject constructor(
    private val rAtivParadaDao: RAtivParadaDao
) : RAtivParadaRoomDatasource {
    override suspend fun addAll(list: List<RAtivParadaRoomModel>): Result<Boolean> {
        try {
            rAtivParadaDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRAtivParadaRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rAtivParadaDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRAtivParadaRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
