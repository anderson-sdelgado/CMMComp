package br.com.usinasantafe.cmm.infra.datasource.room.variable

import br.com.usinasantafe.cmm.infra.models.room.variable.ImplementRoomModel
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ImplementRoomDatasource {
    suspend fun save(implementRoomModel: ImplementRoomModel): EmptyResult
}