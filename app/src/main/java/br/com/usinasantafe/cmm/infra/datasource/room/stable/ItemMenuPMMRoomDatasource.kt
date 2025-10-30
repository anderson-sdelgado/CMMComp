package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
import br.com.usinasantafe.cmm.utils.FunctionItemMenu

interface ItemMenuPMMRoomDatasource {
    suspend fun addAll(list: List<ItemMenuPMMRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByTypeList(typeList: List<FunctionItemMenu>): Result<List<ItemMenuPMMRoomModel>>
}