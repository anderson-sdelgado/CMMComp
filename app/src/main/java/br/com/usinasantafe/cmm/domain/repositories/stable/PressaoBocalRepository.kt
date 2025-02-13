package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.PressaoBocal

interface PressaoBocalRepository {
    suspend fun addAll(list: List<PressaoBocal>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<PressaoBocal>>
}