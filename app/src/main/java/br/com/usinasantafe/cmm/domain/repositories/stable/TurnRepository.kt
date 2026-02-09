package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Turn
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface TurnRepository : UpdateRepository<Turn> {
    suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<Turn>>
    suspend fun getNroTurnByIdTurn(
        idTurn: Int
    ): Result<Int>
}