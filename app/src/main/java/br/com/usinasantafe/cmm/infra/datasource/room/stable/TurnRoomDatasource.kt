package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface TurnRoomDatasource {
    suspend fun addAll(list: List<TurnRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<TurnRoomModel>>

    suspend fun getNroTurnByIdTurn(idTurn: Int): Result<Int>
}
