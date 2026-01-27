package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemCheckList
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemCheckListRepository {
    suspend fun addAll(list: List<ItemCheckList>): EmptyResult
    suspend fun deleteAll(): EmptyResult
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
    suspend fun countByIdCheckList(
        idCheckList: Int
    ): Result<Int>
}