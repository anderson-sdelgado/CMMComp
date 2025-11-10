package br.com.usinasantafe.cmm.presenter.model

data class ItemMenuModel(
    val id: Int,
    val descr: String,
    val function: Pair<Int, String>,
    val type: Pair<Int, String>,
    val app: Pair<Int, String>,
)