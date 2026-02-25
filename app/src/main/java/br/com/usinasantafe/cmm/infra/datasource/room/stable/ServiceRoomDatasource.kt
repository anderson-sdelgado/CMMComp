package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ServiceRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ServiceRoomDatasource {
    suspend fun addAll(list: List<ServiceRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}