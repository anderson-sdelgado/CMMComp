package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel

interface HeaderMotoMecRoomDatasource {
    suspend fun save(headerMotoMecRoomModel: HeaderMotoMecRoomModel): Result<Boolean>
    suspend fun checkHeaderOpen(): Result<Boolean>
    suspend fun getIdByHeaderOpen(): Result<Int>
}