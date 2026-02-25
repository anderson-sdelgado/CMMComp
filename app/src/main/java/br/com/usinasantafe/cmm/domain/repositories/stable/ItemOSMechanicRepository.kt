package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemOSMechanic
import br.com.usinasantafe.cmm.utils.EmptyResult


interface ItemOSMechanicRepository {
    suspend fun addAll(list: List<ItemOSMechanic>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listByIdEquipAndNroOS(
        token: String,
        idEquip: Int,
        nroOS: Int
    ): Result<List<ItemOSMechanic>>
}