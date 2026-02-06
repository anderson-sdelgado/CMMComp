package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Activity
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface ActivityRepository : UpdateRepository<Activity> {
    suspend fun listByIdList(
        idList: List<Int>
    ): Result<List<Activity>>
    suspend fun getById(id: Int): Result<Activity>
}
