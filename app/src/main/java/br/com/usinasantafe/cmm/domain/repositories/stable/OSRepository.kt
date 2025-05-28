package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS

interface OSRepository {
    suspend fun addAll(list: List<OS>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<OS>>
    suspend fun checkNroOS(nroOS: Int): Result<Boolean>
    suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OS>>
    suspend fun add(os: OS): Result<Boolean>
    suspend fun listByNroOS(nroOS: Int): Result<List<OS>>
}