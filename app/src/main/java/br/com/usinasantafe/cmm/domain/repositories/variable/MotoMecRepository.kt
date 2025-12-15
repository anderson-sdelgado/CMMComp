package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.variable.ItemMotoMec
import br.com.usinasantafe.cmm.lib.FlowComposting
import br.com.usinasantafe.cmm.lib.TypeEquipMain

interface MotoMecRepository {
    suspend fun refreshHeaderOpen(): Result<Boolean>
    suspend fun setRegOperatorHeader(regOperator: Int): Result<Boolean>
    suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquipMain: TypeEquipMain
    ): Result<Boolean>
    suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean>
    suspend fun setNroOSHeader(nroOS: Int): Result<Boolean>
    suspend fun getNroOSHeader(): Result<Int>
    suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean>
    suspend fun getIdActivityHeader(): Result<Int>
    suspend fun getIdEquipHeader(): Result<Int>
    suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean>
    suspend fun saveHeader(): Result<Boolean>
    suspend fun hasHeaderOpen(): Result<Boolean>
    suspend fun getIdByHeaderOpen(): Result<Int>
    suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean>
    suspend fun finishHeader(): Result<Boolean>
    suspend fun hasHeaderSend(): Result<Boolean>
    suspend fun setStatusConHeader(status: Boolean): Result<Boolean>
    suspend fun getIdTurnHeader(): Result<Int>
    suspend fun getRegOperatorHeader(): Result<Int>
    suspend fun getFlowCompostingHeader(): Result<FlowComposting>
    suspend fun setNroOSNote(nroOS: Int): Result<Boolean>
    suspend fun setIdActivityNote(id: Int): Result<Boolean>
    suspend fun setNroEquipTranshipmentNote(nroEquipTranshipment: Long): Result<Boolean>
    suspend fun saveNote(
        idHeader: Int
    ): Result<Boolean>
    suspend fun getIdActivityNote(): Result<Int>
    suspend fun setIdStop(id: Int): Result<Boolean>
    suspend fun hasNoteByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean>
    suspend fun noteListByIdHeader(idHeader: Int): Result<List<ItemMotoMec>>
    suspend fun hasNoteByIdStopAndIdHeader(
        idHeader: Int,
        idStop: Int
    ): Result<Boolean>
    suspend fun getNoteLastByIdHeader(idHeader: Int): Result<ItemMotoMec>
    suspend fun hasCouplingTrailerImplement(): Result<Boolean>
    suspend fun uncouplingTrailerImplement(): Result<Boolean>
}