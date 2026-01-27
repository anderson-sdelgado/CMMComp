package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.FunctionStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IFunctionStopRoomDatasource @Inject constructor(
    private val functionStopDao: FunctionStopDao
): FunctionStopRoomDatasource {

    override suspend fun addAll(list: List<FunctionStopRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            functionStopDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            functionStopDao.deleteAll()
        }

    override suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?> =
        result(getClassAndMethod()) {
            functionStopDao.getIdStopByType(typeStop)
        }
}