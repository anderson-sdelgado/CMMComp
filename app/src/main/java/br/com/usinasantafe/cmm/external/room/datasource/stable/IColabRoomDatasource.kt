package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ColabDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IColabRoomDatasource @Inject constructor(
    private val colabDao: ColabDao
) : ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            colabDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            colabDao.deleteAll()
        }

    override suspend fun checkByReg(reg: Int): Result<Boolean> =
        result(getClassAndMethod()) {
            colabDao.checkReg(reg) > 0
        }
}
