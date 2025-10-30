package br.com.usinasantafe.cmm.presenter.model

import br.com.usinasantafe.cmm.utils.TypeView

data class ItemMenuModel(
    val id: Int,
    val title: String,
    val type: TypeView = TypeView.ITEM,
)