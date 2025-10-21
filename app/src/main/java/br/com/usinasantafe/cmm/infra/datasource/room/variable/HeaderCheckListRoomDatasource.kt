package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel

interface HeaderCheckListRoomDatasource {
    suspend fun save(headerCheckListRoomModel: HeaderCheckListRoomModel): Result<Long>
}