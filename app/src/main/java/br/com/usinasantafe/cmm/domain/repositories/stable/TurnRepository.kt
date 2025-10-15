package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn

interface TurnRepository {
    suspend fun addAll(list: List<Turn>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Turn>>
    suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>>
    suspend fun getNroTurnByIdTurn(
        idTurn: Int
    ): Result<Int>
}