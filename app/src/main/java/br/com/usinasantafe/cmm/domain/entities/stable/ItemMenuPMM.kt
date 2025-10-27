package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.utils.TypeItemMenu

data class ItemMenuPMM(
    val id: Int,
    val title: String,
    val type: TypeItemMenu
)