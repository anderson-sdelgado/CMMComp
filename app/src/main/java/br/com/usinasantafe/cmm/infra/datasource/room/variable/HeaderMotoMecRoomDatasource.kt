package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel

interface HeaderMotoMecRoomDatasource {
    suspend fun save(headerMotoMecRoomModel: HeaderMotoMecRoomModel): Result<Boolean>
    suspend fun checkHeaderOpen(): Result<Boolean>
    suspend fun getIdByHeaderOpen(): Result<Int>
    suspend fun setHourMeterFinish(hourMeter: Double): Result<Boolean>
    suspend fun finish(): Result<Boolean>
    suspend fun checkHeaderSend(): Result<Boolean>
    suspend fun listHeaderSend(): Result<List<HeaderMotoMecRoomModel>>
    suspend fun getStatusConByHeaderOpen(): Result<Boolean>
    suspend fun setSentHeader(
        id: Int,
        idBD: Long
    ): Result<Boolean>
    suspend fun setSendHeader(
        id: Int
    ): Result<Boolean>
    suspend fun getIdTurnByHeaderOpen(): Result<Int>
    suspend fun getRegOperatorOpen(): Result<Int>
}