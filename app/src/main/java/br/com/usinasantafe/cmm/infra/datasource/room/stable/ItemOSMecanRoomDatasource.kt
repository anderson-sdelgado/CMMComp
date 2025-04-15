package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMecanRoomModel

interface ItemOSMecanRoomDatasource {
    suspend fun addAll(list: List<ItemOSMecanRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
