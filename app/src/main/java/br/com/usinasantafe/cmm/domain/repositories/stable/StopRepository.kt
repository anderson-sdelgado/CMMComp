package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.utils.EmptyResult

interface StopRepository {
    suspend fun addAll(list: List<Stop>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Stop>>
    suspend fun listByIdList(
        idList: List<Int>
    ): Result<List<Stop>>
    suspend fun getById(
        id: Int
    ): Result<Stop>

}