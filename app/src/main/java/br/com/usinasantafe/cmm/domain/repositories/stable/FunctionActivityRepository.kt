package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionActivity
import br.com.usinasantafe.cmm.lib.TypeActivity
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface FunctionActivityRepository: UpdateRepository<FunctionActivity>  {
    suspend fun listById(idActivity: Int): Result<List<FunctionActivity>>
    suspend fun hasByIdAndType(idActivity: Int, typeActivity: TypeActivity): Result<Boolean>
}