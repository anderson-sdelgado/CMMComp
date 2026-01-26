package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeStop

interface FunctionStopRoomDatasource {
    suspend fun addAll(list: List<FunctionStopRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getIdStopByType(typeStop: TypeStop): Result<Int?>
}