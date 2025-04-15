package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RAtivParadaRoomModel

interface RAtivParadaRoomDatasource {
    suspend fun addAll(list: List<RAtivParadaRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
