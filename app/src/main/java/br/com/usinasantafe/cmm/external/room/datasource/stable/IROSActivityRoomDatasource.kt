package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ROSActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ROSActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IROSActivityRoomDatasource @Inject constructor(
    private val rOSActivityDao: ROSActivityDao
) : ROSActivityRoomDatasource {

    override suspend fun addAll(list: List<ROSActivityRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            rOSActivityDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            rOSActivityDao.deleteAll()
        }

    override suspend fun listByIdOS(idOS: Int): Result<List<ROSActivityRoomModel>> =
        result(getClassAndMethod()) {
            rOSActivityDao.listByIdOS(idOS)
        }
}
