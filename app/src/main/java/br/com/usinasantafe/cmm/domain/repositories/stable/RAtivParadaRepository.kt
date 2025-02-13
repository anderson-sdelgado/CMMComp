package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.RAtivParada

interface RAtivParadaRepository {
    suspend fun addAll(list: List<RAtivParada>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<RAtivParada>>
}