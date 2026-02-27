package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemOSMechanicRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemOSMechanicRoomDatasource {
    suspend fun addAll(list: List<ItemOSMechanicRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}