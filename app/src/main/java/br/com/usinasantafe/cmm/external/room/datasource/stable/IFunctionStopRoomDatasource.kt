package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.FunctionStopRoomModel
import javax.inject.Inject

class IFunctionStopRoomDatasource @Inject constructor(

): FunctionStopRoomDatasource {
    override suspend fun addAll(list: List<FunctionStopRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}