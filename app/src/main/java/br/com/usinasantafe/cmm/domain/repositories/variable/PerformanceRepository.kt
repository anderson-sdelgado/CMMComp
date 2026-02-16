package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.Performance
import br.com.usinasantafe.cmm.utils.EmptyResult

interface PerformanceRepository {
    suspend fun initial(nroOS: Int, idHeader: Int): Result<Unit>
    suspend fun listByIdHeader(idHeader: Int): Result<List<Performance>>
    suspend fun update(id: Int, value: Double): EmptyResult
    suspend fun getNroOSById(id: Int): Result<Int>
    suspend fun hasByIdHeaderAndValueNull(idHeader: Int): Result<Boolean>
    suspend fun hasByIdHeader(idHeader: Int): Result<Boolean>
}