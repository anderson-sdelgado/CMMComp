package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn

interface TurnRepository {
    suspend fun addAll(list: List<Turn>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<Turn>>
    suspend fun getListByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>>
}