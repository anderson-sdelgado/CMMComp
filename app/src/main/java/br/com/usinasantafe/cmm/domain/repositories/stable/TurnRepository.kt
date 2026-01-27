package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.utils.EmptyResult

interface TurnRepository {
    suspend fun addAll(list: List<Turn>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<Turn>>
    suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>>
    suspend fun getNroTurnByIdTurn(
        idTurn: Int
    ): Result<Int>
}