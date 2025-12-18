package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel

interface PerformanceRoomDatasource {
    suspend fun insert(model: PerformanceRoomModel): Result<Boolean>
}