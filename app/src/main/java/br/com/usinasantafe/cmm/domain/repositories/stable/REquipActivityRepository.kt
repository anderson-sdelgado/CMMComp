package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity

interface REquipActivityRepository {
    suspend fun addAll(list: List<REquipActivity>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getListByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivity>>
    suspend fun getListByIdEquip(
        idEquip: Int
    ): Result<List<REquipActivity>>
}