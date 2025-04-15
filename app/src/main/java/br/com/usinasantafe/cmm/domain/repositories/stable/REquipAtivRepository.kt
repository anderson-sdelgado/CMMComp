package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipAtiv

interface REquipAtivRepository {
    suspend fun addAll(list: List<REquipAtiv>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<REquipAtiv>>
}