package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Service
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ServiceRepository {
    suspend fun addAll(list: List<Service>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Service>>
}