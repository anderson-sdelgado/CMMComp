package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.BocalRoomModel

interface BocalRoomDatasource {
    suspend fun addAll(list: List<BocalRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
