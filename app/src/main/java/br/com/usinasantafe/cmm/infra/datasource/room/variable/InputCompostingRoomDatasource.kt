package br.com.usinasantafe.cmm.infra.datasource.room.variable

interface InputCompostingRoomDatasource {
    suspend fun hasSentLoad(): Result<Boolean>
    suspend fun hasWill(): Result<Boolean>
}