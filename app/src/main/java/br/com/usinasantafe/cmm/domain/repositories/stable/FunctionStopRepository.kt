package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeStop
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface FunctionStopRepository: UpdateRepository<FunctionStop> {
    suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?>
}