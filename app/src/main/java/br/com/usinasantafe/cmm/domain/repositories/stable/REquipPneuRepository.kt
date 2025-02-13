package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipPneu

interface REquipPneuRepository {
    suspend fun addAll(list: List<REquipPneu>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<REquipPneu>>
}