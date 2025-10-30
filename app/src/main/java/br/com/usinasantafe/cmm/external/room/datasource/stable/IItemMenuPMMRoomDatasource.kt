package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuPMMDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuPMMRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
import br.com.usinasantafe.cmm.utils.FunctionItemMenu
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMenuPMMRoomDatasource @Inject constructor(
    private val itemMenuPMMDao: ItemMenuPMMDao
): ItemMenuPMMRoomDatasource {

    override suspend fun addAll(list: List<ItemMenuPMMRoomModel>): Result<Boolean> {
        try {
            itemMenuPMMDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            itemMenuPMMDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByTypeList(typeList: List<FunctionItemMenu>): Result<List<ItemMenuPMMRoomModel>> {
        try {
            val list = itemMenuPMMDao.listByTypeList(typeList)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}