package br.com.usinasantafe.cmm.presenter.model

data class ItemHistoryScreenModel(
    val id: Int,
    val function: Pair<Int, String>,
    val descr: String,
    val dateHour: String,
    val detail: String
)
