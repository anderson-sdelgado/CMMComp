package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.REquipPneuRoomModel

interface REquipPneuRoomDatasource {
    suspend fun addAll(list: List<REquipPneuRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
