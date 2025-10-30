package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel

interface FunctionActivityRoomDatasource {
    suspend fun addAll(list: List<FunctionActivityRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdActivity(idActivity: Int): Result<List<FunctionActivityRoomModel>>
}
