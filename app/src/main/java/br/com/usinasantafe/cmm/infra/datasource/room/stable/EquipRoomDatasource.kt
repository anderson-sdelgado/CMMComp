package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getDescrByIdEquip(id: Int): Result<String>
    suspend fun getCodTurnEquipByIdEquip(id: Int): Result<Int>
    suspend fun getHourMeterByIdEquip(id: Int): Result<Double>
    suspend fun updateHourMeterByIdEquip(
        hourMeter: Double,
        idEquip: Int
    ): Result<Boolean>
    suspend fun getTypeFertByIdEquip(id: Int): Result<Int>
    suspend fun getIdCheckListByIdEquip(id: Int): Result<Int>
}