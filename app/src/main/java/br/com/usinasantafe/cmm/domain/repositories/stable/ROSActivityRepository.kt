package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity

interface ROSActivityRepository {
    suspend fun addAll(list: List<ROSActivity>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<ROSActivity>>
    suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivity>>
    suspend fun listByIdOS(
        idOS: Int
    ): Result<List<ROSActivity>>
}