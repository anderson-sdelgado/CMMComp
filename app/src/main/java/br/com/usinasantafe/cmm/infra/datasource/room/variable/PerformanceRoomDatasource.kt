package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.PerformanceRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface PerformanceRoomDatasource {
    suspend fun insert(model: PerformanceRoomModel): EmptyResult
    suspend fun listByIdHeader(idHeader: Int): Result<List<PerformanceRoomModel>>
    suspend fun update(id: Int, value: Double): EmptyResult
    suspend fun getNroOSById(id: Int): Result<Int>
    suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean>
}
