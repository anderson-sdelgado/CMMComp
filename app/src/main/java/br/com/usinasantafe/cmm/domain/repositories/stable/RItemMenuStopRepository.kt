package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RItemMenuStop
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface RItemMenuStopRepository : UpdateRepository<RItemMenuStop> {
    suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?>
}