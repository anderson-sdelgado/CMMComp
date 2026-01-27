package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.RActivityStopDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.RActivityStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IRActivityStopRoomDatasource @Inject constructor(
    private val rActivityStopDao: RActivityStopDao
) : RActivityStopRoomDatasource {

    override suspend fun addAll(list: List<RActivityStopRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            rActivityStopDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            rActivityStopDao.deleteAll()
        }

    override suspend fun listByIdActivity(idActivity: Int): Result<List<RActivityStopRoomModel>> =
        result(getClassAndMethod()) {
            rActivityStopDao.listByIdActivity(idActivity)
        }
}
