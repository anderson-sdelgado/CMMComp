package br.com.usinasantafe.cmm.domain.entities.stable

data class ItemMenu(
    val id: Int,
    val descr: String,
    val type: Pair<Int, String>,
    val pos: Int,
    val function: Pair<Int, String>,
    val app: Pair<Int, String>
)
