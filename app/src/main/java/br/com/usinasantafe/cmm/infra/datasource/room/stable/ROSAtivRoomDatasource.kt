package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ROSAtivRoomModel

interface ROSAtivRoomDatasource {
    suspend fun addAll(list: List<ROSAtivRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}
