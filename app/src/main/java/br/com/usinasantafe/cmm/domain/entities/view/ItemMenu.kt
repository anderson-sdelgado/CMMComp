package br.com.usinasantafe.cmm.domain.entities.view

import br.com.usinasantafe.cmm.utils.TypeItemMenu

data class ItemMenu(
    val id: Int,
    val title: String,
    val type: TypeItemMenu
)
