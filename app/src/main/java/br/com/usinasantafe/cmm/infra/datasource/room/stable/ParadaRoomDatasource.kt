package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ParadaRoomModel

interface ParadaRoomDatasource {
    suspend fun addAll(list: List<ParadaRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
