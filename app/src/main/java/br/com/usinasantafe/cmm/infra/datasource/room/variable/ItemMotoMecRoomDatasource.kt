package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemMotoMecRoomDatasource {
    suspend fun save(itemMotoMecRoomModel: ItemMotoMecRoomModel): EmptyResult
    suspend fun hasByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<ItemMotoMecRoomModel>>
    suspend fun setSentNote(
        id: Int,
        idServ: Int
    ): EmptyResult
    suspend fun listByIdHeader(idHeader: Int): Result<List<ItemMotoMecRoomModel>>
    suspend fun hasByIdStopAndIdHeader(
        idStop: Int,
        idHeader: Int
    ): Result<Boolean>
    suspend fun getLastByIdHeader(idHeader: Int): Result<ItemMotoMecRoomModel>
}