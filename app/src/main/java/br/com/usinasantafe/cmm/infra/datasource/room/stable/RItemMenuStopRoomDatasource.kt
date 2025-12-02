package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.RItemMenuStopRoomModel

interface RItemMenuStopRoomDatasource {
    suspend fun addAll(list: List<RItemMenuStopRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getIdStopByIdFunctionAndIdApp(
        idFunction: Int,
        idApp: Int
    ): Result<Int?>
}