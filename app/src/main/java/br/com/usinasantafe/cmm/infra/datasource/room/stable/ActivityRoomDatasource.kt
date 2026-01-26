package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ActivityRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult

interface ActivityRoomDatasource {
    suspend fun addAll(list: List<ActivityRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdList(idList: List<Int>): Result<List<ActivityRoomModel>>
    suspend fun getById(id: Int): Result<ActivityRoomModel>
}