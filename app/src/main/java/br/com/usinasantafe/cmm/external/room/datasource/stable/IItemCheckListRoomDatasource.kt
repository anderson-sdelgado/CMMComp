package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ItemCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemCheckListRoomDatasource @Inject constructor(
    private val itemCheckListDao: ItemCheckListDao
) : ItemCheckListRoomDatasource {
    override suspend fun addAll(list: List<ItemCheckListRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            itemCheckListDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            itemCheckListDao.deleteAll()
        }

    override suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckListRoomModel>> =
        result(getClassAndMethod()) {
            itemCheckListDao.listByIdCheckList(idCheckList)
        }

    override suspend fun countByIdCheckList(idCheckList: Int): Result<Int> =
        result(getClassAndMethod()) {
            itemCheckListDao.countByIdCheckList(idCheckList)
        }
}
