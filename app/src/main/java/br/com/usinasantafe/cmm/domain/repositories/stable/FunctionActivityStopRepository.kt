package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RFuncaoAtivParada

interface FunctionActivityStopRepository {
    suspend fun addAll(list: List<RFuncaoAtivParada>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<RFuncaoAtivParada>>
}