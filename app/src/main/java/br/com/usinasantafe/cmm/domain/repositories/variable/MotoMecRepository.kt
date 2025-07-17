package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.utils.TypeEquip

interface MotoMecRepository {
    suspend fun setRegOperatorHeader(regOperator: Int): Result<Boolean>
    suspend fun setDataEquipHeader(
        idEquip: Int,
        typeEquip: TypeEquip
    ): Result<Boolean>
    suspend fun setIdTurnHeader(idTurn: Int): Result<Boolean>
    suspend fun setNroOSHeader(nroOS: Int): Result<Boolean>
    suspend fun getNroOSHeader(): Result<Int>
    suspend fun setIdActivityHeader(idActivity: Int): Result<Boolean>
    suspend fun getIdEquipHeader(): Result<Int>
    suspend fun setHourMeterInitialHeader(hourMeter: Double): Result<Boolean>
    suspend fun saveHeader(): Result<Boolean>
    suspend fun checkOpenHeader(): Result<Boolean>
    suspend fun getIdByOpenHeader(): Result<Int>
    suspend fun setHourMeterFinishHeader(hourMeter: Double): Result<Boolean>
    suspend fun finishHeader(): Result<Boolean>
    suspend fun checkSendHeader(): Result<Boolean>
    suspend fun setStatusConHeader(status: Boolean): Result<Boolean>
    suspend fun getIdTurnHeader(): Result<Int>
    suspend fun getRegOperator(): Result<Int>
    suspend fun setNroOSNote(nroOS: Int): Result<Boolean>
    suspend fun setIdActivityNote(id: Int): Result<Boolean>
    suspend fun saveNote(
        idHeader: Int
    ): Result<Boolean>
    suspend fun getIdActivity(): Result<Int>
    suspend fun setIdStop(id: Int): Result<Boolean>
    suspend fun checkNoteHasByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun send(
        number: Long,
        token: String
    ): Result<Boolean>
}