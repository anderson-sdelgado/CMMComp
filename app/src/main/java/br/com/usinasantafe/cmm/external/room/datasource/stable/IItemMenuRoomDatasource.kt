package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.domain.errors.resultFailure
import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import javax.inject.Inject

class IItemMenuRoomDatasource @Inject constructor(
    private val itemMenuDao: ItemMenuDao
): ItemMenuRoomDatasource {

    override suspend fun addAll(list: List<ItemMenuRoomModel>): Result<Boolean> {
        try {
            itemMenuDao.insertAll(list)
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
            itemMenuDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun listByTypeList(typeList: List<Pair<Int, String>>): Result<List<ItemMenuRoomModel>> {
        try {
            val idTypeList = typeList.map { it.first }
            val list = itemMenuDao.listByIdTypeList(idTypeList)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}