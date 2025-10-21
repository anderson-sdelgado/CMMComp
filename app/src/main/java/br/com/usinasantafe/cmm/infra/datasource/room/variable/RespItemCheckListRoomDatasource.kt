package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.RespItemCheckListRoomModel

interface RespItemCheckListRoomDatasource {
    suspend fun save(respItemCheckListRoomModel: RespItemCheckListRoomModel): Result<Boolean>
}