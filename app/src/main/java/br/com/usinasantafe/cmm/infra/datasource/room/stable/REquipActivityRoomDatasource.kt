package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.REquipActivityRoomModel

interface REquipActivityRoomDatasource {
    suspend fun addAll(list: List<REquipActivityRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getListByIdEquip(idEquip: Int): Result<List<REquipActivityRoomModel>>
}
