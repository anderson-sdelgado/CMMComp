package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.utils.TypeStop

interface FunctionStopRoomDatasource {
    suspend fun addAll(list: List<FunctionStopRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?>
}