package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel

interface ROSActivityRoomDatasource {
    suspend fun addAll(list: List<ROSActivityRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdOS(idOS: Int): Result<List<ROSActivityRoomModel>>
}
