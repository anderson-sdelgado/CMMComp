package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.REquipActivityDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.REquipActivityRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IREquipActivityRoomDatasource @Inject constructor(
    private val rEquipActivityDao: REquipActivityDao
) : REquipActivityRoomDatasource {

    override suspend fun addAll(list: List<REquipActivityRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            rEquipActivityDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            rEquipActivityDao.deleteAll()
        }

    override suspend fun listByIdEquip(idEquip: Int): Result<List<REquipActivityRoomModel>> =
        result(getClassAndMethod()) {
            rEquipActivityDao.listByIdEquip(idEquip)
        }

}
