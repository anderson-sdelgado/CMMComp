package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop

interface RActivityStopRepository {
    suspend fun addAll(list: List<RActivityStop>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<RActivityStop>>
    suspend fun listByIdActivity(
        idActivity: Int
    ): Result<List<RActivityStop>>
}