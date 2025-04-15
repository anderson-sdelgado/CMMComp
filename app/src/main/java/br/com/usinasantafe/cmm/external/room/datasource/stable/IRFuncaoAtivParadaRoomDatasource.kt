package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.RFuncaoAtivParadaDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RFuncaoAtivParadaRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RFuncaoAtivParadaRoomModel
import javax.inject.Inject

class IRFuncaoAtivParadaRoomDatasource @Inject constructor(
    private val rFuncaoAtivParadaDao: RFuncaoAtivParadaDao
) : RFuncaoAtivParadaRoomDatasource {
    override suspend fun addAll(list: List<RFuncaoAtivParadaRoomModel>): Result<Boolean> {
        try {
            rFuncaoAtivParadaDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRFuncaoAtivParadaRoomDatasource.addAll",
                message = "-",
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rFuncaoAtivParadaDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = "IRFuncaoAtivParadaRoomDatasource.deleteAll",
                message = "-",
                cause = e
            )
        }
    }
}
