package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.StopRoomModel

interface StopRoomDatasource {
    suspend fun addAll(list: List<StopRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByIdList(idList: List<Int>): Result<List<StopRoomModel>>
    suspend fun getById(
        id: Int
    ): Result<StopRoomModel>
}
