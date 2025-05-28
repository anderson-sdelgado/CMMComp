package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.Equip

interface EquipRepository {
    suspend fun addAll(list: List<Equip>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getListByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<Equip>>
    suspend fun getDescrByIdEquip(
        idEquip: Int
    ): Result<String>
    suspend fun getCodTurnEquipByIdEquip(
        idEquip: Int
    ): Result<Int>
    suspend fun getMeasureByIdEquip(
        idEquip: Int
    ): Result<Double>
    suspend fun updateMeasureByIdEquip(
        measure: Double,
        idEquip: Int
    ): Result<Boolean>
}