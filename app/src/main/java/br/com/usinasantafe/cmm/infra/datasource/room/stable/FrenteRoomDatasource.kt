package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FrenteRoomModel

interface FrenteRoomDatasource {
    suspend fun addAll(list: List<FrenteRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
