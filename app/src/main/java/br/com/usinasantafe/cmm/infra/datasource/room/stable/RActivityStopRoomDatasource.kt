package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel

interface RActivityStopRoomDatasource {
    suspend fun addAll(list: List<RActivityStopRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
