package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel

interface OSRoomDatasource {
    suspend fun addAll(list: List<OSRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun checkNroOS(nroOS: Int): Result<Boolean>
    suspend fun add(model: OSRoomModel): Result<Boolean>
}
