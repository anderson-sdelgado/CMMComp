package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.TypeEquipMain

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getDescrByIdEquip(id: Int): Result<String>
}