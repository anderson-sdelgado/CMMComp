package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

interface OSRepository {
    suspend fun addAll(list: List<OS>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<OS>>
}