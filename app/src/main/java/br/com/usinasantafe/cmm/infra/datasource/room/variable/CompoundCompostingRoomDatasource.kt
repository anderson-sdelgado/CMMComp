package br.com.usinasantafe.cmm.infra.datasource.room.variable

interface CompoundCompostingRoomDatasource {
    suspend fun hasWill(): Result<Boolean>
}