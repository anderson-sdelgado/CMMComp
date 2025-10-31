package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.utils.TypeStop
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionStopRoomDatasource @Inject constructor(
    private val functionStopDao: FunctionStopDao
): FunctionStopRoomDatasource {

    override suspend fun addAll(list: List<FunctionStopRoomModel>): Result<Boolean> {
        try {
            functionStopDao.insertAll(list)
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
            functionStopDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> {
        try {
            val idStop = functionStopDao.getIdStopByType(typeStop)
            return Result.success(idStop)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}