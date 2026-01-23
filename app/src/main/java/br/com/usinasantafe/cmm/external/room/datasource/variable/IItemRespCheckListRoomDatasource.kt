package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.lib.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.variable.ItemRespCheckListDao
import br.com.usinasantafe.cmm.infra.datasource.room.variable.ItemRespCheckListRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemRespCheckListRoomDatasource @Inject constructor(
    private val itemRespCheckListDao: ItemRespCheckListDao
): ItemRespCheckListRoomDatasource {

    override suspend fun save(itemRespCheckListRoomModel: ItemRespCheckListRoomModel): Result<Boolean> {
        try {
            itemRespCheckListDao.insert(itemRespCheckListRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<ItemRespCheckListRoomModel>> {
        try {
            val list = itemRespCheckListDao.listByIdHeader(idHeader)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setIdServById(
        id: Int,
        idServ: Int
    ): Result<Boolean> {
        try {
            val model = itemRespCheckListDao.getById(id)
            model.idServ = idServ
            itemRespCheckListDao.update(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}