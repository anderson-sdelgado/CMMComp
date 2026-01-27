package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface RItemMenuStopRoomDatasource {
    suspend fun addAll(list: List<RItemMenuStopRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?>
}