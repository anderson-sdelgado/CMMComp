package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu

interface ItemMenuRepository {
    suspend fun addAll(list: List<ItemMenu>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<ItemMenu>>
    suspend fun listByTypeList(app: Pair<Int, String>, typeList: List<Pair<Int, String>>): Result<List<ItemMenu>>
    suspend fun listByTypeList(app: Pair<Int, String>): Result<List<ItemMenu>>
}