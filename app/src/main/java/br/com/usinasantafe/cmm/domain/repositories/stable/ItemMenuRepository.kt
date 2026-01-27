package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.utils.EmptyResult

interface ItemMenuRepository {
    suspend fun addAll(list: List<ItemMenu>): EmptyResult
    suspend fun deleteAll(): EmptyResult
    suspend fun listAll(token: String): Result<List<ItemMenu>>
    suspend fun listByTypeList(typeList: List<Pair<Int, String>>): Result<List<ItemMenu>>
    suspend fun listByApp(app: Pair<Int, String>): Result<List<ItemMenu>>
}