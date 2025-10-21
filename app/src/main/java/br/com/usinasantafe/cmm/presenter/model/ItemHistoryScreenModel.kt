package br.com.usinasantafe.cmm.presenter.model

import br.com.usinasantafe.cmm.utils.FlowNote

data class ItemHistoryScreenModel(
    val id: Int,
    val type: FlowNote,
    val descr: String,
    val dateHour: String,
    val detail: String
)
