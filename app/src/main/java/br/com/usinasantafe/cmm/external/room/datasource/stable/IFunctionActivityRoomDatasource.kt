package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IFunctionActivityRoomDatasource @Inject constructor(
    private val functionActivityDao: FunctionActivityDao
) : FunctionActivityRoomDatasource {

    override suspend fun addAll(list: List<FunctionActivityRoomModel>): Result<Boolean> {
        try {
            functionActivityDao.insertAll(list)
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
            functionActivityDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listById(idActivity: Int): Result<List<FunctionActivityRoomModel>> {
        try {
            val list = functionActivityDao.listByIdActivity(idActivity)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun hasByIdAndType(
        idActivity: Int,
        typeActivity: TypeActivity
    ): Result<Boolean> {
        try {
            val result = functionActivityDao.hasByIdAndType(idActivity, typeActivity)
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}
