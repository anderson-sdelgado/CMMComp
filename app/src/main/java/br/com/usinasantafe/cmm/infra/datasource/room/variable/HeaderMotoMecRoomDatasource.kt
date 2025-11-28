package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.FlowComposting

interface HeaderMotoMecRoomDatasource {
    suspend fun save(headerMotoMecRoomModel: HeaderMotoMecRoomModel): Result<Boolean>
    suspend fun checkOpen(): Result<Boolean>
    suspend fun getId(): Result<Int>
    suspend fun setHourMeterFinish(hourMeter: Double): Result<Boolean>
    suspend fun finish(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun listSend(): Result<List<HeaderMotoMecRoomModel>>
    suspend fun getStatusCon(): Result<Boolean>
    suspend fun setSent(
        id: Int,
        idServ: Int
    ): Result<Boolean>
    suspend fun setSend(
        id: Int
    ): Result<Boolean>
    suspend fun getIdTurn(): Result<Int>
    suspend fun getRegOperator(): Result<Int>
    suspend fun getFlowComposting(): Result<FlowComposting>
}
