package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.NozzleDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.NozzleRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class INozzleRoomDatasource @Inject constructor(
    private val nozzleDao: NozzleDao
): NozzleRoomDatasource {

    override suspend fun addAll(list: List<NozzleRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            nozzleDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            nozzleDao.deleteAll()
        }

    override suspend fun listAll(): Result<List<NozzleRoomModel>> =
        result(getClassAndMethod()) {
            nozzleDao.allOrderByCod()
        }

}