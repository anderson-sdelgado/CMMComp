package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquip

interface MotoMecRepository {
    suspend fun refreshHeaderOpen(): EmptyResult
    suspend fun setRegOperatorHeader(regOperator: Int): EmptyResult
    suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): EmptyResult
    suspend fun getTypeEquipHeader(): Result<TypeEquip>
    suspend fun setIdTurnHeader(idTurn: Int): EmptyResult
    suspend fun setNroOSHeader(nroOS: Int): EmptyResult
    suspend fun getNroOSHeader(): Result<Int>
    suspend fun setIdActivityHeader(idActivity: Int): EmptyResult
    suspend fun getIdActivityHeader(): Result<Int>
    suspend fun getIdEquipHeader(): Result<Int>
    suspend fun setHourMeterInitialHeader(hourMeter: Double): EmptyResult
    suspend fun saveHeader(): EmptyResult
    suspend fun hasHeaderOpen(): Result<Boolean>
    suspend fun getIdByHeaderOpen(): Result<Int>
    suspend fun setHourMeterFinishHeader(hourMeter: Double): EmptyResult
    suspend fun setIdEquipMotorPumpHeader(idEquip: Int): EmptyResult
    suspend fun finishHeader(): EmptyResult
    suspend fun hasHeaderSend(): Result<Boolean>
    suspend fun setStatusConHeader(status: Boolean): EmptyResult
    suspend fun getIdTurnHeader(): Result<Int>
    suspend fun getRegOperatorHeader(): Result<Int>
    suspend fun getFlowCompostingHeader(): Result<FlowComposting>
    suspend fun setNroOSNote(nroOS: Int): EmptyResult
    suspend fun setIdActivityNote(id: Int): EmptyResult
    suspend fun setNroEquipTranshipmentNote(nroEquipTranshipment: Long): EmptyResult
    suspend fun saveNote(
        idHeader: Int
    ): EmptyResult
    suspend fun getIdActivityNote(): Result<Int>
    suspend fun setIdStop(id: Int): EmptyResult
    suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun send(
        number: Long,
        token: String
    ): EmptyResult
    suspend fun noteListByIdHeader(idHeader: Int): Result<List<ItemMotoMec>>
    suspend fun hasNoteByIdStopAndIdHeader(
        idHeader: Int,
        idStop: Int
    ): Result<Boolean>
    suspend fun getNoteLastByIdHeader(idHeader: Int): Result<ItemMotoMec>
    suspend fun hasCouplingTrailerImplement(): Result<Boolean>
    suspend fun uncouplingTrailerImplement(): EmptyResult
    suspend fun insertInitialPerformance(): Result<Unit>
}