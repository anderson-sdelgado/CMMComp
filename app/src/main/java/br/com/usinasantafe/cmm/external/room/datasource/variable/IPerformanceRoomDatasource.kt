package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.infra.datasource.room.variable.PerformanceRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import javax.inject.Inject

class IPerformanceRoomDatasource @Inject constructor(

): PerformanceRoomDatasource {
    override suspend fun insert(model: PerformanceRoomModel): Result<Boolean> {
        TODO("Not yet implemented")
    }
}