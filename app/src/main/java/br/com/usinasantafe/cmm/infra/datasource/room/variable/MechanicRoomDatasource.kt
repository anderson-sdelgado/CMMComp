package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.MechanicRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface MechanicRoomDatasource {
    suspend fun checkNoteOpenByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun setFinishNote(): EmptyResult
    suspend fun save(model: MechanicRoomModel): EmptyResult
}