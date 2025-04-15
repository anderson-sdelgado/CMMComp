package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.AtividadeRoomModel

interface AtividadeRoomDatasource {
    suspend fun addAll(list: List<AtividadeRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}