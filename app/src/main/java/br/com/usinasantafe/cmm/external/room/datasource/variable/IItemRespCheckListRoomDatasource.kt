package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.external.room.dao.variable.ItemRespCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemRespCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemRespCheckListRoomDatasource @Inject constructor(
    private val itemRespCheckListDao: ItemRespCheckListDao
): ItemRespCheckListRoomDatasource {

    override suspend fun save(itemRespCheckListRoomModel: ItemRespCheckListRoomModel): EmptyResult =
        result(getClassAndMethod()) {
            itemRespCheckListDao.insert(itemRespCheckListRoomModel)
        }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<ItemRespCheckListRoomModel>> =
        result(getClassAndMethod()) {
            itemRespCheckListDao.listByIdHeader(idHeader)
        }

    override suspend fun setIdServById(
        id: Int,
        idServ: Int
    ): EmptyResult =
        result(getClassAndMethod()) {
            val model = itemRespCheckListDao.getById(id)
            model.idServ = idServ
            itemRespCheckListDao.update(model)
        }

}