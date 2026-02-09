package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Stop
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface StopRepository : UpdateRepository<Stop> {
    suspend fun listByIdList(
        idList: List<Int>
    ): Result<List<Stop>>
    suspend fun getById(
        id: Int
    ): Result<Stop>

}