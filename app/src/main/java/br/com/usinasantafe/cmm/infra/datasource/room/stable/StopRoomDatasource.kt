package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface StopRoomDatasource {
    suspend fun addAll(list: List<StopRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdList(idList: List<Int>): Result<List<StopRoomModel>>
    suspend fun getById(
        id: Int
    ): Result<StopRoomModel>
}
