package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel

interface NoteMotoMecRoomDatasource {
    suspend fun save(noteMotoMecRoomModel: NoteMotoMecRoomModel): Result<Boolean>
    suspend fun checkHasByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun listByIdHeaderAndSend(idHeader: Int): Result<List<NoteMotoMecRoomModel>>
    suspend fun setSentNote(
        id: Int,
        idServ: Int
    ): Result<Boolean>
    suspend fun listByIdHeader(idHeader: Int): Result<List<NoteMotoMecRoomModel>>
}