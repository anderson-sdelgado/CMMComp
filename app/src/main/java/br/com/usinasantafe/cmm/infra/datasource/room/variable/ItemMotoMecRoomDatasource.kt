package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ItemMotoMecRoomModel

interface ItemMotoMecRoomDatasource {
    suspend fun save(itemMotoMecRoomModel: ItemMotoMecRoomModel): Result<Boolean>
    suspend fun checkHasByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<ItemMotoMecRoomModel>>
    suspend fun setSentNote(
        id: Int,
        idServ: Int
    ): Result<Boolean>
    suspend fun listByIdHeader(idHeader: Int): Result<List<ItemMotoMecRoomModel>>
    suspend fun hasByIdStopAndIdHeader(
        idStop: Int,
        idHeader: Int
    ): Result<Boolean>
    suspend fun getLastByIdHeader(idHeader: Int): Result<ItemMotoMecRoomModel>
}