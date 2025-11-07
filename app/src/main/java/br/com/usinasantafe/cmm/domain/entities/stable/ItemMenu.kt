package br.com.usinasantafe.cmm.domain.entities.stable

import br.com.usinasantafe.cmm.utils.FERTIGATION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.ITEM_NORMAL
import br.com.usinasantafe.cmm.utils.MECHANICAL
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.TIRE
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.TypeItemMenu

data class ItemMenu(
    val id: Int,
    val descr: String,
    val idType: Int,
    val pos: Int,
    val idFunction: Int,
    val idApp: Int,
    val function: TypeItemMenu = TypeItemMenu.ITEM_NORMAL
)


val typeListPMM = listOf(
    1 to ITEM_NORMAL,
    3 to PERFORMANCE,
    4 to TRANSHIPMENT,
    5 to IMPLEMENT,
    6 to FERTIGATION,
    7 to MECHANICAL,
    8 to TIRE,
)

