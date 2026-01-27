package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RActivityStopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface RActivityStopRoomDatasource {
    suspend fun addAll(list: List<RActivityStopRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdActivity(idActivity: Int): Result<List<RActivityStopRoomModel>>
}
