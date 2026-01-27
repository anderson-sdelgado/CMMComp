package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSActivity
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ROSActivityRepository {
    suspend fun addAll(list: List<ROSActivity>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<ROSActivity>>
    suspend fun listByNroOS(
        token: String,
        nroOS: Int
    ): Result<List<ROSActivity>>
    suspend fun listByIdOS(
        idOS: Int
    ): Result<List<ROSActivity>>
}