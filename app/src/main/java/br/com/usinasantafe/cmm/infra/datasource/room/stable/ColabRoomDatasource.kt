package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ColabRoomDatasource {
    suspend fun addAll(list: List<ColabRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun checkByReg(reg: Int): Result<Boolean>
}
