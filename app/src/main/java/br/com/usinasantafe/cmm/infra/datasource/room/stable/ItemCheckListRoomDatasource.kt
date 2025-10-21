package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel

interface ItemCheckListRoomDatasource {
    suspend fun addAll(list: List<ItemCheckListRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckListRoomModel>>
    suspend fun countByIdCheckList(idCheckList: Int): Result<Int>
}
