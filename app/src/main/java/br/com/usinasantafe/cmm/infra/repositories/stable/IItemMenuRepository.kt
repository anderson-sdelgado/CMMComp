package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemMenuRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemMenuRetrofitDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuRoomDatasource
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.cmm.infra.models.room.stable.entityItemMenuToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IItemMenuRepository  @Inject constructor(
    private val itemMenuRetrofitDatasource: ItemMenuRetrofitDatasource,
    private val itemMenuRoomDatasource: ItemMenuRoomDatasource
): ItemMenuRepository {

    override suspend fun addAll(list: List<ItemMenu>): EmptyResult =
        call(getClassAndMethod()) {
            val modelList = list.map { it.entityItemMenuToRoomModel() }
            itemMenuRoomDatasource.addAll(modelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            itemMenuRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listAll(token: String): Result<List<ItemMenu>> =
        call(getClassAndMethod()) {
            val modelList = itemMenuRetrofitDatasource.listAll(token).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun listByTypeList(
        typeList: List<Pair<Int, String>>
    ): Result<List<ItemMenu>> =
        call(getClassAndMethod()) {
            val modelList = itemMenuRoomDatasource.listByTypeList(typeList).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun listByApp(app: Pair<Int, String>): Result<List<ItemMenu>> =
        call(getClassAndMethod()) {
            val modelList = itemMenuRoomDatasource.listByTypeList(app = app).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

}