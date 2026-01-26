package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionActivityRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeActivity

interface FunctionActivityRoomDatasource {
    suspend fun addAll(list: List<FunctionActivityRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listById(idActivity: Int): Result<List<FunctionActivityRoomModel>>
    suspend fun hasByIdAndType(idActivity: Int, typeActivity: TypeActivity): Result<Boolean>
}
