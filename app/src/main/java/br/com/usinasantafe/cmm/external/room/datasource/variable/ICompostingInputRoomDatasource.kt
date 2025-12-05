package br.com.usinasantafe.cmm.external.room.datasource.variable

import br.com.usinasantafe.cmm.infra.datasource.room.variable.CompostingInputRoomDatasource
import javax.inject.Inject

class ICompostingInputRoomDatasource @Inject constructor(

): CompostingInputRoomDatasource {
    override suspend fun hasSentLoad(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}