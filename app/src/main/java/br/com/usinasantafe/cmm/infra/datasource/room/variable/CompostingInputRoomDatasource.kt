package br.com.usinasantafe.cmm.infra.datasource.room.variable

interface CompostingInputRoomDatasource {
    suspend fun hasSentLoad(): Result<Boolean>
}