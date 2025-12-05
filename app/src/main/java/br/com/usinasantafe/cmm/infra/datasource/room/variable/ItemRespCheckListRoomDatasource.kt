package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ItemRespCheckListRoomModel

interface ItemRespCheckListRoomDatasource {
    suspend fun save(itemRespCheckListRoomModel: ItemRespCheckListRoomModel): Result<Boolean>
    suspend fun listByIdHeader(idHeader: Int): Result<List<ItemRespCheckListRoomModel>>
    suspend fun setIdServById(id: Int, idServ: Int): Result<Boolean>
}