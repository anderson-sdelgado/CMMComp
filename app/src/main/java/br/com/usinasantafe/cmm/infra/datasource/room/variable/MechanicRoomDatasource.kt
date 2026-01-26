package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.lib.EmptyResult

interface MechanicRoomDatasource {
    suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): EmptyResult
}