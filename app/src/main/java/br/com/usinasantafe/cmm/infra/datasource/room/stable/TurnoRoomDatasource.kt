package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.TurnoRoomModel

interface TurnoRoomDatasource {
    suspend fun addAll(list: List<TurnoRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
