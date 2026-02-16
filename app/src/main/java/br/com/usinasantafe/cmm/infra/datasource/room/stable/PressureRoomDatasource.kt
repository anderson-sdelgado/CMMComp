package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.PressureRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface PressureRoomDatasource {
    suspend fun addAll(list: List<PressureRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
}