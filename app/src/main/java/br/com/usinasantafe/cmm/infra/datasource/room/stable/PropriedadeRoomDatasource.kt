package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.PropriedadeRoomModel

interface PropriedadeRoomDatasource {
    suspend fun addAll(list: List<PropriedadeRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
