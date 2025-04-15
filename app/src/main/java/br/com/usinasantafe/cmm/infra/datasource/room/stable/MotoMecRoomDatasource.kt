package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.MotoMecRoomModel

interface MotoMecRoomDatasource {
    suspend fun addAll(list: List<MotoMecRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
