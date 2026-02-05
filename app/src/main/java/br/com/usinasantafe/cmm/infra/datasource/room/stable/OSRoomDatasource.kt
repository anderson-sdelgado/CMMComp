package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface OSRoomDatasource {
    suspend fun addAll(list: List<OSRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun hasByNroOS(nroOS: Int): Result<Boolean>
    suspend fun add(model: OSRoomModel): EmptyResult
    suspend fun getByNroOS(nroOS: Int): Result<OSRoomModel>
}
