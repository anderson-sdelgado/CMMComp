package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemRespCheckListRoomDatasource {
    suspend fun save(itemRespCheckListRoomModel: ItemRespCheckListRoomModel): EmptyResult
    suspend fun listByIdHeader(idHeader: Int): Result<List<ItemRespCheckListRoomModel>>
    suspend fun setIdServById(id: Int, idServ: Int): EmptyResult
}