package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IFunctionActivityRoomDatasource @Inject constructor(
    private val functionActivityDao: FunctionActivityDao
) : FunctionActivityRoomDatasource {

    override suspend fun addAll(list: List<FunctionActivityRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            functionActivityDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            functionActivityDao.deleteAll()
        }

    override suspend fun listById(idActivity: Int): Result<List<FunctionActivityRoomModel>> =
        result(getClassAndMethod()) {
            functionActivityDao.listByIdActivity(idActivity)
        }

    override suspend fun hasByIdAndType(
        idActivity: Int,
        typeActivity: TypeActivity
    ): Result<Boolean> =
        result(getClassAndMethod()) {
            functionActivityDao.hasByIdAndType(idActivity, typeActivity)
        }
}
