package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.CollectionRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface CollectionRoomDatasource {
    suspend fun insert(model: CollectionRoomModel): EmptyResult
}