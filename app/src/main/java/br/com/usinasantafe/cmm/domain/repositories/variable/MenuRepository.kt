package br.com.usinasantafe.cmm.domain.repositories.variable

import br.com.usinasantafe.cmm.domain.entities.view.ItemMenu
import br.com.usinasantafe.cmm.utils.TypeItemMenu

interface MenuRepository {
    suspend fun listMenu(typeList: List<TypeItemMenu>): Result<List<ItemMenu>>
}