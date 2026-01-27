package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.HeaderMotoMecRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting

interface HeaderMotoMecRoomDatasource {
    suspend fun save(headerMotoMecRoomModel: HeaderMotoMecRoomModel): Result<Long>
    suspend fun getOpen(): Result<HeaderMotoMecRoomModel>
    suspend fun checkOpen(): Result<Boolean>
    suspend fun getId(): Result<Int>
    suspend fun setHourMeterFinish(hourMeter: Double): EmptyResult
    suspend fun finish(): EmptyResult
    suspend fun hasSend(): Result<Boolean>
    suspend fun listSend(): Result<List<HeaderMotoMecRoomModel>>
    suspend fun getStatusCon(): Result<Boolean>
    suspend fun setSent(
        id: Int,
        idServ: Int
    ): EmptyResult
    suspend fun setSend(
        id: Int
    ): EmptyResult
    suspend fun getIdTurn(): Result<Int>
    suspend fun getRegOperator(): Result<Int>
    suspend fun getFlowComposting(): Result<FlowComposting>
}
