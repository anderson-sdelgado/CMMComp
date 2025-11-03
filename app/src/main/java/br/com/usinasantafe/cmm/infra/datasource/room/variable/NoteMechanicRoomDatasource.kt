package br.com.usinasantafe.cmm.infra.datasource.room.variable

interface NoteMechanicRoomDatasource {
    suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
}