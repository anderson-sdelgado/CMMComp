package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.MotoMec

interface MotoMecRepository {
    suspend fun addAll(list: List<MotoMec>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<MotoMec>>
}