package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity

interface FunctionActivityRepository {
    suspend fun addAll(list: List<FunctionActivity>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<FunctionActivity>>
}