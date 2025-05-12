package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel

interface ActivityRoomDatasource {
    suspend fun addAll(list: List<ActivityRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}