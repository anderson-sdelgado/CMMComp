package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList

interface ItemCheckListRepository {
    suspend fun addAll(list: List<ItemCheckList>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun recoverAll(token: String): Result<List<ItemCheckList>>
    suspend fun checkUpdateByNroEquip(nroEquip: Long): Result<Boolean>
}