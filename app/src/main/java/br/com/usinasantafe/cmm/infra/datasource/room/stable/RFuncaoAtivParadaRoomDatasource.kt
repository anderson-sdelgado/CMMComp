package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RFuncaoAtivParadaRoomModel

interface RFuncaoAtivParadaRoomDatasource {
    suspend fun addAll(list: List<RFuncaoAtivParadaRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
