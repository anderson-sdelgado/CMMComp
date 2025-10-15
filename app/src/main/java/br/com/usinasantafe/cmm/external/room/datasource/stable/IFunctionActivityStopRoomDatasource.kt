package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.RFuncaoAtivParadaDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RFuncaoAtivParadaRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityStopRoomDatasource @Inject constructor(
    private val rFuncaoAtivParadaDao: RFuncaoAtivParadaDao
) : FunctionActivityStopRoomDatasource {
    override suspend fun addAll(list: List<RFuncaoAtivParadaRoomModel>): Result<Boolean> {
        try {
            rFuncaoAtivParadaDao.insertAll(list)
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
            rFuncaoAtivParadaDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
