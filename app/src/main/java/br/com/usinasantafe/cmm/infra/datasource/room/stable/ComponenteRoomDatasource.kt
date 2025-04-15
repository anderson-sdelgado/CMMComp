package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ComponenteRoomModel

interface ComponenteRoomDatasource {
    suspend fun addAll(list: List<ComponenteRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
