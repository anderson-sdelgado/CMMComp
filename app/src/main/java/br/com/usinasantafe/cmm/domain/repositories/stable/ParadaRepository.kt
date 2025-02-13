package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Parada

interface ParadaRepository {
    suspend fun addAll(list: List<Parada>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<Parada>>
}