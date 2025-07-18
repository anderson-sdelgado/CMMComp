package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.TurnRoomModel

interface TurnRoomDatasource {
    suspend fun addAll(list: List<TurnRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByCodTurnEquip(
        codTurnEquip: Int
    ): Result<List<TurnRoomModel>>
}
