package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.utils.EmptyResult

interface RActivityStopRepository {
    suspend fun addAll(list: List<RActivityStop>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<RActivityStop>>
    suspend fun listByIdActivity(
        idActivity: Int
    ): Result<List<RActivityStop>>
}