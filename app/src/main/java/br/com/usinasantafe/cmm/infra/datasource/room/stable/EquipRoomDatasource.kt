package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.lib.EmptyResult
import br.com.usinasantafe.cmm.lib.TypeEquip

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun getDescrById(id: Int): Result<String>
    suspend fun hasByNroAndType(
        nroEquip: Long,
        typeEquip: TypeEquip
    ): Result<Boolean>
    suspend fun getIdByNro(nro: Long): Result<Int>
}