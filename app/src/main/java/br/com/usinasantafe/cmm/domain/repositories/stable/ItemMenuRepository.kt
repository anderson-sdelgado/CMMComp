package br.com.usinasantafe.cmm.domain.repositories.stable

import br.com.usinasantafe.cmm.domain.entities.stable.ItemMenu
import br.com.usinasantafe.cmm.utils.EmptyResult
import br.com.usinasantafe.cmm.utils.UpdateRepository

interface ItemMenuRepository: UpdateRepository<ItemMenu> {
    suspend fun listByTypeList(typeList: List<Pair<Int, String>>): Result<List<ItemMenu>>
    suspend fun listByApp(app: Pair<Int, String>): Result<List<ItemMenu>>
}