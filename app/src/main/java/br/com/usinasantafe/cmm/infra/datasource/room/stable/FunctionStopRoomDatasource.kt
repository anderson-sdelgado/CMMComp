package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel

interface FunctionStopRoomDatasource {
    suspend fun addAll(list: List<FunctionStopRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}