package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Atividade

interface AtividadeRepository  {
    suspend fun addAll(list: List<Atividade>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<Atividade>>
}