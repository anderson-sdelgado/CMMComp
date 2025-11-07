package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.TypeItemMenu

interface ItemMenuRoomDatasource {
    suspend fun addAll(list: List<ItemMenuRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByTypeList(typeList: List<Pair<Int, String>>): Result<List<ItemMenuRoomModel>>
}