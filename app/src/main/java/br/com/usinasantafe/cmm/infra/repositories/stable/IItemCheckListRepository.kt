package br.com.usinasantafe.cmm.infra.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.domain.repositories.stable.ItemCheckListRepository
import br.com.usinasantafe.cmm.infra.datasource.retrofit.stable.ItemCheckListRetrofitDatasource // Import da datasource Retrofit
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemCheckListRoomDatasource // Import da datasource Room
import br.com.usinasantafe.cmm.infra.models.retrofit.stable.retrofitModelToEntity // Import da função de mapeamento Retrofit -> Entidade
import br.com.usinasantafe.cmm.infra.models.room.stable.entityItemCheckListToRoomModel
import br.com.usinasantafe.cmm.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.getClassAndMethod
import br.com.usinasantafe.cmm.utils.call
import javax.inject.Inject

class IItemCheckListRepository @Inject constructor(
    private val itemCheckListRetrofitDatasource: ItemCheckListRetrofitDatasource,
    private val itemCheckListRoomDatasource: ItemCheckListRoomDatasource
) : ItemCheckListRepository {

    override suspend fun addAll(list: List<ItemCheckList>): EmptyResult =
        call(getClassAndMethod()) {
            val roomModelList = list.map { it.entityItemCheckListToRoomModel() }
            itemCheckListRoomDatasource.addAll(roomModelList).getOrThrow()
        }

    override suspend fun deleteAll(): EmptyResult =
        call(getClassAndMethod()) {
            itemCheckListRoomDatasource.deleteAll().getOrThrow()
        }

    override suspend fun listByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<List<ItemCheckList>> =
        call(getClassAndMethod()) {
            val modelList = itemCheckListRetrofitDatasource.listByNroEquip(token, nroEquip).getOrThrow()
            modelList.map { it.retrofitModelToEntity() }
        }

    override suspend fun checkUpdateByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<Boolean> =
        call(getClassAndMethod()) {
            val model = itemCheckListRetrofitDatasource.checkUpdateByNroEquip(token, nroEquip).getOrThrow()
            model.qtd > 0
        }

    override suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckList>> =
        call(getClassAndMethod()) {
            val modelList = itemCheckListRoomDatasource.listByIdCheckList(idCheckList).getOrThrow()
            modelList.map { it.roomModelToEntity() }
        }

    override suspend fun countByIdCheckList(idCheckList: Int): Result<Int> =
        call(getClassAndMethod()) {
            itemCheckListRoomDatasource.countByIdCheckList(idCheckList).getOrThrow()
        }

}
