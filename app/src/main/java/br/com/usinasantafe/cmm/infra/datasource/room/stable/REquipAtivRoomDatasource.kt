package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.REquipAtivRoomModel

interface REquipAtivRoomDatasource {
    suspend fun addAll(list: List<REquipAtivRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
