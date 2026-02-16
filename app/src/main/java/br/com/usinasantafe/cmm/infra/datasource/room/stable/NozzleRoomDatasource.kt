package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.NozzleRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface NozzleRoomDatasource {
    suspend fun addAll(list: List<NozzleRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}