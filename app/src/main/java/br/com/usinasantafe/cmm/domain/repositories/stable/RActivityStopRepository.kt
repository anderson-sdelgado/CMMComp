package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RActivityStop
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface RActivityStopRepository : UpdateRepository<RActivityStop> {
    suspend fun listByIdActivity(
        idActivity: Int
    ): Result<List<RActivityStop>>
}