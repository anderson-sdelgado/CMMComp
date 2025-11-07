package br.com.usinasantafe.cmm.presenter.model

import br.com.usinasantafe.cmm.utils.FINISH_MECHANICAL
import br.com.usinasantafe.cmm.utils.HOSE_COLLECTION
import br.com.usinasantafe.cmm.utils.IMPLEMENT
import br.com.usinasantafe.cmm.utils.NOTE_MECHANICAL
import br.com.usinasantafe.cmm.utils.PERFORMANCE
import br.com.usinasantafe.cmm.utils.REEL
import br.com.usinasantafe.cmm.utils.STOP
import br.com.usinasantafe.cmm.utils.TIRE_CHANGE
import br.com.usinasantafe.cmm.utils.TIRE_INFLATION
import br.com.usinasantafe.cmm.utils.TRANSHIPMENT
import br.com.usinasantafe.cmm.utils.WORK

data class ItemMenuModel(
    val id: Int,
    val descr: String,
    val function: Pair<Int, String>,
)

val functionListPMM = listOf(
    1 to WORK,
    2 to STOP,
    3 to PERFORMANCE,
    4 to TRANSHIPMENT,
    5 to IMPLEMENT,
    6 to HOSE_COLLECTION,
    7 to NOTE_MECHANICAL,
    8 to FINISH_MECHANICAL,
    9 to TIRE_INFLATION,
    10 to TIRE_CHANGE,
    11 to REEL
)
