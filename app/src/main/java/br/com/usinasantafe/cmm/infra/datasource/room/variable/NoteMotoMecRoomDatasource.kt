package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.NoteMotoMecRoomModel

interface NoteMotoMecRoomDatasource {
    suspend fun save(noteMotoMecRoomModel: NoteMotoMecRoomModel): Result<Boolean>
}