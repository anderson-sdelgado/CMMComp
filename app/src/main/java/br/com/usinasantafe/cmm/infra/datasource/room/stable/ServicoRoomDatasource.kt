package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ServicoRoomModel

interface ServicoRoomDatasource {
    suspend fun addAll(list: List<ServicoRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
