package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult

interface PerformanceRoomDatasource {
    suspend fun insert(model: PerformanceRoomModel): EmptyResult
}