package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.FunctionStop
import br.com.usinasantafe.cmm.utils.TypeStop

interface FunctionStopRepository {
    suspend fun addAll(list: List<FunctionStop>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<FunctionStop>>
    suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?>
}