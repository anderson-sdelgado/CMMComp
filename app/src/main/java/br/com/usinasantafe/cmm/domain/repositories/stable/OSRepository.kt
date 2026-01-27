package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.OS
import br.com.usinasantafe.cmm.utils.EmptyResult

interface OSRepository {
    suspend fun addAll(list: List<OS>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<OS>>
    suspend fun checkNroOS(nroOS: Int): Result<Boolean>
    suspend fun getListByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<OS>>
    suspend fun add(os: OS): EmptyResult
    suspend fun listByNroOS(nroOS: Int): Result<List<OS>>
}