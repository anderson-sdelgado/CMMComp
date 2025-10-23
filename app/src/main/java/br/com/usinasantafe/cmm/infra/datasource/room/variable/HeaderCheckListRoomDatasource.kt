package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderCheckListRoomModel

interface HeaderCheckListRoomDatasource {
    suspend fun save(headerCheckListRoomModel: HeaderCheckListRoomModel): Result<Long>
    suspend fun checkSend(): Result<Boolean>
    suspend fun listBySend(): Result<List<HeaderCheckListRoomModel>>
    suspend fun setSentAndIdServById(
        id: Int,
        idServ: Int
    ): Result<Boolean>

}