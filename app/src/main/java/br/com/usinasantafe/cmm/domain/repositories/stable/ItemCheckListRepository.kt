package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList

interface ItemCheckListRepository {
    suspend fun addAll(list: List<ItemCheckList>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<List<ItemCheckList>>
    suspend fun checkUpdateByNroEquip(
        token: String,
        nroEquip: Long
    ): Result<Boolean>
    suspend fun listByIdCheckList(
        idCheckList: Int
    ): Result<List<ItemCheckList>>
}