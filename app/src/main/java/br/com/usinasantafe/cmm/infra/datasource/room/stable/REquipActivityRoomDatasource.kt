package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface REquipActivityRoomDatasource {
    suspend fun addAll(list: List<REquipActivityRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdEquip(idEquip: Int): Result<List<REquipActivityRoomModel>>
}
