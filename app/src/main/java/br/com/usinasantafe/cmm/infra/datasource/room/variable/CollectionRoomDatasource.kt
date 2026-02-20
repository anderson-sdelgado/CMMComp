package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface CollectionRoomDatasource {
    suspend fun insert(model: CollectionRoomModel): EmptyResult
    suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean>
    suspend fun listByIdHeader(idHeader: Int): Result<List<CollectionRoomModel>>
    suspend fun update(id: Int, value: Double): EmptyResult
}