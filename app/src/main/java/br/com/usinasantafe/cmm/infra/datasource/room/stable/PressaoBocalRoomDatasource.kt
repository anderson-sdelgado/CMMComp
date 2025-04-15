package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.PressaoBocalRoomModel

interface PressaoBocalRoomDatasource {
    suspend fun addAll(list: List<PressaoBocalRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
