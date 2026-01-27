package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.PMM

interface ItemMenuRoomDatasource {
    suspend fun addAll(list: List<ItemMenuRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByTypeList(
        typeList: List<Pair<Int, String>> = emptyList(),
        app: Pair<Int, String> = Pair(1, PMM),
        checkMenu: Boolean = true
    ): Result<List<ItemMenuRoomModel>>
}