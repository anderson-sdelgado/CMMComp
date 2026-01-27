package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.REquipActivity
import br.com.usinasantafe.cmm.utils.EmptyResult

interface REquipActivityRepository {
    suspend fun addAll(list: List<REquipActivity>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdEquip(
        token: String,
        idEquip: Int
    ): Result<List<REquipActivity>>
    suspend fun listByIdEquip(
        idEquip: Int
    ): Result<List<REquipActivity>>
}