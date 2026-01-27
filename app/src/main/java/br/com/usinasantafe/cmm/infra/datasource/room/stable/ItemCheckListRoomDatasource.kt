package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemCheckListRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemCheckListRoomDatasource {
    suspend fun addAll(list: List<ItemCheckListRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdCheckList(idCheckList: Int): Result<List<ItemCheckListRoomModel>>
    suspend fun countByIdCheckList(idCheckList: Int): Result<Int>
}
