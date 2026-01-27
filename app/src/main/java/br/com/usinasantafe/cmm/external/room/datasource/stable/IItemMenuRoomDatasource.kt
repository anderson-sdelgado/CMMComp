package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.external.room.dao.stable.ItemMenuDao
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.PMM
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.result
import javax.inject.Inject

class IItemMenuRoomDatasource @Inject constructor(
    private val itemMenuDao: ItemMenuDao
): ItemMenuRoomDatasource {

    override suspend fun addAll(list: List<ItemMenuRoomModel>): EmptyResult =
        result(getClassAndMethod()) {
            itemMenuDao.insertAll(list)
        }

    override suspend fun deleteAll(): EmptyResult =
        result(getClassAndMethod()) {
            itemMenuDao.deleteAll()
        }

    override suspend fun listByTypeList(
        typeList: List<Pair<Int, String>>,
        app: Pair<Int, String>,
        checkMenu: Boolean
    ): Result<List<ItemMenuRoomModel>> =
        result(getClassAndMethod()) {
            if(app.second == PMM) {
                val idTypeList = typeList.map { it.first }
                val list = itemMenuDao.listByIdTypeListAndIdApp(idTypeList, app.first)
                return@result list
            }
            val list = itemMenuDao.listByIdApp(app.first)
            if(checkMenu) {
                val listMenu = list.filter { it.pos != 0 }
                return@result listMenu
            }
            return@result list
        }
}