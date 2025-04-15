package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.LeiraRoomModel

interface LeiraRoomDatasource {
    suspend fun addAll(list: List<LeiraRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
