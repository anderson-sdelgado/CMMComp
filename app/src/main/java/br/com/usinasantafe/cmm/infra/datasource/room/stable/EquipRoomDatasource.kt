package br.com.usinasantafe.cmm.infra.datasource.room.stable

import br.com.usinasantafe.cmm.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.cmm.utils.TypeEquip

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
    suspend fun getTypeEquipByIdEquip(id: Int): Result<TypeEquip>
    suspend fun getIdCheckListByIdEquip(id: Int): Result<Int>
    suspend fun getFlagMechanicByIdEquip(
        idEquip: Int
    ): Result<Boolean>
    suspend fun getFlagTireByIdEquip(
        idEquip: Int
    ): Result<Boolean>
}