package br.com.usinasantafe.cmm.infra.datasource.room.variable

interface MechanicRoomDatasource {
    suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): Result<Boolean>
}