package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel

interface ColabRoomDatasource {
    suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
