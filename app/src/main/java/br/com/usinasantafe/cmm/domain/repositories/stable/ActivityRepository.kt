package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity

interface ActivityRepository  {
    suspend fun addAll(list: List<Activity>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Activity>>
    suspend fun listByIdList(
        idList: List<Int>
    ): Result<List<Activity>>
}