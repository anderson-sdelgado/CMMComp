package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ROSActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ROSActivityRoomDatasource {
    suspend fun addAll(list: List<ROSActivityRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdOS(idOS: Int): Result<List<ROSActivityRoomModel>>
}
