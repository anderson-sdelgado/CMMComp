package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ActivityRepository {
    suspend fun addAll(list: List<Activity>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Activity>>
    suspend fun listByIdList(
        idList: List<Int>
    ): Result<List<Activity>>
    suspend fun getById(id: Int): Result<Activity>
}
