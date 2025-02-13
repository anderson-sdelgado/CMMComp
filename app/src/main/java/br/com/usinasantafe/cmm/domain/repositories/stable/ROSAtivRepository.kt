package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ROSAtiv

interface ROSAtivRepository {
    suspend fun addAll(list: List<ROSAtiv>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<ROSAtiv>>
}