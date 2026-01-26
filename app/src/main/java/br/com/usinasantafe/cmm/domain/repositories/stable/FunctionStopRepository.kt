package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeStop

interface FunctionStopRepository {
    suspend fun addAll(list: List<FunctionStop>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<FunctionStop>>
    suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?>
}