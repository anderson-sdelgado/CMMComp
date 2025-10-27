package br.com.usinasantafe.cmm.external.room.datasource.stable

import br.com.usinasantafe.cmm.infra.datasource.room.stable.FunctionStopRoomDatasource
import br.com.usinasantafe.cmm.infra.datasource.room.stable.ItemMenuPMMRoomDatasource
import br.com.usinasantafe.cmm.infra.models.room.stable.ItemMenuPMMRoomModel
import javax.inject.Inject

class IItemMenuPMMRoomDatasource @Inject constructor(

): ItemMenuPMMRoomDatasource {
    override suspend fun addAll(list: List<ItemMenuPMMRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}