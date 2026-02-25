package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ComponentRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ComponentRoomDatasource {
    suspend fun addAll(list: List<ComponentRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}